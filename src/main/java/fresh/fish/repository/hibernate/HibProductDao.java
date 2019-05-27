package fresh.fish.repository.hibernate;



import fresh.fish.domain.hibernate.HibProduct;
import fresh.fish.repository.GenericDao;

import java.util.List;

public interface HibProductDao extends GenericDao<HibProduct, Long> {

    List<Long> batchUpdate(List<HibProduct> products);
    List<Long> batchCreate(List<HibProduct> products);
    List<HibProduct> search(String query, Integer limit, Integer offset);

}
