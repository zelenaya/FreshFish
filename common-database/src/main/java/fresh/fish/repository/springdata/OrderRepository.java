package fresh.fish.repository.springdata;

import fresh.fish.domain.hibernate.HibOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends JpaRepository<HibOrder, Long>, CrudRepository<HibOrder, Long>, PagingAndSortingRepository<HibOrder, Long> {



}
