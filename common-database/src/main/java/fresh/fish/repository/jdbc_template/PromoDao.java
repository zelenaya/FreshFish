package fresh.fish.repository.jdbc_template;


import fresh.fish.domain.jdbc_template.Promo;
import fresh.fish.repository.GenericDao;

import java.util.List;

public interface PromoDao extends GenericDao<Promo, Long> {

    List<Long> batchUpdate(List<Promo> promos);
    List<Long> batchCreate(List<Promo> promos);
    List<Promo> search(String query, Integer limit, Integer offset);
    Promo findByPromoCode(String promoCode);

}
