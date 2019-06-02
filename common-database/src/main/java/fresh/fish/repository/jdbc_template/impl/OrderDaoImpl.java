package fresh.fish.repository.jdbc_template.impl;

import fresh.fish.domain.jdbc_template.*;
import fresh.fish.repository.jdbc_template.OrderDao;
import fresh.fish.repository.jdbc_template.PromoDao;
import fresh.fish.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
    public static final String PROD_NAME = "prod_name";
    public static final String COUNT = "count";
    public static final String PROMO_ID = "promo_id";
    public static final String PROMO = "promo_code";
    public static final String DISCOUNT = "discount";
    public static final String PRICE = "price";

    public static final String STATUS_HIS = "history";
    public static final String PRODUCTS = "products";


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PromoDao promoDao;

    private Order getOrderRowMapper(ResultSet resultSet, int i) throws SQLException {
        Order order = new Order();
        order.setOrderId(resultSet.getLong(ORDER_ID));
        order.setCustomerId(resultSet.getLong(CUST_ID));
        order.setPersDiscountId(resultSet.getLong(PERS_DISC_ID));
        order.setCurrencyCode(resultSet.getString(CURRENCY));
        order.setStatusId(resultSet.getLong(STATUS_ID));
        return order;
    }

    private OrderService getOrderServiceRowMapper(ResultSet resultSet, int i) throws SQLException {
        OrderService orderService = new OrderService();
        orderService.setOrder(getOrderRowMapper(resultSet, i));

        Customer customer = new Customer();
        customer.setCustomerId(resultSet.getLong("customer_id"));
        customer.setUserId(resultSet.getLong("cust_user_id"));
        customer.setName(resultSet.getString("name"));
        customer.setSurname(resultSet.getString("surname"));
        customer.setBirthDate(resultSet.getTimestamp("birth_date"));
        //customer.setGender(resultSet.getObject("gender", Gender.class));
        customer.setGender((resultSet.getString("gender").equals(Gender.MALE.toString())?Gender.MALE:Gender.FEMALE));
        customer.setEmail(resultSet.getString("e-mail"));
        customer.setPhone_number(resultSet.getString("phone_number"));
        customer.setAdress(resultSet.getString("adress"));
        orderService.setCustomer(customer);

        return orderService;
    }

    private OrderedProduct getOrderedProductRowMapper(ResultSet resultSet, int i) throws SQLException {
        OrderedProduct orderedProduct = new OrderedProduct();
        orderedProduct.setProductId(resultSet.getLong(PROD_ID));
        orderedProduct.setProductName(resultSet.getString(PROD_NAME));
        orderedProduct.setCount(resultSet.getLong(COUNT));
        orderedProduct.setPromoId(resultSet.getLong(PROMO_ID));
        orderedProduct.setPromoCode(resultSet.getString(PROMO));
        orderedProduct.setPrice(resultSet.getLong(PRICE));
        orderedProduct.setDiscount(resultSet.getLong(DISCOUNT));
        return orderedProduct;
    }

    private Long getPriceRowMapper(ResultSet resultSet, int i) throws SQLException {
        if (resultSet.isFirst())
            return resultSet.getLong("price");
        else return 1L;
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
        final String findAllQuery = "SELECT * FROM orders";
        return namedParameterJdbcTemplate.query(findAllQuery, this::getOrderRowMapper);
    }

    @Override
    public List <OrderService> findAllFullInfo (){
        final String findAllQuery = "SELECT * FROM orders o JOIN customers c ON o.customer_id = c.cust_id";
        List<OrderService> orderServices = namedParameterJdbcTemplate.query(findAllQuery, this::getOrderServiceRowMapper);
        for (OrderService orderService: orderServices) {
            Order order = orderService.getOrder();
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("orderId", order.getOrderId());
            order.setProducts(findProductsForOrder(params));
        }
        return orderServices;
    }

    @Override
    public Order findById(Long id) {
        String findById = "SELECT * FROM orders WHERE order_id = :orderId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("orderId", id);
        Order order = namedParameterJdbcTemplate.queryForObject(findById, params, this::getOrderRowMapper);
        order.setProducts(findProductsForOrder(params));

        return order;
    }

    private List <OrderedProduct> findProductsForOrder(MapSqlParameterSource params) {
        String findById = "SELECT op.product_id, p.prod_name, op.count, op.promo_id, op.price, pr.promo_code, pr.discount FROM order_products op " +
                "JOIN products p ON p.product_id=op.product_id " +
                "LEFT JOIN promo pr ON op.promo_id = pr.promo_id " +
                "WHERE op.order_id = :orderId";
        return namedParameterJdbcTemplate.query(findById, params, this::getOrderedProductRowMapper);
    }

    @Override
    public List<Order> findByCustomerId(Long id) {
        String findById = "SELECT * FROM orders WHERE customer_id = ?";
        List<Order> orders = jdbcTemplate.query(findById, new Object[]{id}, this::getOrderRowMapper);
        return orders;
    }

    @Override
    public void delete(Long id) {
        String delQuery = "DELETE FROM orders WHERE order_id = :orderId";
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("orderId", id);
        namedParameterJdbcTemplate.update(delQuery, param);

        delQuery = "DELETE FROM order_status WHERE order_id = :orderId";
        namedParameterJdbcTemplate.update(delQuery, param);

        delQuery = "DELETE FROM order_products WHERE order_id = :orderId";
        namedParameterJdbcTemplate.update(delQuery, param);

        delQuery = "DELETE FROM order_promo WHERE order_id = :orderId";
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
        params.addValue("custId", entity.getCustomerId());
        params.addValue("persDiscId", entity.getPersDiscountId());
        params.addValue("currency", entity.getCurrencyCode());
        params.addValue("statusId", entity.getStatusId());

        namedParameterJdbcTemplate.update(query, params, keyHolder);
        Long createdOrderId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        createNewStatus(createdOrderId, 1L);
        batchCreateProducts(createdOrderId, entity.getProducts());

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

        updateOrderedProducts(entity);

        createNewStatus(entity.getOrderId(), entity.getStatusId());

        return findById(entity.getOrderId());
    }

    private void updateOrderedProducts(Order order) {
        final String queryForInsert = "INSERT INTO order_products (product_id, order_id, count, price, promo_id)" +
                "VALUES (:prodId, :orderId, :prodCount, :prodPrice, :promoId);";
        final String queryForUpdate = "UPDATE order_products set count=:prodCount, price=:prodPrice, promo_id=:promoId " +
                "where order_id = :orderId and product_id = :prodId";
        List<OrderedProduct> products = order.getProducts();

        List<SqlParameterSource> batchForInsert = new ArrayList<>();
        List<SqlParameterSource> batchForUpdate = new ArrayList<>();

        for (OrderedProduct prod: products) {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("orderId", order.getOrderId());
            param.addValue("prodId", prod.getProductId());
            param.addValue("prodCount", prod.getCount());
            if (prod.getPrice()==0) {
                fillPrice(param, prod);
                batchForInsert.add(param);
            } else {
                fillPrice(param, prod);
                batchForUpdate.add(param);
            }
        }
        if (batchForInsert.size()>0)
            namedParameterJdbcTemplate.batchUpdate(queryForInsert, batchForInsert.toArray(new SqlParameterSource[batchForInsert.size()]));
        if (batchForUpdate.size()>0)
            namedParameterJdbcTemplate.batchUpdate(queryForUpdate, batchForUpdate.toArray(new SqlParameterSource[batchForUpdate.size()]));
    }

    public void batchCreateProducts(Long id, List<OrderedProduct> products) {
        final String query = "INSERT INTO order_products (product_id, order_id, count, price, promo_id)" +
                "VALUES (:prodId, :orderId, :prodCount, :prodPrice, :promoId);";
        List<SqlParameterSource> batch = new ArrayList<>();
        for (OrderedProduct prod: products) {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("orderId", id);
            param.addValue("prodId", prod.getProductId());
            param.addValue("prodCount", prod.getCount());
            fillPrice(param, prod);
            batch.add(param);
        }
        namedParameterJdbcTemplate.batchUpdate(query, batch.toArray(new SqlParameterSource[batch.size()]));

        //batchCreatePromo(createdOrderId, entity.getProducts());
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

    private void fillPrice(MapSqlParameterSource param, OrderedProduct prod){
        String query = "SELECT p.price FROM products p WHERE product_id = :prodId AND amount >= :amount AND is_aviable ";
        MapSqlParameterSource paramForPrice = new MapSqlParameterSource();
        paramForPrice.addValue("prodId", prod.getProductId());
        paramForPrice.addValue("amount", prod.getCount());
        List <Long> prices = namedParameterJdbcTemplate.query(query, paramForPrice, this::getPriceRowMapper);
        Long price = prices.get(0);

        String promoCode = prod.getPromoCode();
        if (Objects.nonNull(promoCode)&&!promoCode.isEmpty()&&!promoCode.equals("string")) {
            Promo promo = promoDao.findByPromoCode(promoCode.trim());
            if (Objects.nonNull(promo)||promo.getDiscount()>0) {
                price = price - price * promo.getDiscount() / 100;
                param.addValue("promoId", promo.getPromoId());
            }
        } else param.addValue("promoId", 0);
        param.addValue("prodPrice", price);
    }
}
