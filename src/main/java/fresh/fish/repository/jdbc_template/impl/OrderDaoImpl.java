package fresh.fish.repository.jdbc_template.impl;

import fresh.fish.domain.jdbc_template.Order;
import fresh.fish.domain.jdbc_template.OrderedProduct;
import fresh.fish.repository.jdbc_template.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional
public class OrderDaoImpl implements OrderDao{

    public static final String ORDER_ID = "order_id";
    public static final String CUST_ID = "customer_id";
    public static final String PERS_DISC_ID = "pers_discount_id";
    public static final String CURRENCY = "order_currency_code";
    public static final String STATUS_ID = "order_status_id";

    public static final String PROD_ID = "product_id";
    public static final String COUNT = "count";
    public static final String PRICE = "price";

    public static final String STATUS_HIS = "history";
    public static final String PRODUCTS = "products";
    public static final String PROMO = "promo";


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Order getOrderRowMapper(ResultSet resultSet, int i) throws SQLException {
        Order order = new Order();
        order.setOrderId(resultSet.getLong(ORDER_ID));
        order.setCustomerId(resultSet.getLong(CUST_ID));
        order.setPersDiscountId(resultSet.getLong(PERS_DISC_ID));
        order.setCurrencyCode(resultSet.getString(CURRENCY));
        order.setStatusId(resultSet.getLong(STATUS_ID));
        return order;
    }

    private OrderedProduct getOrderedProductRowMapper(ResultSet resultSet, int i) throws SQLException {
        OrderedProduct orderedProduct = new OrderedProduct();
        orderedProduct.setOrderId(resultSet.getLong(ORDER_ID));
        orderedProduct.setProductId(resultSet.getLong(PROD_ID));
        orderedProduct.setCount(resultSet.getLong(COUNT));
        orderedProduct.setPrice(resultSet.getLong(PRICE));
        return orderedProduct;
    }



    @Override
    public List<Order> search(String query, Integer limit, Integer offset) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public List<Long> batchUpdate(List<Order> orders) {
        String createQuery = "UPDATE orders set order_status_id = :statusId " +
                "where order_id = :orderId";

        List<SqlParameterSource> batch = new ArrayList<>();
        for (Order order : orders) {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("orderId", order.getOrderId());
            params.addValue("statusId", order.getStatusId());
            batch.add(params);
        }
        namedParameterJdbcTemplate.batchUpdate(createQuery, batch.toArray(new SqlParameterSource[batch.size()]));

        createQuery = "INSERT INTO order_status (status_id, order_id, status_date, update_by_user_id)" +
                "VALUES (:statusId, :orderId, :statusDate, :updateBy);";
        for (Order order : orders) {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("orderId", order.getOrderId());
            params.addValue("statusId", order.getStatusId());
            params.addValue("statusDate", new Timestamp(new Date().getTime()));
            params.addValue("updateBy", 1L);
            batch.add(params);
        }
        namedParameterJdbcTemplate.batchUpdate(createQuery, batch.toArray(new SqlParameterSource[batch.size()]));
        return orders.stream().map(Order::getOrderId).collect(Collectors.toList());
    }


    @Override
    public List<Order> findAll() {
        final String findAllQuery = "select * from orders";
        return namedParameterJdbcTemplate.query(findAllQuery, this::getOrderRowMapper);
    }

    @Override
    public Order findById(Long id) {
        String findById = "SELECT * FROM orders WHERE order_id = :orderId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("orderId", id);
        Order order = namedParameterJdbcTemplate.queryForObject(findById, params, this::getOrderRowMapper);

        findById = "SELECT * FROM order_products WHERE order_id = :orderId";
        order.setProducts(namedParameterJdbcTemplate.query(findById, params, this::getOrderedProductRowMapper));

        return order;
    }

    @Override
    public void delete(Long id) {
        String delQuery = "DELETE * FROM orders WHERE order_id = :orderId";
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("orderId", id);
        namedParameterJdbcTemplate.update(delQuery, param);

        delQuery = "DELETE * FROM order_status WHERE order_id = :orderId";
        namedParameterJdbcTemplate.update(delQuery, param);

        delQuery = "DELETE * FROM order_products WHERE order_id = :orderId";
        namedParameterJdbcTemplate.update(delQuery, param);

        delQuery = "DELETE * FROM order_promo WHERE order_id = :orderId";
        namedParameterJdbcTemplate.update(delQuery, param);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public Order save(Order entity) {
        String query = "INSERT INTO orders (customer_id, pers_discount_id, " +
                "order_currency_code, order_status_id) " +
                "VALUES (:custId, :persDiscId, :currency, :statusId);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("custId", new Timestamp(new Date().getTime()));
        params.addValue("persDiscId", entity.getPersDiscountId());
        params.addValue("currency", entity.getCurrencyCode());
        params.addValue("statusId", entity.getStatusId());

        namedParameterJdbcTemplate.update(query, params, keyHolder);
        Long createdOrderId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        createNewStatus(createdOrderId, entity.getStatusId());
        batchCreateProducts(createdOrderId, entity.getProducts());
        if (entity.getPromoId().size() > 0) {
            batchCreatePromo(createdOrderId, entity.getPromoId());
        }
        return findById(createdOrderId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public Order update(Order entity) {
        final String createQuery = "UPDATE orders set order_status_id = :statusId " +
                "where order_id = :orderId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("orderId", entity.getOrderId());
        params.addValue("statusId", entity.getStatusId());
        namedParameterJdbcTemplate.update(createQuery, params);

        createNewStatus(entity.getOrderId(), entity.getStatusId());

        return findById(entity.getOrderId());
    }

    public void batchCreateProducts(Long id, List<OrderedProduct> products) {
        final String query = "INSERT INTO order_products (product_id, order_id, count, price)" +
                "VALUES (:prodId, :orderId, :prodCount, :price);";
        List<SqlParameterSource> batch = new ArrayList<>();
        for (OrderedProduct prod: products) {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("orderId", id);
            param.addValue("prodId", prod.getProductId());
            param.addValue("prodCount", prod.getCount());
            param.addValue("price", prod.getPrice());
            batch.add(param);
        }
        namedParameterJdbcTemplate.batchUpdate(query, batch.toArray(new SqlParameterSource[batch.size()]));
        //return promos.stream().map(Promo::getProductId).collect(Collectors.toList());
    }

    public void batchCreatePromo(Long id, List<Long> promos) {
//        final String  query = "INSERT INTO order_promo (prod_id, order_id, promo_id)" +
//                "VALUES (:prodId, :orderId, :promoId);";
        final String  query = "INSERT INTO order_promo (order_id, promo_id)" +
                "VALUES (:orderId, :promoId);";
        List<SqlParameterSource> batch = new ArrayList<>();
        for (Long promo: promos) {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("orderId", id);
            //param.addValue("prodId", promo.getProductId());
            param.addValue("promoId", promo);
            batch.add(param);
        }
        namedParameterJdbcTemplate.batchUpdate(query, batch.toArray(new SqlParameterSource[batch.size()]));






    }

    public void createNewStatus(Long orderId, Long statusId){
        String query = "INSERT INTO order_status (status_id, order_id, status_date, update_by_user_id)" +
                "VALUES (:statusId, :orderId, :statusDate, :updateBy);";
        MapSqlParameterSource paramStatus = new MapSqlParameterSource();
        paramStatus.addValue("statusId", statusId);
        paramStatus.addValue("orderId", orderId);
        paramStatus.addValue("statusDate", new Timestamp(new Date().getTime()));
        paramStatus.addValue("updateBy", 1L);

        namedParameterJdbcTemplate.update(query, paramStatus);
    }

}
