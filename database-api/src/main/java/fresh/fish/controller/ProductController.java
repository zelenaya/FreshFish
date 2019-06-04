package fresh.fish.controller;

import fresh.fish.domain.jdbc_template.Product;
import fresh.fish.repository.jdbc_template.ProductDao;
import fresh.fish.controller.requests.ProductCreateRequest;
import fresh.fish.controller.requests.SearchCriteria;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/products")
public class ProductController {

    @Autowired
    @Qualifier("productDaoImpl")
    private ProductDao prodDao;

    @ApiOperation(value = "Get all products from DB")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(prodDao.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Find product by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity <Product> getProductById(@PathVariable Long id) {
        Product product = prodDao.findById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @ApiOperation(value = "Create new product")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Product> createProduct(@RequestBody ProductCreateRequest request) {
        Product product = new Product();
        setFields(product, request);

        Product savedProduct = prodDao.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @ApiOperation(value = "Update some info of the product by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful product update"),
            @ApiResponse(code = 400, message = "Invalid product ID supplied"),
            @ApiResponse(code = 404, message = "Product was not found"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> updateProduct(@PathVariable("id")  Long prodId, @RequestBody ProductCreateRequest request) {

        Product product = prodDao.findById(prodId);
        setFields(product, request);
        prodDao.update(product);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @ApiOperation(value = "Oh no! It can delete product from DB by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteProduct (@PathVariable ("id") Long prodId) {
        prodDao.delete(prodId);
        return new ResponseEntity<>(prodId, HttpStatus.OK);
    }

    @ApiOperation(value = "You can search the product by name or title")
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Product>> searchProduct(@ModelAttribute SearchCriteria criteria) {
        List <Product> searchResult = prodDao.search(criteria.getQuery(), criteria.getLimit(), criteria.getOffset());
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    private void setFields(Product prod, ProductCreateRequest req) {
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
