package fresh.fish.controller;



import fresh.fish.controller.requests.PromoCreateRequest;
import fresh.fish.controller.requests.SearchCriteria;
import fresh.fish.domain.jdbc_template.Promo;
import fresh.fish.repository.jdbc_template.PromoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/promo")
public class PromoController {

    @Autowired
    private PromoDao promoDao;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<Promo>> getPromo() {
        return new ResponseEntity<>(promoDao.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity <String> getPromoById(@PathVariable Long id) {
        Promo promo = promoDao.findById(id);
        return new ResponseEntity<>(promo.toString(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Promo> createPromo(@RequestBody PromoCreateRequest request) {
        Promo promo = new Promo();
        setFields(promo, request);

        Promo savedPromo = promoDao.save(promo);
        return new ResponseEntity<>(savedPromo, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updatePromo(@PathVariable("id")  Long promoId, @RequestBody PromoCreateRequest request) {

        Promo promo = promoDao.findById(promoId);
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
    public ResponseEntity<List<Promo>> searchPromo(@ModelAttribute SearchCriteria criteria) {
        List <Promo> searchResult = promoDao.search(criteria.getQuery(), criteria.getLimit(), criteria.getOffset());
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    private void setFields(Promo promo, PromoCreateRequest req) {
        promo.setPromoName(req.getPromoName());
        promo.setPromoDescription(req.getPromoDescription());
        promo.setProductId(req.getProductId());
        promo.setLimitedAmount(req.getLimitedAmount());
        promo.setDiscount(req.getDiscount());
        promo.setPromoCode(req.getPromoCode());
    }

}
