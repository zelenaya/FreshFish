package fresh.fish.repository.jdbc_template;



import fresh.fish.domain.jdbc_template.PersonalDiscount;
import fresh.fish.repository.GenericDao;

import java.util.List;

public interface PersDiscountDao extends GenericDao<PersonalDiscount, Long> {

    List<PersonalDiscount> search(String query, Integer limit, Integer offset);
}
