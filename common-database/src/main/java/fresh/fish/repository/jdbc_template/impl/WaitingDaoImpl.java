package fresh.fish.repository.jdbc_template.impl;

import fresh.fish.domain.jdbc_template.WaitingList;
import fresh.fish.repository.jdbc_template.WaitingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional
public class WaitingDaoImpl implements WaitingDao {

    public static final String WAITING_ID = "waiting_id";
    public static final String CUST__ID = "customer_id";
    public static final String PROD_ID = "product_id";
    public static final String COUNT = "count";

    public static final String DATE_CREATED = "date_create";
    public static final String DATE_CLOSE = "date_close";

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private WaitingList getWListRowMapper(ResultSet resultSet, int i) throws SQLException {
        WaitingList waitingList = new WaitingList();
        waitingList.setWaitingId(resultSet.getLong(WAITING_ID));
        waitingList.setCustomerId(resultSet.getLong(CUST__ID));
        waitingList.setProductId(resultSet.getLong(PROD_ID));
        waitingList.setCount(resultSet.getLong(COUNT));

        waitingList.setDateCreated(resultSet.getTimestamp(DATE_CREATED));
        waitingList.setDateClose(resultSet.getTimestamp(DATE_CLOSE));
        return waitingList;
    }


    @Override
    public List<WaitingList> search(String query, Integer limit, Integer offset) {
        final String searchQuery = "SELECT wl.waiting_id, wl.customer_id, wl.product_id, wl.count, wl.date_create, wl.date_close " +
               "FROM waiting_list wl JOIN products p WHERE p.product_id = wl.product_id "+
               "AND lower(p.prod_name) LIKE lower(:query) " +
               "limit :lim offset :off";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("query", "%" + query + "%");
        params.addValue("lim", (Objects.isNull(limit)?10000:limit));
        params.addValue("off", (Objects.isNull(offset)?0:limit));

        return namedParameterJdbcTemplate.query(searchQuery, params, this::getWListRowMapper);
    }

    @Override
    public List<Long> batchUpdate(List<WaitingList> waitingLists) {
        return null;
    }

    @Override
    public List<WaitingList> findAll() {
        final String findAllQuery = "select * from waiting_list";
        return namedParameterJdbcTemplate.query(findAllQuery, this::getWListRowMapper);
    }

    @Override
    public WaitingList findById(Long id) {
        final String findById = "SELECT * FROM waiting_list WHERE waiting_id = :wListId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("wListId", id);

        return namedParameterJdbcTemplate.queryForObject(findById, params, this::getWListRowMapper);
    }

    @Override
    public void delete(Long id) {
        final String delQuery = "DELETE FROM waiting_list WHERE waiting_id = :wListId";
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("wListId", id);
        namedParameterJdbcTemplate.update(delQuery, param);
    }

    @Override
    public WaitingList save(WaitingList entity) {
        final String createQuery = "INSERT INTO waiting_list (customer_id, product_id, " +
                "count, date_create) " +
                "VALUES (:custId, :prodId, :prod_count, :dCreated);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("custId", entity.getCustomerId());
        params.addValue("prodId", entity.getProductId());
        params.addValue("prod_count", entity.getCount());
        params.addValue("dCreated", new Timestamp(new Date().getTime()));

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder);

        long createdWLId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return findById(createdWLId);
    }

    @Override
    public WaitingList update(WaitingList entity) {
        final String createQuery = "UPDATE waiting_list set customer_id = :custId, product_id = :prodId, " +
                "count = :prod_count, date_create = :dCreated, date_close = :dClose " +
                "where waiting_id = :waitingId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("custId", entity.getCustomerId());
        params.addValue("prodId", entity.getProductId());
        params.addValue("prod_count", entity.getCount());
        params.addValue("dCreated", entity.getDateCreated());
        params.addValue("dClose", entity.getDateClose());

        namedParameterJdbcTemplate.update(createQuery, params);
        return findById(entity.getWaitingId());
    }
}
