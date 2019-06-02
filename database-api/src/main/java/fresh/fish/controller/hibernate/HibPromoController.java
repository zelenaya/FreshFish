package fresh.fish.controller.hibernate;


import fresh.fish.domain.hibernate.HibPromo;
import fresh.fish.repository.hibernate.HibProductDao;
import fresh.fish.repository.hibernate.HibPromoDao;
import fresh.fish.controller.requests.PromoCreateRequest;
import fresh.fish.controller.requests.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/hibernate/managed/promo")
public class HibPromoController {

    @Autowired
    private HibPromoDao promoDao;

    @Autowired
    private HibProductDao prodDao;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<HibPromo>> getPromo() {
        return new ResponseEntity<>(promoDao.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity <String> getPromoById(@PathVariable Long id) {
        HibPromo promo = promoDao.findById(id);
        return new ResponseEntity<>(promo.toString(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibPromo> createPromo(@RequestBody PromoCreateRequest request) {
        HibPromo promo = new HibPromo();
        setFields(promo, request);

        HibPromo savedPromo = promoDao.save(promo);
        return new ResponseEntity<>(savedPromo, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updatePromo(@PathVariable("id")  Long promoId, @RequestBody PromoCreateRequest request) {

        HibPromo promo = promoDao.findById(promoId);
        setFields(promo, request);
        promoDao.update(promo);

        return new ResponseEntity<>(promo.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deletePromo (@PathVariable ("id") Long promoId) {
        promoDao.delete(promoId);
        return new ResponseEntity<>(promoId, HttpStatus.OK);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibPromo>> searchPromo(@ModelAttribute @Valid SearchCriteria criteria) {
        List <HibPromo> searchResult = promoDao.search(criteria.getQuery(), criteria.getLimit(), criteria.getOffset());
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    private void setFields(HibPromo promo, PromoCreateRequest req) {
        promo.setPromoName(req.getPromoName());
        promo.setPromoDescription(req.getPromoDescription());
        promo.setProduct(prodDao.findById(req.getProductId()));
        promo.setLimitedAmount(req.getLimitedAmount());
        promo.setDiscount(req.getDiscount());
        promo.setPromoCode(req.getPromoCode());
    }

}
