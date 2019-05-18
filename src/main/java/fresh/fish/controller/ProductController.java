package fresh.fish.controller;



import fresh.fish.controller.requests.ProductCreateRequest;
import fresh.fish.controller.requests.SearchCriteria;
import fresh.fish.domain.Product;
import fresh.fish.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/products")
public class ProductController {

    @Autowired
    private ProductDao prodDao;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(prodDao.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity <String> getProductById(@PathVariable Long id) {
        Product product = prodDao.findById(id);
        return new ResponseEntity<>(product.toString(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Product> createProduct(@RequestBody ProductCreateRequest request) {
        Product product = new Product();
        setFields(product, request);

        Product savedProduct = prodDao.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateProduct(@PathVariable("id")  Long prodId, @RequestBody ProductCreateRequest request) {

        Product product = prodDao.findById(prodId);
        setFields(product, request);
        prodDao.update(product);

        return new ResponseEntity<>(product.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteProduct (@PathVariable ("id") Long prodId) {
        prodDao.delete(prodId);
        return new ResponseEntity<>(prodId, HttpStatus.OK);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Product>> searchProduct(@ModelAttribute SearchCriteria criteria) {
        List <Product> searchResult = prodDao.search(criteria.getQuery(), criteria.getLimit(), criteria.getOffset());
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    private void setFields(Product prod, ProductCreateRequest req) {
        prod.setProdTitle(req.getProdTitle());
        prod.setProdName(req.getProdName());
        prod.setProdDescription(req.getProdDescription());
        prod.setMeasure(req.getMeasure());
        prod.setAmount(req.getAmount());
        prod.setLot(req.getLot());
        prod.setDeliveryDate(req.getDeliveryDate());
        prod.setProductionPlace(req.getProductionPlace());
        prod.setCostPrice(req.getCostPrice());
        prod.setPrice(req.getPrice());
        prod.setUrlPhoto(req.getUrlPhoto());
        if (prod.getAmount()>0) prod.setAvailable(true); else prod.setAvailable(false);
    }

}
