package fresh.fish.repository;



import fresh.fish.domain.DiscountSize;

import java.util.List;

public interface DiscountDao extends GenericDao <DiscountSize, Long>{

    List<DiscountSize> search(String query, Integer limit, Integer offset);
}
