package fresh.fish.controller.hibernate;


import fresh.fish.controller.requests.*;
import fresh.fish.domain.hibernate.HibOrder;
import fresh.fish.domain.hibernate.HibOrderForResponse;
import fresh.fish.domain.jdbc_template.Order;
import fresh.fish.domain.jdbc_template.OrderedProduct;
import fresh.fish.repository.hibernate.HibCustomerDao;
import fresh.fish.repository.hibernate.HibOrderDao;
import fresh.fish.repository.jdbc_template.OrderDao;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/hibernate/orders")
public class HibOrderController {

    @Autowired
    private HibOrderDao orderDao;

    @Autowired
    private OrderDao jdbcOrderDao;

    @Autowired
    private HibCustomerDao customerDao;

    @ApiOperation(value = "Get all orders with information about customers and lists of ordered products")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<HibOrderForResponse>> getOrders() {
        return new ResponseEntity<>(orderDao.findAll(""), HttpStatus.OK);
    }

    @ApiOperation(value = "Find order by id with lists of ordered products")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity <HibOrderForResponse> getOrderById(@PathVariable Long id) {
        HibOrderForResponse order = orderDao.findById(id, "");
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @ApiOperation(value = "Create new order")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibOrderForResponse> createOrder(@RequestBody HibOrderCreateRequest request) {
//        HibOrder order = new HibOrder();
//        setFields(order, request);

        Order order = new Order();
        order.setStatusId(1L);
        fillTheOrder(order, request);
        Order savedOrder = jdbcOrderDao.save(order);
        HibOrderForResponse orderForResponse = orderDao.findById(savedOrder.getOrderId(), "");

        return new ResponseEntity<>(orderForResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "Update some info of the order by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HibOrderForResponse> updateOrder(@PathVariable("id")  Long orderId, @RequestBody HibOrderCreateRequest request) {

//        HibOrder order = orderDao.findById(orderId);
//        setFields(order, request);
//        orderDao.update(order);
        Order order = jdbcOrderDao.findById(orderId);
        setClearFields(order, request);
        jdbcOrderDao.update(order);
        //HibOrder hibOrder = orderDao.findById(orderId);
        HibOrderForResponse orderForResponse = orderDao.findById(orderId, "");
        return new ResponseEntity<>(orderForResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "You can try to delete order from DB by id, but...")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteOrder (@PathVariable ("id") Long orderId) {
        orderDao.delete(orderId);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }


//    @GetMapping("/search")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<List<HibOrder>> searchOrder(@ModelAttribute SearchCriteria criteria) {
//        List <HibOrder> searchResult = orderDao.search(criteria.getQuery(), criteria.getLimit(), criteria.getOffset());
//        return new ResponseEntity<>(searchResult, HttpStatus.OK);
//    }

    private void setFields(HibOrder order, HibOrderCreateRequest req) {
        if (req.getCustomerId()!=0)
            order.setCustomerId(req.getCustomerId());
        if (req.getPersDiscountId()!=0)
            order.setPersDiscountId(req.getPersDiscountId());
        if (!req.getCurrencyCode().equals("string")&&!req.getCurrencyCode().isEmpty())
            order.setCurrencyCode(req.getCurrencyCode());
        if (req.getStatusId()!=0)
            order.setStatusId(req.getStatusId());

    }

    private void setClearFields(Order order, HibOrderCreateRequest req) {
        if (req.getCustomerId()!=0)
            order.setCustomerId(req.getCustomerId());
        if (req.getPersDiscountId()!=0)
            order.setPersDiscountId(req.getPersDiscountId());
        if (!req.getCurrencyCode().equals("string")&&!req.getCurrencyCode().isEmpty())
            order.setCurrencyCode(req.getCurrencyCode());
        if (req.getStatusId()!=0)
            order.setStatusId(req.getStatusId());

        List<OrderedProduct> products = req.getProducts();
        List<OrderedProduct> oldProducts = order.getProducts();
        for (OrderedProduct product: products) {
            if (oldProducts.contains(product)) continue;
            boolean isExist = false;
            for (OrderedProduct oldProd: oldProducts) {
                if (oldProd.getProductId() == product.getProductId()) {
                    if (product.getCount()!=0) oldProd.setCount(product.getCount());
                    if (!product.getPromoCode().equals("string")&&!product.getPromoCode().isEmpty()&&!oldProd.getPromoCode().equals(product.getPromoCode()))
                        oldProd.setPromoCode(product.getPromoCode());
                    isExist = true;
                    break;
                }
            }
            if(!isExist) oldProducts.add(product);
        }

    }

    private void fillTheOrder(Order order, HibOrderCreateRequest data) {
        order.setCustomerId(data.getCustomerId());
        order.setPersDiscountId(data.getPersDiscountId());
        order.setCurrencyCode(data.getCurrencyCode());

        List <OrderedProduct> orderedProducts = data.getProducts();
        List <OrderedProduct> productsForOrder = new ArrayList<>();
        for (OrderedProduct orderedProduct: orderedProducts) {
            OrderedProduct oProd = new OrderedProduct(orderedProduct.getProductId(), orderedProduct.getCount(), orderedProduct.getPromoCode());
            productsForOrder.add(oProd);
        }
        order.setProducts(productsForOrder);

    }

}
