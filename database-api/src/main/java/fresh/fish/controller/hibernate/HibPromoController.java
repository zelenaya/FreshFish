package fresh.fish.controller.hibernate;


import fresh.fish.domain.hibernate.HibPromo;
import fresh.fish.repository.hibernate.HibProductDao;
import fresh.fish.repository.hibernate.HibPromoDao;
import fresh.fish.controller.requests.PromoCreateRequest;
import fresh.fish.controller.requests.SearchCriteria;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/hibernate/promo")
public class HibPromoController {

    @Autowired
    private HibPromoDao promoDao;

    @Autowired
    private HibProductDao prodDao;

    @ApiOperation(value = "Get all promo action from DB by hibernate")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<HibPromo>> getPromo() {
        return new ResponseEntity<>(promoDao.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Find promo action by id by hibernate")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity <HibPromo> getPromoById(@PathVariable Long id) {
        HibPromo promo = promoDao.findById(id);
        return new ResponseEntity<>(promo, HttpStatus.OK);
    }

    @ApiOperation(value = "You can search promo action by promo code by hibernate")
    @GetMapping("/searchByCode/{code}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HibPromo> searchPromoByCode(@PathVariable("code")  String promoCode) {
        HibPromo searchResult = promoDao.findByPromoCode(promoCode);
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    @ApiOperation(value = "Create new promo action by hibernate")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibPromo> createPromo(@RequestBody PromoCreateRequest request) {
        HibPromo promo = new HibPromo();
        setFields(promo, request);

        HibPromo savedPromo = promoDao.save(promo);
        return new ResponseEntity<>(savedPromo, HttpStatus.OK);
    }

    @ApiOperation(value = "Update some info of the promo action by id by hibernate")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updatePromo(@PathVariable("id")  Long promoId, @RequestBody PromoCreateRequest request) {

        HibPromo promo = promoDao.findById(promoId);
        setFields(promo, request);
        promoDao.update(promo);

        return new ResponseEntity<>(promo.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "Oh no! It can delete promo action from DB by id by hibernate")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deletePromo (@PathVariable ("id") Long promoId) {
        promoDao.delete(promoId);
        return new ResponseEntity<>(promoId, HttpStatus.OK);
    }

    @ApiOperation(value = "You can search promo action by name by hibernate")
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibPromo>> searchPromo(@ModelAttribute @Valid SearchCriteria criteria) {
        List <HibPromo> searchResult = promoDao.search(criteria.getQuery(), criteria.getLimit(), criteria.getOffset());
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    private void setFields(HibPromo promo, PromoCreateRequest req) {
        if (!req.getPromoName().equals("string")&&!req.getPromoName().isEmpty())
            promo.setPromoName(req.getPromoName());
        if (!req.getPromoDescription().equals("string")&&!req.getPromoDescription().isEmpty())
            promo.setPromoDescription(req.getPromoDescription());
        if (req.getProductId()!=0)
            promo.setProduct(prodDao.findById(req.getProductId()));
        if (req.getLimitedAmount()!=0)
            promo.setLimitedAmount(req.getLimitedAmount());
        if (req.getDiscount()!=0)
            promo.setDiscount(req.getDiscount());
        if (!req.getPromoCode().equals("string")&&!req.getPromoCode().isEmpty())
            promo.setPromoCode(req.getPromoCode());
        if (!Objects.isNull(req.getDateClose()))
            promo.setDateClose(req.getDateClose());
    }
}
