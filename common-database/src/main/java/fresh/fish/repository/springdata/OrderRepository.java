package fresh.fish.repository.springdata;

import fresh.fish.domain.hibernate.HibOrder;
import fresh.fish.domain.hibernate.HibOrderForResponse;
import fresh.fish.domain.hibernate.HibOrderedProduct;
import fresh.fish.domain.jdbc_template.OrderedProduct;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.List;

public interface OrderRepository extends JpaRepository<HibOrder, Long>, CrudRepository<HibOrder, Long>, PagingAndSortingRepository<HibOrder, Long> {


    @Query("select ho from HibOrder ho")
    List<HibOrder> getHibOrders();


    @Query("from HibOrderedProduct ho where ho.orderId = :orderId")
    List<HibOrderedProduct> getHibOrdersProduct(Long orderId);



}
