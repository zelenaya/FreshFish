package fresh.fish.repository.springdata;

import fresh.fish.domain.hibernate.HibOrder;
import fresh.fish.domain.hibernate.HibOrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface HibOrderedProductRepository extends JpaRepository<HibOrderedProduct, Long>, CrudRepository<HibOrderedProduct, Long>, PagingAndSortingRepository<HibOrderedProduct, Long> {

    @Query("select ho from HibOrderedProduct ho")
    List<HibOrderedProduct> getHibOrderedProduct();


}
