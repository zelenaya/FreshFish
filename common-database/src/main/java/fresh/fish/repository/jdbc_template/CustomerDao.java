package fresh.fish.repository.jdbc_template;


import fresh.fish.domain.jdbc_template.Customer;
import fresh.fish.repository.GenericDao;
import fresh.fish.service.CustomerService;

import java.util.List;

public interface CustomerDao extends GenericDao<Customer, Long> {

    List<Customer> search(String query, Integer limit, Integer offset);

    List<CustomerService> getFullInfo();
    CustomerService getFullInfo(Long id);

}
