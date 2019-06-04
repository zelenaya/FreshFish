package fresh.fish.controller.springdata;


import fresh.fish.domain.hibernate.HibProduct;
import fresh.fish.repository.hibernate.HibProductDao;
import fresh.fish.controller.requests.ProductCreateRequest;
import fresh.fish.controller.requests.SearchCriteria;
import fresh.fish.repository.springdata.ProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping(value = "/rest/springdata/products")
public class SDataProductController {

    @Autowired
    private ProductRepository prodRep;

    @ApiOperation(value = "Get all products from DB")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibProduct>> getProducts() {
        return new ResponseEntity<>(prodRep.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Find product by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity <HibProduct> getProductById(@PathVariable Long id) {
        Optional hibOptProduct = prodRep.findById(id);
        HibProduct product = new HibProduct();
        if (hibOptProduct.isPresent()) {
            product = (HibProduct) hibOptProduct.get(); }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @ApiOperation(value = "Create new product")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibProduct> createProduct(@RequestBody ProductCreateRequest request) {
        HibProduct hibProduct = new HibProduct();
        setFields(hibProduct, request);

        HibProduct savedProduct = prodRep.saveAndFlush(hibProduct);
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @ApiOperation(value = "Update some info of the product by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HibProduct> updateProduct(@PathVariable("id")  Long prodId, @RequestBody ProductCreateRequest request) {

        Optional hibOptProduct = prodRep.findById(prodId);
        HibProduct product = new HibProduct();
        if (hibOptProduct.isPresent()) {
            product = (HibProduct) hibOptProduct.get(); }
        setFields(product, request);
        prodRep.save(product);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @ApiOperation(value = "You can delete product from DB by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteProduct (@PathVariable ("id") Long prodId) {
        Optional hibOptProduct = prodRep.findById(prodId);
        if (hibOptProduct.isPresent()) {
            HibProduct product = (HibProduct) hibOptProduct.get();
            prodRep.delete(product);
        }
        return new ResponseEntity<>(prodId, HttpStatus.OK);
    }

    @ApiOperation(value = "You can search the product by name or title")
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibProduct>> searchProduct(@ModelAttribute @Valid SearchCriteria criteria) {
        List <HibProduct> searchResult = prodRep.findAllByProdNameLikeOrProdTitleLike("%"+criteria.getQuery()+"%", "%"+criteria.getQuery()+"%");
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    private void setFields(HibProduct prod, ProductCreateRequest req) {
        if (!req.getProdTitle().equals("string")&&!req.getProdTitle().isEmpty())
            prod.setProdTitle(req.getProdTitle());
        if (!req.getProdName().equals("string")&&!req.getProdName().isEmpty())
            prod.setProdName(req.getProdName());
        if (!req.getProdDescription().equals("string")&&!req.getProdDescription().isEmpty())
            prod.setProdDescription(req.getProdDescription());
        if (!req.getMeasure().isEmpty()&&!req.getMeasure().equals("string"))
            prod.setMeasure(req.getMeasure());
        if (req.getAmount()!=0)
            prod.setAmount(req.getAmount());
        if (!req.getLot().equals("string")&&!req.getLot().isEmpty())
            prod.setLot(req.getLot());

        prod.setDeliveryDate(new Timestamp(req.getDeliveryDate().getTime()));
        if (!req.getProductionPlace().equals("string")&&!req.getProductionPlace().isEmpty())
            prod.setProductionPlace(req.getProductionPlace());
        if (req.getCostPrice()!=0)
            prod.setCostPrice(req.getCostPrice());
        if (req.getPrice()!=0)
            prod.setPrice(req.getPrice());
        if (!req.getUrlPhoto().equals("string")&&!req.getUrlPhoto().isEmpty())
            prod.setUrlPhoto(req.getUrlPhoto());
        if (prod.getAmount()>0) prod.setAvailable(true); else prod.setAvailable(false);
    }

}
