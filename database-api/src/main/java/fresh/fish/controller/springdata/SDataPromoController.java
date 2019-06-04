package fresh.fish.controller.springdata;


import fresh.fish.domain.hibernate.HibProduct;
import fresh.fish.domain.hibernate.HibPromo;
import fresh.fish.repository.hibernate.HibProductDao;
import fresh.fish.repository.hibernate.HibPromoDao;
import fresh.fish.controller.requests.PromoCreateRequest;
import fresh.fish.controller.requests.SearchCriteria;
import fresh.fish.repository.springdata.PromoRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/springdata/promo")
public class SDataPromoController {

    @Autowired
    private PromoRepository promoRep;

    @Autowired
    private HibProductDao prodDao;

    @ApiOperation(value = "Get all promo action from DB by hibernate and springdata")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<HibPromo>> getPromo() {
        return new ResponseEntity<>(promoRep.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Find promo action by id by hibernate and springdata")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity <HibPromo> getPromoById(@PathVariable Long id) {
        Optional hibOptPromo = promoRep.findById(id);
        HibPromo promo = new HibPromo();
        if (hibOptPromo.isPresent()) {
            promo = (HibPromo) hibOptPromo.get(); }
        return new ResponseEntity<>(promo, HttpStatus.OK);
    }

    @ApiOperation(value = "Create new promo action")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibPromo> createPromo(@RequestBody PromoCreateRequest request) {
        HibPromo promo = new HibPromo();
        setFields(promo, request);
        promo.setDateCreated(new Timestamp(new Date().getTime()));
        HibPromo savedPromo = promoRep.saveAndFlush(promo);
        return new ResponseEntity<>(savedPromo, HttpStatus.OK);
    }

    @ApiOperation(value = "Update some info of the promo action")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HibPromo> updatePromo(@PathVariable("id")  Long promoId, @RequestBody PromoCreateRequest request) {

        Optional hibOptPromo = promoRep.findById(promoId);
        HibPromo promo = new HibPromo();
        if (hibOptPromo.isPresent()) {
            promo = (HibPromo) hibOptPromo.get(); }
        setFields(promo, request);
        promoRep.save(promo);

        return new ResponseEntity<>(promo, HttpStatus.OK);
    }

    @ApiOperation(value = "It can delete promo action from DB by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deletePromo (@PathVariable ("id") Long promoId) {
        Optional hibOptPromo = promoRep.findById(promoId);
        if (hibOptPromo.isPresent()) {
            HibPromo promo = (HibPromo) hibOptPromo.get();
            promoRep.delete(promo);
        }
        return new ResponseEntity<>(promoId, HttpStatus.OK);
    }

    @ApiOperation(value = "You can search promo action by promo code by hibernate and springdata")
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibPromo>> searchPromo(@ModelAttribute @Valid SearchCriteria criteria) {
        List <HibPromo> searchResult = promoRep.findAllByPromoNameOrderByPromoNameAsc(criteria.getQuery());
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
