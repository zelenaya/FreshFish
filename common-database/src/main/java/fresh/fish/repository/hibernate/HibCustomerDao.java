package fresh.fish.repository.hibernate;


import fresh.fish.domain.hibernate.HibCustomer;
import fresh.fish.repository.GenericDao;

import java.util.List;

public interface HibCustomerDao extends GenericDao<HibCustomer, Long> {

    List<HibCustomer> search(String query, Integer limit, Integer offset);

}
