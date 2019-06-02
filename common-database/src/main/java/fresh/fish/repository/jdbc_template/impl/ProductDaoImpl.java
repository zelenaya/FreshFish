package fresh.fish.repository.jdbc_template.impl;

import fresh.fish.domain.jdbc_template.Product;
import fresh.fish.repository.jdbc_template.ProductDao;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@Transactional
public class ProductDaoImpl implements ProductDao {

    public static final String PROD_ID = "product_id";
    public static final String PROD_TITLE = "prod_title";
    public static final String PROD_NAME = "prod_name";
    public static final String PROD_DESCR = "prod_describing";
    public static final String PROD_MEASURE = "measure";
    public static final String PROD_AMOUNT = "amount";
    public static final String PROD_LOT = "lot";
    public static final String PROD_DATE = "delivery_date";
    public static final String PROD_PLACE = "production_place";
    public static final String PROD_COST_PRICE = "cost_price";
    public static final String PROD_PRICE = "price";
    public static final String PROD_PHOTO = "url_photo";
    public static final String PROD_IS_AVIABLE = "is_aviable";

//    @Autowired
//    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /*Read from Result Set by column name*/
    private Product getProductRowMapper(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setProductId(resultSet.getLong(PROD_ID));
        product.setProdTitle(resultSet.getString(PROD_TITLE));
        product.setProdName(resultSet.getString(PROD_NAME));
        product.setProdDescription(resultSet.getString(PROD_DESCR));
        product.setMeasure(resultSet.getString(PROD_MEASURE));
        product.setAmount(resultSet.getLong(PROD_AMOUNT));
        product.setLot(resultSet.getString(PROD_LOT));
        product.setDeliveryDate(resultSet.getTimestamp(PROD_DATE));
        product.setProductionPlace(resultSet.getString(PROD_PLACE));
        product.setCostPrice(resultSet.getLong(PROD_COST_PRICE));
        product.setPrice(resultSet.getLong(PROD_PRICE));
        product.setUrlPhoto(resultSet.getString(PROD_PHOTO));
        product.setAvailable(resultSet.getBoolean(PROD_IS_AVIABLE));
        return product;
    }

    @Override
    public List<Long> batchUpdate(List<Product> products) {
        final String createQuery = "UPDATE products set prod_title = :prodTitle, prod_name = :prodName, " +
                "prod_describing = :prodDescr, measure = :measure, amount = :amount, lot = :lot, " +
                "delivery_date = :deliveryDate, production_place = :prodPlace, cost_price = :costPrice, "+
                "price = :price, url_photo = :urlPhoto, is_aviable = :isAvialable where product_id = :prodId";

        List<SqlParameterSource> batch = new ArrayList<>();
        for (Product prod : products) {
            MapSqlParameterSource params = new MapSqlParameterSource();
            setParams(params, prod);
            params.addValue("prodId", prod.getProductId());
            batch.add(params);
        }

        namedParameterJdbcTemplate.batchUpdate(createQuery, batch.toArray(new SqlParameterSource[batch.size()]));
        return products.stream().map(Product::getProductId).collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public List<Long> batchCreate(List<Product> products) {
        final String query =  "INSERT INTO products (prod_title, prod_name, prod_describing, " +
                "measure, amount, lot, delivery_date, production_place, cost_price, price, url_photo, is_aviable) " +
                "VALUES (:prodTitle, :prodName, :prodDescr, :measure, :amount, :lot, "+
                ":deliveryDate, :prodPlace, :costPrice, :price, :urlPhoto, :isAvialable);";
        List<SqlParameterSource> batch = new ArrayList<>();
        for (Product prod: products) {
            MapSqlParameterSource param = new MapSqlParameterSource();
            setParams(param, prod);
            batch.add(param);
        }
        namedParameterJdbcTemplate.batchUpdate(query, batch.toArray(new SqlParameterSource[batch.size()]));
        return products.stream().map(Product::getProductId).collect(Collectors.toList());
    }

    @Override
    public List<Product> search(String query, Integer limit, Integer offset) {
        final String searchQuery = "select * from products where lower(prod_name) LIKE lower(:query) or " +
                "lower(prod_title) LIKE lower(:query) limit :lim offset :off";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("query", "%" + query + "%");
        params.addValue("lim", (Objects.isNull(limit)?10000:limit));
        params.addValue("off", (Objects.isNull(offset)?0:limit));

        return namedParameterJdbcTemplate.query(searchQuery, params, this::getProductRowMapper);
    }

    @Override
    public List<Product> findAll() {
        final String findAllQuery = "select * from products";
        return namedParameterJdbcTemplate.query(findAllQuery, this::getProductRowMapper);
    }

    @Override
    public Product findById(Long id) {
        final String findById = "SELECT * FROM products WHERE product_id = :prodId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("prodId", id);

        return namedParameterJdbcTemplate.queryForObject(findById, params, this::getProductRowMapper);
    }

    @Override
    public void delete(Long id) {
        final String delQuery = "DELETE FROM products WHERE product_id = :prodId";
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("prodId", id);
        namedParameterJdbcTemplate.update(delQuery, param);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public Product save(Product entity) {
        final String createQuery = "INSERT INTO products (prod_title, prod_name, prod_describing, " +
                "measure, amount, lot, delivery_date, production_place, cost_price, price, url_photo, is_aviable) " +
                "VALUES (:prodTitle, :prodName, :prodDescr, :measure, :amount, :lot, "+
                ":deliveryDate, :prodPlace, :costPrice, :price, :urlPhoto, :isAvialable);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        setParams(params, entity);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder);

        long createdProductId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return findById(createdProductId);
    }

    @Override
    public Product update(Product entity) {
        final String createQuery = "UPDATE products set prod_title = :prodTitle, prod_name = :prodName, " +
                "prod_describing = :prodDescr, measure = :measure, amount = :amount, lot = :lot, " +
                "delivery_date = :deliveryDate, production_place = :prodPlace, cost_price = :costPrice, "+
                "price = :price, url_photo = :urlPhoto, is_aviable = :isAvialable where product_id = :prodId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        setParams(params, entity);
        params.addValue("prodId", entity.getProductId());

        namedParameterJdbcTemplate.update(createQuery, params);
        return findById(entity.getProductId());
    }

    private void setParams(MapSqlParameterSource params, Product product) {
        params.addValue("prodTitle", product.getProdTitle());
        params.addValue("prodName", product.getProdName());
        params.addValue("prodDescr", product.getProdDescription());
        params.addValue("measure", product.getMeasure());
        params.addValue("amount", product.getAmount());
        params.addValue("lot", product.getLot());
        params.addValue("deliveryDate", product.getDeliveryDate());
        params.addValue("prodPlace", product.getProductionPlace());
        params.addValue("costPrice", product.getCostPrice());
        params.addValue("price", product.getPrice());
        params.addValue("urlPhoto", product.getUrlPhoto());
        params.addValue("isAvialable", product.isAvailable());
    }

}
