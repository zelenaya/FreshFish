package fresh.fish.controller;


import fresh.fish.domain.jdbc_template.Promo;
import fresh.fish.repository.jdbc_template.PromoDao;
import fresh.fish.controller.requests.PromoCreateRequest;
import fresh.fish.controller.requests.SearchCriteria;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/promo")
public class PromoController {

    @Autowired
    private PromoDao promoDao;

    @ApiOperation(value = "Get all promo action from DB")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<Promo>> getPromo() {
        return new ResponseEntity<>(promoDao.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Find promo action by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity <String> getPromoById(@PathVariable Long id) {
        Promo promo = promoDao.findById(id);
        return new ResponseEntity<>(promo.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new promo action")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Promo> createPromo(@RequestBody PromoCreateRequest request) {
        Promo promo = new Promo();
        setFields(promo, request);
        promo.setDateCreated(new Timestamp(new Date().getTime()));
        Promo savedPromo = promoDao.save(promo);
        return new ResponseEntity<>(savedPromo, HttpStatus.OK);
    }

    @ApiOperation(value = "Update some info of the promo action by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updatePromo(@PathVariable("id")  Long promoId, @RequestBody PromoCreateRequest request) {

        Promo promo = promoDao.findById(promoId);
        setFields(promo, request);
        promoDao.update(promo);

        return new ResponseEntity<>(promo.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "Oh no! It can delete promo action from DB by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deletePromo (@PathVariable ("id") Long promoId) {
        promoDao.delete(promoId);
        return new ResponseEntity<>(promoId, HttpStatus.OK);
    }

    @ApiOperation(value = "You can search promo action by name")
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Promo>> searchPromo(@ModelAttribute SearchCriteria criteria) {
        List <Promo> searchResult = promoDao.search(criteria.getQuery(), criteria.getLimit(), criteria.getOffset());
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    private void setFields(Promo promo, PromoCreateRequest req) {
        if (!req.getPromoName().equals("string")&&!req.getPromoName().isEmpty())
            promo.setPromoName(req.getPromoName());
        if (!req.getPromoDescription().equals("string")&&!req.getPromoDescription().isEmpty())
            promo.setPromoDescription(req.getPromoDescription());
        if (req.getProductId()!=0)
            promo.setProductId(req.getProductId());
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
