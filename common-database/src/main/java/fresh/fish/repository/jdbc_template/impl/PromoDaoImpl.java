package fresh.fish.repository.jdbc_template.impl;

import fresh.fish.domain.jdbc_template.Promo;
import fresh.fish.repository.jdbc_template.PromoDao;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@Transactional
public class PromoDaoImpl implements PromoDao {

    public static final String PROMO_ID = "promo_id";
    public static final String PROMO_NAME = "promo_name";
    public static final String PROMO_DESCR = "promo_description";
    public static final String PROD_ID = "promo_product_id";
    public static final String LIMIT = "limited_amount";
    public static final String DISCOUNT = "discount";
    public static final String PROMO_CODE = "promo_code";

    public static final String DATE_CREATED = "date_created";
    public static final String DATE_CLOSE = "date_close";
    public static final String UPDATE_BY = "update_by_user_id";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Promo getPromoRowMapper(ResultSet resultSet, int i) throws SQLException {
        Promo promo = new Promo();
        promo.setPromoId(resultSet.getLong(PROMO_ID));
        promo.setPromoName(resultSet.getString(PROMO_NAME));
        promo.setPromoDescription(resultSet.getString(PROMO_DESCR));
        promo.setProductId(resultSet.getLong(PROD_ID));
        promo.setLimitedAmount(resultSet.getLong(LIMIT));
        promo.setDiscount(resultSet.getLong(DISCOUNT));
        promo.setPromoCode(resultSet.getString(PROMO_CODE));

        promo.setDateCreated(resultSet.getTimestamp(DATE_CREATED));
        promo.setDateClose(resultSet.getTimestamp(DATE_CLOSE));
        promo.setUpdateByUser(resultSet.getLong(UPDATE_BY));
        return promo;
    }


    @Override
    public List<Long> batchUpdate(List<Promo> promos) {
        final String createQuery = "UPDATE promo set promo_name = :promoName, promo_description = :promoDescr, " +
                "promo_product_id = :prodId, limited_amount = :limited, discount = :discount, " +
                "promo_code = :promoCode, date_close = :dClose, update_by_user_id = :updateBy "+
                "where promo_id = :promoId";

        List<SqlParameterSource> batch = new ArrayList<>();
        for (Promo prod : promos) {
            MapSqlParameterSource params = new MapSqlParameterSource();
            setPromoParams(params, prod);
            params.addValue("promoId", prod.getProductId());
            params.addValue("dClose", prod.getDateClose());
            params.addValue("updateBy", 1L);
            batch.add(params);
        }

        namedParameterJdbcTemplate.batchUpdate(createQuery, batch.toArray(new SqlParameterSource[batch.size()]));
        return promos.stream().map(Promo::getProductId).collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public List<Long> batchCreate(List<Promo> promos) {
        final String query =  "INSERT INTO promo (promo_name, promo_description, promo_product_id, " +
                "limited_amount, discount, promo_code, date_created, update_by_user_id) " +
                "VALUES (:promoName, :promoDescr, :prodId, :limited, :discount, :promoCode, "+
                ":dCreate, :updateBy);";
        List<SqlParameterSource> batch = new ArrayList<>();
        for (Promo prod: promos) {
            MapSqlParameterSource param = new MapSqlParameterSource();
            setPromoParams(param, prod);
            param.addValue("dCreate", new Timestamp(new Date().getTime()));
            param.addValue("updateBy", 1L);
            batch.add(param);
        }
        namedParameterJdbcTemplate.batchUpdate(query, batch.toArray(new SqlParameterSource[batch.size()]));
        return promos.stream().map(Promo::getProductId).collect(Collectors.toList());
    }

    @Override
    public List<Promo> search(String query, Integer limit, Integer offset) {
        final String searchQuery = "select * from promo where lower(promo_name) LIKE lower(:query) " +
                "limit :lim offset :off";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("query", "%" + query + "%");
        params.addValue("lim", (Objects.isNull(limit)?10000:limit));
        params.addValue("off", (Objects.isNull(offset)?0:limit));

        return namedParameterJdbcTemplate.query(searchQuery, params, this::getPromoRowMapper);
    }

    @Override
    public List<Promo> findAll() {
        final String findAllQuery = "select * from promo";
        return namedParameterJdbcTemplate.query(findAllQuery, this::getPromoRowMapper);
    }

    @Override
    public Promo findById(Long id) {
        final String findById = "SELECT * FROM promo WHERE promo_id = :promoId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("promoId", id);

        return namedParameterJdbcTemplate.queryForObject(findById, params, this::getPromoRowMapper);
    }

    @Override
    public Promo findByPromoCode(String promoCode) {
        final String query = "SELECT * FROM promo WHERE LOWER(promo_code) LIKE LOWER(:promoCode) AND date_close IS null";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("promoCode", promoCode);
        return namedParameterJdbcTemplate.queryForObject(query, params, this::getPromoRowMapper);
    }

    @Override
    public void delete(Long id) {
        final String delQuery = "DELETE FROM promo WHERE promo_id = :promoId";
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("promoId", id);
        namedParameterJdbcTemplate.update(delQuery, param);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public Promo save(Promo entity) {
        final String query =  "INSERT INTO promo (promo_name, promo_description, promo_product_id, " +
                "limited_amount, discount, promo_code, date_created, update_by_user_id) " +
                "VALUES (:promoName, :promoDescr, :prodId, :limited, :discount, :promoCode, "+
                ":dCreate, :updateBy);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("dCreate", new Timestamp(new Date().getTime()));
        params.addValue("updateBy", 1L);
        setPromoParams(params, entity);

        namedParameterJdbcTemplate.update(query, params, keyHolder);

        long createdPromoId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return findById(createdPromoId);
    }

    @Override
    public Promo update(Promo entity) {
        final String createQuery = "UPDATE promo set promo_name = :promoName, promo_description = :promoDescr, " +
                "promo_product_id = :prodId, limited_amount = :limited, discount = :discount, " +
                "promo_code = :promoCode, date_close = :dClose, update_by_user_id = :updateBy "+
                "where promo_id = :promoId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("promoId", entity.getPromoId());
        params.addValue("dClose", entity.getDateClose());
        params.addValue("updateBy", 1L);
        setPromoParams(params, entity);

        namedParameterJdbcTemplate.update(createQuery, params);
        return findById(entity.getPromoId());
    }

    private void setPromoParams(MapSqlParameterSource params, Promo promo) {
        params.addValue("promoName", promo.getPromoName());
        params.addValue("promoDescr", promo.getPromoDescription());
        params.addValue("prodId", promo.getProductId());
        params.addValue("limited", promo.getLimitedAmount());
        params.addValue("discount", promo.getDiscount());
        params.addValue("promoCode", promo.getPromoCode());
    }

}
