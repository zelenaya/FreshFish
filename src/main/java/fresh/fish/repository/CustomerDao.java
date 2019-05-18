package fresh.fish.repository;


import fresh.fish.domain.Customer;

import java.util.List;

public interface CustomerDao extends GenericDao<Customer, Long>{

    List<Customer> search(String query, Integer limit, Integer offset);

}
