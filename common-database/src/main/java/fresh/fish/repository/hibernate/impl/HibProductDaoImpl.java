package fresh.fish.repository.hibernate.impl;

import fresh.fish.domain.hibernate.HibOrder;
import fresh.fish.domain.hibernate.HibProduct;
import fresh.fish.domain.hibernate.HibProduct_;
import fresh.fish.repository.hibernate.HibProductDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
@Qualifier("hibProductDaoImpl")
public class HibProductDaoImpl implements HibProductDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<HibProduct> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select hp from HibProduct hp", HibProduct.class).getResultList();
        }
    }

    @Override
    public HibProduct findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(HibProduct.class, id);
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.remove(findById(id));
        }
    }

    @Override
    public HibProduct save(HibProduct entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Long newProdID = (Long) session.save(entity);
            transaction.commit();
            return session.find(HibProduct.class, newProdID);
        }
    }

    @Override
    public HibProduct update(HibProduct entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.saveOrUpdate(entity);
            transaction.commit();
            return session.find(HibProduct.class, entity.getProductId());
        }
    }

//    @Override
//    public HibProduct findByLogin(String login) {
//        try (Session session = sessionFactory.openSession()) {
//
//            //SQLQuery
//
////            NativeQuery<TestUser> nativeQuery = session.createNativeQuery("select * from test_user", TestUser.class);
////            nativeQuery.getSingleResult();
//
////            Query query = session.createQuery("" +
////                    "select tu from TestUser tu where tu.userName = :login", TestUser.class);
////            query.setParameter("login", login);
////            return (TestUser)query.getSingleResult();
//
//            TypedQuery<TestUser> query = session.createQuery("" +
//                    "select tu from TestUser tu where tu.userName = :login", TestUser.class);
//            query.setParameter("login", login);
//            return query.getSingleResult();
//        }
//    }

    @Override
    public List<Long> batchUpdate(List<HibProduct> products) {
        return null;
    }

    @Override
    public List<Long> batchCreate(List<HibProduct> products) {
        return null;
    }

    @Override
    public List<HibProduct> search(String searchQuery, Integer limit, Integer offset) {
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();//and like grater equals
        //1. Select u from TestUser u == select * from test_user
//        CriteriaQuery<TestUser> q = cb.createQuery(TestUser.class);//start to create query
//        Root<TestUser> c = q.from(TestUser.class); //from TestUser u
//        q.select(c); //select u -- select *


        //select dictinct * from test_user where userName = :userName and userSurname = :userName order by userName asc
        CriteriaQuery<HibProduct> query = cb.createQuery(HibProduct.class); //here select, where, orderBy, having
        Root<HibProduct> root = query.from(HibProduct.class); //here params

        CriteriaQuery<HibOrder> orderQuery = cb.createQuery(HibOrder.class); //here select, where, orderBy, having
        Root<HibOrder> roleRoot = orderQuery.from(HibOrder.class); //here params

        ParameterExpression<String> param = cb.parameter(String.class);
        Expression<Long> id = root.get(HibProduct_.productId);

        query.select(root)
                .distinct(true)
                .where(
                        cb.or(
                                cb.like(root.get("userName"), param),
                                cb.like(root.get("userSurname"), param)
                        ),
                        cb.and(
                                cb.gt(root.get(HibProduct_.productId), 0L),
                                id.in(1L
                                )
                        ),
                        cb.between(
                                root.get(HibProduct_.deliveryDate),
                                new Timestamp(new Date().getTime()),
                                new Timestamp(new Date().getTime())
                        )
//                        cb.between(
//                                root.get(HibProduct_.price),
//                                20,
//                                30
//                        )
                ).orderBy(cb.asc(root.get("userName")));

        try (Session session = sessionFactory.openSession()) {
            TypedQuery<HibProduct> resultQuery = session.createQuery(query); //prepared statement on hql
            resultQuery.setParameter(param, searchQuery);
            resultQuery.setMaxResults(limit);
            resultQuery.setFirstResult(offset);
            return resultQuery.getResultList();
        }
    }
}
