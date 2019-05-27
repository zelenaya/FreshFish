package fresh.fish.repository.jdbc_template;

import fresh.fish.domain.jdbc_template.Order;
import fresh.fish.repository.GenericDao;

import java.util.List;

public interface OrderDao extends GenericDao<Order, Long> {

    List<Order> search(String query, Integer limit, Integer offset);
    List<Long> batchUpdate(List<Order> orders);

}
