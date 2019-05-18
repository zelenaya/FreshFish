package fresh.fish.repository;

import fresh.fish.domain.Order;

import java.util.List;

public interface OrderDao extends GenericDao <Order, Long>{

    List<Order> search(String query, Integer limit, Integer offset);
    List<Long> batchUpdate(List<Order> orders);

}
