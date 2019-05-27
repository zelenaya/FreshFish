package fresh.fish.repository.jdbc_template.impl;

import fresh.fish.domain.jdbc_template.Customer;
import fresh.fish.domain.jdbc_template.Gender;
import fresh.fish.repository.jdbc_template.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
public class CustomerDaoImpl implements CustomerDao {

    public static final String CUST_ID = "cust_id";
    public static final String CUST_USER_ID = "cust_user_id";
    public static final String CUST_NAME = "name";
    public static final String CUST_SURNAME = "surmane";
    public static final String CUST_BOD = "birth_date";
    public static final String CUST_G = "gender";
    public static final String CUST_TEL = "phone_number";
    public static final String CUST_EMAIL = "e-mail";
    public static final String CUST_ADRESS = "adress";

    public static final String DATE_CREATED = "date_created";
    public static final String DATE_MOD = "date_modificated";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Customer getCustomerRowMapper(ResultSet resultSet, int i) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(resultSet.getLong(CUST_ID));
        customer.setUserId(resultSet.getLong(CUST_USER_ID));
        customer.setName(resultSet.getString(CUST_NAME));
        customer.setSurname(resultSet.getString(CUST_SURNAME));
        customer.setBirthDate(resultSet.getTimestamp(CUST_BOD));
        customer.setGender(resultSet.getObject(CUST_G, Gender.class));
        customer.setPhone_number(resultSet.getString(CUST_TEL));
        customer.setEmail(resultSet.getString(CUST_EMAIL));
        customer.setAdress(resultSet.getString(CUST_ADRESS));

//        customer.setDate_created(resultSet.getTimestamp(DATE_CREATED));
//        customer.setDate_modificate(resultSet.getTimestamp(DATE_MOD));
        return customer;
    }


    @Override
    public List<Customer> search(String query, Integer limit, Integer offset) {
        final String searchQuery = "select * from customers where lower(name) LIKE lower(:query) or " +
                "lower(surname) LIKE lower(:query) limit :lim offset :off";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("query", "%" + query + "%");
        params.addValue("lim", limit);
        params.addValue("off", offset);

        return namedParameterJdbcTemplate.query(searchQuery, params, this::getCustomerRowMapper);
    }

    @Override
    public List<Customer> findAll() {
        final String findAllQuery = "select * from customers";
        return namedParameterJdbcTemplate.query(findAllQuery, this::getCustomerRowMapper);
    }

    @Override
    public Customer findById(Long id) {
        final String findById = "SELECT * FROM customers WHERE cust_id = :custId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("custId", id);

        return namedParameterJdbcTemplate.queryForObject(findById, params, this::getCustomerRowMapper);
    }

    @Override
    public void delete(Long id) {
        final String delQuery = "DELETE * FROM customers WHERE cust_id = :custId";
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("custId", id);
        namedParameterJdbcTemplate.update(delQuery, param);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public Customer save(Customer entity) {
        final String createQuery = "INSERT INTO customers (cust_user_id, name, surname, " +
                "birth_date, gender, phone_number, `e-mail`, adress, date_created, date_modificate) " +
                "VALUES (:userId, :custName, :custSurname, :custBoD, :gender, :tel, "+
                ":email, :adress, :dCreated);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        setCustParams(params, entity);
        params.addValue("dCreated", new Timestamp(new Date().getTime()));

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder);

        long createdCustomerId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return findById(createdCustomerId);
    }

    @Override
    public Customer update(Customer entity) {
        final String createQuery = "UPDATE customers set cust_user_id = :userId, name = :custName, " +
                "surname = :custSurname, birth_date = :custBoD, gender = :gender, phone_number = :tel, " +
                "`e-mail` = :email, adress = :adress, date_modificate = :dModif "+
                "where cust_id = :custId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        setCustParams(params, entity);
        params.addValue("custId", entity.getCustomerId());
        params.addValue("dModif", new Timestamp(new Date().getTime()));

        namedParameterJdbcTemplate.update(createQuery, params);
        return findById(entity.getCustomerId());
    }

    private void setCustParams(MapSqlParameterSource params, Customer customer) {
        params.addValue("userId", customer.getUserId());
        params.addValue("custName", customer.getName());
        params.addValue("custSurname", customer.getSurname());
        params.addValue("custBoD", customer.getBirthDate());
        params.addValue("gender", customer.getGender());
        params.addValue("tel", customer.getPhone_number());
        params.addValue("email", customer.getEmail());
        params.addValue("adress", customer.getAdress());
    }
}
