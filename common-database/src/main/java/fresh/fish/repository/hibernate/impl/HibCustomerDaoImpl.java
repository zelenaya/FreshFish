package fresh.fish.repository.hibernate.impl;

import fresh.fish.domain.hibernate.HibCustomer;
import fresh.fish.domain.jdbc_template.Customer;
import fresh.fish.domain.jdbc_template.OrderedProduct;
import fresh.fish.repository.hibernate.HibCustomerDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

@Repository
@Qualifier("hibCustomerDaoImpl")
public class HibCustomerDaoImpl implements HibCustomerDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<HibCustomer> search(String query, Integer limit, Integer offset) {
        return null;
    }

    @Override
    public List<HibCustomer> findAll() {
        return null;
    }

    @Override
    public HibCustomer findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            HibCustomer hibCustomer = session.createNativeQuery("SELECT * FROM customers WHERE cust_id = :custId", HibCustomer.class)
                    .setParameter("custId", id)
                    .getSingleResult();
            return hibCustomer;
        }
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public HibCustomer save(HibCustomer entity) {
        return null;
    }

    @Override
    public HibCustomer update(HibCustomer entity) {
        return null;
    }


}
