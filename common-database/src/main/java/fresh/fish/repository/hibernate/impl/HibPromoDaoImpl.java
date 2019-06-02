package fresh.fish.repository.hibernate.impl;

import fresh.fish.domain.hibernate.HibPromo;
import fresh.fish.domain.hibernate.HibPromo_;
import fresh.fish.repository.hibernate.HibPromoDao;
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
@Qualifier("hibPromoDaoImpl")
public class HibPromoDaoImpl implements HibPromoDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<HibPromo> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select hp from HibPromo hp", HibPromo.class).getResultList();
        }
    }

    @Override
    public HibPromo findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(HibPromo.class, id);
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.remove(findById(id));
        }
    }

    @Override
    public HibPromo save(HibPromo entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            Long newPromoId = (Long) session.save(entity);
            transaction.commit();
            return session.find(HibPromo.class, newPromoId);
        }
    }

    @Override
    public HibPromo update(HibPromo entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.saveOrUpdate(entity);
            transaction.commit();
            return session.find(HibPromo.class, entity.getPromoId());
        }
    }

    @Override
    public List<Long> batchUpdate(List<HibPromo> promos) {
        return null;
    }

    @Override
    public List<Long> batchCreate(List<HibPromo> promos) {
        return null;
    }

    @Override
    public List<HibPromo> search(String searchQuery, Integer limit, Integer offset) {
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();//and like grater equals
        //1. Select u from TestUser u == select * from test_user
//        CriteriaQuery<TestUser> q = cb.createQuery(TestUser.class);//start to create query
//        Root<TestUser> c = q.from(TestUser.class); //from TestUser u
//        q.select(c); //select u -- select *


        //select dictinct * from test_user where userName = :userName and userSurname = :userName order by userName asc
        CriteriaQuery<HibPromo> query = cb.createQuery(HibPromo.class); //here select, where, orderBy, having
        Root<HibPromo> root = query.from(HibPromo.class); //here params

        ParameterExpression<String> param = cb.parameter(String.class);
        Expression<Long> id = root.get(HibPromo_.promoId);

        query.select(root)
                .distinct(true)
                .where(
                        cb.or(
                                cb.like(root.get("userName"), param),
                                cb.like(root.get("userSurname"), param)
                        ),
                        cb.and(
                                cb.gt(root.get(HibPromo_.promoId), 0L),
                                id.in(1L
                                )
                        ),
                        cb.between(
                                root.get(HibPromo_.dateCreated),
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
            TypedQuery<HibPromo> resultQuery = session.createQuery(query);
            resultQuery.setParameter(param, searchQuery);
            resultQuery.setMaxResults(limit);
            resultQuery.setFirstResult(offset);
            return resultQuery.getResultList();
        }
    }
}
