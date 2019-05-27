package fresh.fish.repository.jdbc_template;



import fresh.fish.domain.jdbc_template.DiscountSize;
import fresh.fish.repository.GenericDao;

import java.util.List;

public interface DiscountDao extends GenericDao<DiscountSize, Long> {

    List<DiscountSize> search(String query, Integer limit, Integer offset);
}
