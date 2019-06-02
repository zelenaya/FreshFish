package fresh.fish.controller.springdata;


import fresh.fish.domain.hibernate.HibOrder;
import fresh.fish.repository.hibernate.HibCustomerDao;
import fresh.fish.repository.hibernate.HibOrderDao;
import fresh.fish.controller.requests.HibOrderCreateRequest;
import fresh.fish.controller.requests.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/springdata/managed/orders")
public class SDataOrderController {

    @Autowired
    private HibOrderDao orderDao;

    @Autowired
    private HibCustomerDao customerDao;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<HibOrder>> getOrders() {
        return new ResponseEntity<>(orderDao.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity <HibOrder> getOrderById(@PathVariable Long id) {
        HibOrder order = orderDao.findById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibOrder> createOrder(@RequestBody HibOrderCreateRequest request) {
        HibOrder order = new HibOrder();
        setFields(order, request);
//        order.setProducts();
//        order.setPromos();

        HibOrder savedOrder = orderDao.save(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HibOrder> updateOrder(@PathVariable("id")  Long orderId, @RequestBody HibOrderCreateRequest request) {

        HibOrder order = orderDao.findById(orderId);
        setFields(order, request);
        //        order.setProducts();
//        order.setPromos();

        orderDao.update(order);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteOrder (@PathVariable ("id") Long orderId) {
        orderDao.delete(orderId);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HibOrder>> searchOrder(@ModelAttribute SearchCriteria criteria) {
        List <HibOrder> searchResult = orderDao.search(criteria.getQuery(), criteria.getLimit(), criteria.getOffset());
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    private void setFields(HibOrder order, HibOrderCreateRequest req) {
        order.setCustomer(customerDao.findById(req.getCustomerId()));
        order.setPersDiscountId(req.getPersDiscountId());
        order.setCurrencyCode(req.getCurrencyCode());
        order.setStatusId(req.getStatusId());
    }

}
