package fresh.fish.repository.springdata;

import fresh.fish.domain.hibernate.HibCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends JpaRepository<HibCustomer, Long>, CrudRepository<HibCustomer, Long>, PagingAndSortingRepository<HibCustomer, Long> {



}
