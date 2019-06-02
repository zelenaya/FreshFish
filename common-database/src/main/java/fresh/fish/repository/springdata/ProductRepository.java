package fresh.fish.repository.springdata;

import fresh.fish.domain.hibernate.HibProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends JpaRepository<HibProduct, Long>, CrudRepository<HibProduct, Long>, PagingAndSortingRepository<HibProduct, Long> {



}
