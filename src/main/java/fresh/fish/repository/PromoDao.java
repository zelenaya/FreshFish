package fresh.fish.repository;


import fresh.fish.domain.Promo;

import java.util.List;

public interface PromoDao extends GenericDao <Promo, Long>{

    List<Long> batchUpdate(List<Promo> promos);
    List<Long> batchCreate(List<Promo> promos);
    List<Promo> search(String query, Integer limit, Integer offset);

}
