package fresh.fish.repository.jdbc_template;



import fresh.fish.domain.jdbc_template.Product;
import fresh.fish.repository.GenericDao;

import java.util.List;

public interface ProductDao extends GenericDao<Product, Long> {

    List<Long> batchUpdate(List<Product> products);
    List<Long> batchCreate(List<Product> products);
    List<Product> search(String query, Integer limit, Integer offset);

}
