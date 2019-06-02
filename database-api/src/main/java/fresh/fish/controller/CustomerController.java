//package fresh.fish.controller;
//
//
//import fresh.fish.controller.requests.OrderCreateRequest;
//import fresh.fish.controller.requests.SearchCriteria;
//import fresh.fish.domain.jdbc_template.Customer;
//import fresh.fish.domain.jdbc_template.Order;
//import fresh.fish.repository.jdbc_template.CustomerDao;
//import fresh.fish.repository.jdbc_template.OrderDao;
//import fresh.fish.service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@CrossOrigin
//@RequestMapping(value = "/rest/customers")
//public class CustomerController {
//
//    @Autowired
//    private CustomerDao customerDao;
//
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public ResponseEntity<List<CustomerService>> getCustomers() {
//        return new ResponseEntity<>(customerDao.findAll(), HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity <Order> getCustomerById(@PathVariable Long id) {
//        Customer customer = customerDao.findById(id);
//        return new ResponseEntity<>(customer, HttpStatus.OK);
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<Order> createOrder(@RequestBody OrderCreateRequest request) {
//        Order order = new Order();
//        setFields(order, request);
//
//        Customer savedOrder = customerDao.save(order);
//        return new ResponseEntity<>(savedOrder, HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<Order> updateOrder(@PathVariable("id")  Long orderId, @RequestBody OrderCreateRequest request) {
//
//        Order order = customerDao.findById(orderId);
//        setFields(order, request);
//        customerDao.update(order);
//
//        return new ResponseEntity<>(order, HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<Long> deleteOrder (@PathVariable ("id") Long orderId) {
//        customerDao.delete(orderId);
//        return new ResponseEntity<>(orderId, HttpStatus.OK);
//    }
//
//    @GetMapping("/search")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<List<Order>> searchOrder(@ModelAttribute SearchCriteria criteria) {
//        List <Order> searchResult = customerDao.search(criteria.getQuery(), criteria.getLimit(), criteria.getOffset());
//        return new ResponseEntity<>(searchResult, HttpStatus.OK);
//    }
//
//    private void setFields(Order order, OrderCreateRequest req) {
//        order.setCustomerId(req.getCustomerId());
//        order.setPersDiscountId(req.getPersDiscountId());
//        order.setCurrencyCode(req.getCurrencyCode());
//        order.setStatusId(req.getStatusId());
//    }
//
//}
