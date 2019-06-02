package fresh.fish.repository.hibernate.impl;

import fresh.fish.domain.hibernate.HibOrder;
import fresh.fish.domain.hibernate.HibOrder_;
import fresh.fish.repository.hibernate.HibOrderDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
@Qualifier("hibOrderDaoImpl")
public class HibOrderDaoImpl implements HibOrderDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<HibOrder> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select ho from HibOrder ho", HibOrder.class).getResultList();
        }
    }

    @Override
    public HibOrder findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(HibOrder.class, id);
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.remove(findById(id));
        }
    }

    @Override
    public HibOrder save(HibOrder entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Long newOrderId = (Long) session.save(entity);
            transaction.commit();
            return session.find(HibOrder.class, newOrderId);
        }
    }

    @Override
    public HibOrder update(HibOrder entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.saveOrUpdate(entity);
            transaction.commit();
            return session.find(HibOrder.class, entity.getOrderId());
        }
    }

    @Override
    public List<Long> batchUpdate(List<HibOrder> orders) {
        return null;
    }


    @Override
    public List<HibOrder> search(String searchQuery, Integer limit, Integer offset) {
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();//and like grater equals
        //1. Select u from TestUser u == select * from test_user
//        CriteriaQuery<TestUser> q = cb.createQuery(TestUser.class);//start to create query
//        Root<TestUser> c = q.from(TestUser.class); //from TestUser u
//        q.select(c); //select u -- select *


        //select dictinct * from test_user where userName = :userName and userSurname = :userName order by userName asc
        CriteriaQuery<HibOrder> query = cb.createQuery(HibOrder.class); //here select, where, orderBy, having
        Root<HibOrder> root = query.from(HibOrder.class); //here params

        ParameterExpression<String> param = cb.parameter(String.class);
        Expression<Long> id = root.get(HibOrder_.orderId);

        query.select(root)
                .distinct(true)
                .where(
                        cb.or(
                                cb.like(root.get("userName"), param),
                                cb.like(root.get("userSurname"), param)
                        ),
                        cb.and(
                                cb.gt(root.get(HibOrder_.orderId), 0L),
                                id.in(1L
                                )
                        )
//                        cb.between(
//                                root.get(HibOrder_.),
//                                new Timestamp(new Date().getTime()),
//                                new Timestamp(new Date().getTime())
//                        )
//                        cb.between(
//                                root.get(HibProduct_.price),
//                                20,
//                                30
//                        )
                ).orderBy(cb.asc(root.get("userName")));

        try (Session session = sessionFactory.openSession()) {
            TypedQuery<HibOrder> resultQuery = session.createQuery(query);

            resultQuery.setParameter(param, searchQuery);
            resultQuery.setMaxResults(limit);
            resultQuery.setFirstResult(offset);
            return resultQuery.getResultList();
        }
    }
}
