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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
@Qualifier("hibProductDaoImpl")
public class HibProductDaoImpl implements HibProductDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<HibProduct> findAll() {
        try (Session session = sessionFactory.openSession()) {
          //  return session.createQuery("from HibProduct hp", HibProduct.class).getResultList();
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            HibProduct hibPr = session.load(HibProduct.class, id);
            //session.remove(hibPr);
           //session.delete(hibPr);
            session.remove(findById(id));
            //session.delete(findById(id));
//            session.createQuery("DELETE HibProduct WHERE product_id = :param")
//                    .setParameter("name", "id")
//                    .executeUpdate();
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


    @Override
    public List<HibProduct> search(String searchQuery, Integer limit, Integer offset) {
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();//and like grater equals
        CriteriaQuery<HibProduct> query = cb.createQuery(HibProduct.class); //here select, where, orderBy, having
        Root<HibProduct> root = query.from(HibProduct.class); //here params

        ParameterExpression<String> param = cb.parameter(String.class);
        Expression<Long> id = root.get(HibProduct_.productId);
        query.select(root)
                .where(
                        cb.or(
                                cb.like(root.get("prodName"), param),
                                cb.like(root.get("prodTitle"), param)
                        )
                ).orderBy(cb.asc(root.get("prodName")));

        try (Session session = sessionFactory.openSession()) {
            TypedQuery<HibProduct> resultQuery = session.createQuery(query); //prepared statement on hql
            resultQuery.setParameter(param, "%"+searchQuery+"%");
            resultQuery.setMaxResults(Objects.isNull(limit)?1000:limit);
            resultQuery.setFirstResult(Objects.isNull(offset)?0:offset);
            return resultQuery.getResultList();
        }
    }
}
