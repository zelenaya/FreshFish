package fresh.fish.repository;



import fresh.fish.domain.Product;

import java.util.List;

public interface ProductDao extends GenericDao<Product, Long>{

    List<Long> batchUpdate(List<Product> products);
    List<Long> batchCreate(List<Product> products);
    List<Product> search(String query, Integer limit, Integer offset);

}
