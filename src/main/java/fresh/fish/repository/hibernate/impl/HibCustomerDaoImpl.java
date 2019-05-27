package fresh.fish.repository.hibernate.impl;

import fresh.fish.domain.hibernate.HibCustomer;
import fresh.fish.repository.hibernate.HibCustomerDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

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
//        2. try (Session session = sessionFactory.openSession()) {
////            return session.createQuery("select c from HibCustomer c", HibCustomer.class).getResultList();
////        }

//
//      1. EntityManager entityManager = entityManagerFactory.createEntityManager();
//        System.out.println(entityManager.toString());
//        return entityManager.createQuery("select tu from TestUser tu", TestUser.class).getResultList();
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<HibCustomer> query = cb.createQuery(HibCustomer.class);
        Root<HibCustomer> root = query.from(HibCustomer.class);
        ParameterExpression<String> param = cb.parameter(String.class);
        query.select(root)
                .distinct(true);
//                .where(
//                        cb.and(
//                                cb.like(root.get(TestUser_.userName), param),
//                                cb.like(root.get(TestUser_.userSurname), param)
//                        ))
//                .orderBy(cb.asc(root.get("userName")));

        try (Session session = sessionFactory.openSession()) {
            Query<HibCustomer> resultQuery = session.createQuery(query);
            resultQuery.setParameter(param, "search");
            return resultQuery.getResultList();
        }


    }

    @Override
    public HibCustomer findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public HibCustomer save(HibCustomer entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Long newUserID = (Long) session.save(entity);
            transaction.commit();
            return session.find(HibCustomer.class, newUserID);
        }
    }

    @Override
    public HibCustomer update(HibCustomer entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.saveOrUpdate(entity);
            transaction.commit();
            return session.find(HibCustomer.class, entity.getUserId());
        }
    }


}
