package fresh.fish.repository.hibernate.impl;

import fresh.fish.domain.hibernate.*;
import fresh.fish.domain.jdbc_template.OrderedProduct;
import fresh.fish.repository.hibernate.HibCustomerDao;
import fresh.fish.repository.hibernate.HibOrderDao;
import fresh.fish.repository.hibernate.HibProductDao;
import fresh.fish.repository.hibernate.HibPromoDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@Qualifier("hibOrderDaoImpl")
public class HibOrderDaoImpl implements HibOrderDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Autowired
    private HibCustomerDao custDao;

    @Autowired
    private HibPromoDao promoDao;

    @Override
    public List<HibOrder> findAll() {
        try (Session session = sessionFactory.openSession()) {
            List<HibOrderedProduct> orderedProduct = session.createQuery("select ho from HibOrderedProduct ho", HibOrderedProduct.class).getResultList();

            return session.createQuery("select ho from HibOrder ho", HibOrder.class).getResultList();
        }
    }


    @Override
    public List<HibOrderForResponse> findAll(String name) {
        try (Session session = sessionFactory.openSession()) {
            List<HibOrder> orders = session.createQuery("select ho from HibOrder ho", HibOrder.class).getResultList();
            List<HibOrderForResponse> ordersForResponse = new ArrayList<>();
            for (HibOrder order: orders) {
                List<HibOrderedProduct> orderedProduct = session.createQuery("from HibOrderedProduct ho where ho.orderId = :orderId", HibOrderedProduct.class)
                        .setParameter("orderId", order.getOrderId())
                        .getResultList();
                ordersForResponse.add(convertToHibOrderForResponse(order, orderedProduct));
            }
            return ordersForResponse;
        }
    }


    @Override
    public HibOrder findById(Long id){
        try (Session session = sessionFactory.openSession()) {
            return  session.find(HibOrder.class, id);
        }
    }

    @Override
    public HibOrderForResponse findById(Long id, String name) {
        try (Session session = sessionFactory.openSession()) {
            List<HibOrderedProduct> orderedProduct = session.createQuery("from HibOrderedProduct ho where ho.orderId = :orderId", HibOrderedProduct.class)
                    .setParameter("orderId", id)
                    .getResultList();
            HibOrder order =  session.find(HibOrder.class, id);
            if (Objects.isNull(order)) {
                return new HibOrderForResponse();
            }
            else return convertToHibOrderForResponse(order, orderedProduct);
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

//    @Override
//    public void saveOrderedProducts(List<OrderedProduct> products, Long orderId){
//        List<OrderedProduct> oldProducts = findProductsForOrder(orderId);
//        for (OrderedProduct product: products) {
//            if (oldProducts.contains(product)) continue;
//            boolean isExist = false;
//            for (OrderedProduct oldProd: oldProducts) {
//                if (oldProd.getProductId() == product.getProductId()) {
//                    if (product.getCount()!=0) oldProd.setCount(product.getCount());
//                    if (!product.getPromoCode().equals("string")&&!product.getPromoCode().isEmpty()&&!oldProd.getPromoCode().equals(product.getPromoCode()))
//                        oldProd.setPromoCode(product.getPromoCode());
//                    isExist = true;
//                    break;
//                }
//            }
//            if(!isExist) oldProducts.add(product);
//        }
//
//        String queryforSave = "INSERT INTO order_products (product_id, order_id, count, price, promo_id)" +
//                "VALUES (:prodId, :orderId, :prodCount, :prodPrice, :promoId);";
//
//        try (Session session = sessionFactory.openSession()) {
//            for (OrderedProduct product: products) {
//                NativeQuery<OrderedProduct> nativeQuery = session.createNativeQuery(queryforSave, OrderedProduct.class);
//                nativeQuery.setParameter("prodId", product.getProductId());
//                nativeQuery.setParameter("orderId", orderId);
//                nativeQuery.setParameter("prodCount", product.getCount());
//                nativeQuery.setParameter("prodPrice", product.getPrice());
//                nativeQuery.setParameter("promoId", Objects.isNull(product.getPromoId())?0:product.getPromoId());
//                nativeQuery.executeUpdate();
//            }
//        }
//    }
//
//    private List <OrderedProduct> findProductsForOrder(Long orderId) {
//        String findById = "SELECT op.product_id, p.prod_name, op.count, op.promo_id, op.price, pr.promo_code, pr.discount FROM order_products op " +
//                "JOIN products p ON p.product_id=op.product_id " +
//                "LEFT JOIN promo pr ON op.promo_id = pr.promo_id " +
//                "WHERE op.order_id = :orderId";
//        try (Session session = sessionFactory.openSession()) {
//            NativeQuery<OrderedProduct> nativeQuery = session.createNativeQuery(findById);
//            nativeQuery.setLong("orderId", orderId);
//            return nativeQuery.getResultList();
//        }
//    }

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
    public List<HibOrder> search(String searchQuery, Integer limit, Integer offset) {
        return null;
    }

    private HibOrderForResponse convertToHibOrderForResponse(HibOrder order, List<HibOrderedProduct> orderedProduct) {
        HibOrderForResponse orderForResponse = new HibOrderForResponse(order.getOrderId(), order.getPersDiscountId(), order.getCurrencyCode(), order.getStatusId());
        orderForResponse.setCustomer(custDao.findById(order.getCustomerId()));

        List<OrderedProduct> ordProducts = new ArrayList<>();
        Long totalPrice = 0L;
        for (HibOrderedProduct product: orderedProduct) {
            OrderedProduct ordProd = new OrderedProduct(product.getProductId(),product.getCount(),product.getPromoId(),product.getPrice(), product.getProductName());
            totalPrice = totalPrice + product.getCount()*product.getPrice();
            if(product.getPromoId()!=0) {
                HibPromo promo = promoDao.findById(product.getPromoId());
                ordProd.setPromoCode(promo.getPromoCode());
                ordProd.setDiscount(promo.getDiscount());
            }
            ordProducts.add(ordProd);
        }
        orderForResponse.setProducts(ordProducts);
        orderForResponse.setTotalPrice(totalPrice);
        return orderForResponse;
    }


}
