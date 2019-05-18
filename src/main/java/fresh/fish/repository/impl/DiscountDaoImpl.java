package fresh.fish.repository.impl;

import fresh.fish.domain.DiscountSize;
import fresh.fish.domain.PersonalDiscount;
import fresh.fish.repository.DiscountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional
public class DiscountDaoImpl implements DiscountDao {

    public static final String DICS_ID = "discount_id";
    public static final String AMOUNT = "amount";
    public static final String DISCOUNT = "discount";

    public static final String DATE_CREATED = "date_created";
    public static final String DATE_CLOSED = "date_close";
    public static final String UDATE_BY = "update_by_user_id";


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private DiscountSize getDiscountRowMapper(ResultSet resultSet, int i) throws SQLException {
        DiscountSize discount = new DiscountSize();
        discount.setDiscountId(resultSet.getLong(DICS_ID));
        discount.setAmount(resultSet.getLong(AMOUNT));
        discount.setDiscount(resultSet.getLong(DISCOUNT));

//        discount.setDate_created(resultSet.getTimestamp(DATE_CREATED));
//        discount.setDate_close(resultSet.getTimestamp(DATE_CLOSED));
//        discount.setUpdateById(resultSet.getLong(UDATE_BY));

        return discount;
    }

    @Override
    public List<DiscountSize> search(String query, Integer limit, Integer offset) {
        final String searchQuery = "select * from discount_size where amount >= :query limit :lim offset :off";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("query", query);
        params.addValue("lim", limit);
        params.addValue("off", offset);

        return namedParameterJdbcTemplate.query(searchQuery, params, this::getDiscountRowMapper);
    }

    @Override
    public List<DiscountSize> findAll() {
        final String findAllQuery = "select * from discount_size";
        return namedParameterJdbcTemplate.query(findAllQuery, this::getDiscountRowMapper);
    }

    @Override
    public DiscountSize findById(Long id) {
        final String findById = "SELECT * FROM discount_size WHERE discount_id = :discId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("discId", id);

        return namedParameterJdbcTemplate.queryForObject(findById, params, this::getDiscountRowMapper);
    }

    @Override
    public void delete(Long id) {
        final String delQuery = "DELETE * FROM discount_size WHERE discount_id = :discId";
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("discId", id);
        namedParameterJdbcTemplate.update(delQuery, param);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public DiscountSize save(DiscountSize entity) {
        final String createQuery = "INSERT INTO discount_size (amount, discount, " +
                "date_created, date_close, update_by_user_id) " +
                "VALUES (:amount, :discount, :dCreated, :dModif, :updateBy);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("amount", entity.getAmount());
        params.addValue("discount", entity.getDiscount());

        params.addValue("dCreated", new Timestamp(new Date().getTime()));
        params.addValue("dClose", new Timestamp(new Date().getTime()));
        params.addValue("updateBy", 1L);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder);

        long createdCustomerId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return findById(createdCustomerId);
    }

    @Override
    public DiscountSize update(DiscountSize entity) {
        final String createQuery = "UPDATE discount_size set amount = :amount, discount = :discount, " +
                "update_by_user_id = :updateBy "+
                "where discount_id = :discId";

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("amount", entity.getAmount());
        params.addValue("discount", entity.getDiscount());
        params.addValue("discId", entity.getDiscountId());
        params.addValue("updateBy", 1L);

        namedParameterJdbcTemplate.update(createQuery, params);
        return findById(entity.getDiscountId());
    }
}
