package fresh.fish.repository.hibernate;

import fresh.fish.domain.hibernate.HibOrder;
import fresh.fish.repository.GenericDao;

import java.util.List;

public interface HibOrderDao extends GenericDao<HibOrder, Long> {

    List<HibOrder> search(String query, Integer limit, Integer offset);
    List<Long> batchUpdate(List<HibOrder> orders);

}
