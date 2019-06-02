package fresh.fish.repository.hibernate;


import fresh.fish.domain.hibernate.HibPromo;
import fresh.fish.repository.GenericDao;

import java.util.List;

public interface HibPromoDao extends GenericDao<HibPromo, Long> {

    List<Long> batchUpdate(List<HibPromo> promos);
    List<Long> batchCreate(List<HibPromo> promos);
    List<HibPromo> search(String query, Integer limit, Integer offset);

}
