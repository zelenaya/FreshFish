package fresh.fish.controller.springdata;


import fresh.fish.domain.hibernate.HibProduct;
import fresh.fish.repository.hibernate.HibProductDao;
import fresh.fish.controller.requests.ProductCreateRequest;
import fresh.fish.controller.requests.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/springdata/managed/products")
public class SDataProductController {

    @Autowired
    private HibProductDao prodDao;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibProduct>> getProducts() {
        return new ResponseEntity<>(prodDao.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity <HibProduct> getProductById(@PathVariable Long id) {
        HibProduct hibProduct = prodDao.findById(id);
        return new ResponseEntity<>(hibProduct, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibProduct> createProduct(@RequestBody ProductCreateRequest request) {
        HibProduct hibProduct = new HibProduct();
        setFields(hibProduct, request);

        HibProduct savedProduct = prodDao.save(hibProduct);
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateProduct(@PathVariable("id")  Long prodId, @RequestBody ProductCreateRequest request) {

        HibProduct product = prodDao.findById(prodId);
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
    public ResponseEntity<List<HibProduct>> searchProduct(@ModelAttribute @Valid SearchCriteria criteria) {
        List <HibProduct> searchResult = prodDao.search(criteria.getQuery(), criteria.getLimit(), criteria.getOffset());
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    private void setFields(HibProduct prod, ProductCreateRequest req) {
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
