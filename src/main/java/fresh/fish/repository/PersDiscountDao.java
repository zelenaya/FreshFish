package fresh.fish.repository;



import fresh.fish.domain.PersonalDiscount;

import java.util.List;

public interface PersDiscountDao extends GenericDao <PersonalDiscount, Long>{

    List<PersonalDiscount> search(String query, Integer limit, Integer offset);
}
