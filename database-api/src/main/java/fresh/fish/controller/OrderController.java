package fresh.fish.controller;


import fresh.fish.controller.requests.OrderSearchCriteria;
import fresh.fish.controller.requests.ProductTuOrderSearchCriteria;
import fresh.fish.domain.jdbc_template.Order;
import fresh.fish.domain.jdbc_template.OrderedProduct;
import fresh.fish.repository.jdbc_template.OrderDao;
import fresh.fish.controller.requests.OrderCreateRequest;
import fresh.fish.controller.requests.SearchCriteria;
import fresh.fish.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/orders")
public class OrderController {

    @Autowired
    private OrderDao orderDao;

    @ApiOperation(value = "Get all orders with information about customers and lists of ordered products")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<OrderService>> getOrders() {
        return new ResponseEntity<>(orderDao.findAllFullInfo(), HttpStatus.OK);
    }

    @ApiOperation(value = "Find order by id with lists of ordered products")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity <Order> getOrderById(@PathVariable Long id) {
        Order order = orderDao.findById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @ApiOperation(value = "Create new order")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Order> createOrder(@RequestBody OrderSearchCriteria request) {
        Order order = new Order();
        order.setStatusId(1L);
        fillTheOrder(order, request);

        Order savedOrder = orderDao.save(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.OK);
    }

    @ApiOperation(value = "Update some info of the order by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Order> updateOrder(@PathVariable("id")  Long orderId, @RequestBody OrderCreateRequest request) {

        Order order = orderDao.findById(orderId);
        setFields(order, request);
        orderDao.update(order);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @ApiOperation(value = "Oh no! It can delete order from DB by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteOrder (@PathVariable ("id") Long orderId) {
        orderDao.delete(orderId);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    @ApiOperation(value = "Now you can't search orders, because I don't know what criteria you want to use to find them")
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Order>> searchOrder(@ModelAttribute SearchCriteria criteria) {
        List <Order> searchResult = orderDao.search(criteria.getQuery(), criteria.getLimit(), criteria.getOffset());
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    private void setFields(Order order, OrderCreateRequest req) {
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

    private void fillTheOrder(Order order, OrderSearchCriteria data) {
        order.setCustomerId(data.getCustomerId());
        order.setPersDiscountId(data.getPersDiscountId());
        order.setCurrencyCode(data.getCurrencyCode());

        List <ProductTuOrderSearchCriteria> orderedProducts = data.getProducts();
        List <OrderedProduct> productsForOrder = new ArrayList<>();
        for (ProductTuOrderSearchCriteria orderedProduct: orderedProducts) {
            OrderedProduct oProd = new OrderedProduct(orderedProduct.getProductId(), orderedProduct.getAmount(), orderedProduct.getPromoCode());
            productsForOrder.add(oProd);
        }
        order.setProducts(productsForOrder);

    }

}
