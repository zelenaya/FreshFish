package fresh.fish.repository.springdata;

import fresh.fish.domain.hibernate.HibPromo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PromoRepository extends JpaRepository<HibPromo, Long>, CrudRepository<HibPromo, Long>, PagingAndSortingRepository<HibPromo, Long> {

    List<HibPromo> findAllByPromoNameOrderByPromoNameAsc(String promoName);



}
