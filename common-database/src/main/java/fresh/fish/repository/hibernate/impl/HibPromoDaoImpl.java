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
import java.util.Objects;

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
    public HibPromo findByPromoCode(String promoCode) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT hp FROM HibPromo hp " +
                    "WHERE LOWER(hp.promoCode) LIKE LOWER(:promoCode) AND hp.dateClose IS null", HibPromo.class)
                    .setParameter("promoCode", promoCode)
                    .getSingleResult();
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
//            int result = session.createQuery("Delete HibPromo WHERE promoId = :param")
//                    .setParameter("param", id)
//                    .executeUpdate();

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
        CriteriaQuery<HibPromo> query = cb.createQuery(HibPromo.class); //here select, where, orderBy, having
        Root<HibPromo> root = query.from(HibPromo.class); //here params

        ParameterExpression<String> param = cb.parameter(String.class);
        Expression<Long> id = root.get(HibPromo_.promoId);

        query.select(root)
                .distinct(true)
                .where(
                        cb.and(
                                cb.like(root.get("promoName"), param),
                                cb.isNull(root.get(HibPromo_.dateClose))
                                )
                ).orderBy(cb.asc(root.get("promoName")));

        try (Session session = sessionFactory.openSession()) {
            TypedQuery<HibPromo> resultQuery = session.createQuery(query);
            resultQuery.setParameter(param, "%"+searchQuery+"%");
            resultQuery.setMaxResults(Objects.isNull(limit)?1000:limit);
            resultQuery.setFirstResult(Objects.isNull(offset)?0:offset);
            return resultQuery.getResultList();
        }
    }
}
