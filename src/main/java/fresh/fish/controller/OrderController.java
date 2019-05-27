package fresh.fish.controller;



import fresh.fish.controller.requests.OrderCreateRequest;
import fresh.fish.controller.requests.ProductCreateRequest;
import fresh.fish.controller.requests.SearchCriteria;
import fresh.fish.domain.jdbc_template.Order;
import fresh.fish.domain.jdbc_template.Product;
import fresh.fish.repository.jdbc_template.OrderDao;
import fresh.fish.repository.jdbc_template.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/orders")
public class OrderController {

    @Autowired
    private OrderDao orderDao;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<Order>> getOrders() {
        return new ResponseEntity<>(orderDao.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity <Order> getOrderById(@PathVariable Long id) {
        Order order = orderDao.findById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Order> createOrder(@RequestBody OrderCreateRequest request) {
        Order order = new Order();
        setFields(order, request);

        Order savedOrder = orderDao.save(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Order> updateOrder(@PathVariable("id")  Long orderId, @RequestBody OrderCreateRequest request) {

        Order order = orderDao.findById(orderId);
        setFields(order, request);
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
    public ResponseEntity<List<Order>> searchOrder(@ModelAttribute SearchCriteria criteria) {
        List <Order> searchResult = orderDao.search(criteria.getQuery(), criteria.getLimit(), criteria.getOffset());
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    private void setFields(Order order, OrderCreateRequest req) {
        order.setCustomerId(req.getCustomerId());
        order.setPersDiscountId(req.getPersDiscountId());
        order.setCurrencyCode(req.getCurrencyCode());
        order.setStatusId(req.getStatusId());
    }

}
