package fresh.fish.repository.hibernate;

import fresh.fish.domain.hibernate.HibOrder;
import fresh.fish.domain.hibernate.HibOrderForResponse;
import fresh.fish.domain.jdbc_template.OrderedProduct;
import fresh.fish.repository.GenericDao;

import java.util.List;

public interface HibOrderDao extends GenericDao<HibOrder, Long> {

    List<HibOrder> search(String query, Integer limit, Integer offset);
    HibOrderForResponse findById(Long id, String name);
    List<HibOrderForResponse> findAll(String name);

}
