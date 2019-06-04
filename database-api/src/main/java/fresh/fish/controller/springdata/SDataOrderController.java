package fresh.fish.controller.springdata;


import fresh.fish.domain.hibernate.*;
import fresh.fish.domain.jdbc_template.Order;
import fresh.fish.domain.jdbc_template.OrderedProduct;
import fresh.fish.repository.hibernate.HibCustomerDao;
import fresh.fish.repository.hibernate.HibOrderDao;
import fresh.fish.controller.requests.HibOrderCreateRequest;
import fresh.fish.controller.requests.SearchCriteria;
import fresh.fish.repository.hibernate.HibPromoDao;
import fresh.fish.repository.jdbc_template.OrderDao;
import fresh.fish.repository.springdata.HibOrderedProductRepository;
import fresh.fish.repository.springdata.OrderRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping(value = "/rest/springdata/orders")
public class SDataOrderController {

    @Autowired
    private OrderRepository orderRep;

    @Autowired
    private HibOrderedProductRepository orderProdRep;

    @Autowired
    private HibCustomerDao custDao;

    @Autowired
    private HibPromoDao promoDao;

    @Autowired
    private OrderDao jdbcOrderDao;

    @ApiOperation(value = "Get all orders with information about customers and lists of ordered products")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<HibOrderForResponse>> getOrders() {
        List<HibOrder> orders = orderRep.getHibOrders();

        List<HibOrderForResponse> ordersForResponse = new ArrayList<>();
        for (HibOrder order: orders) {
            List<HibOrderedProduct> orderedProduct = orderRep.getHibOrdersProduct(order.getOrderId());
            ordersForResponse.add(convertToHibOrderForResponse(order, orderedProduct));
        }
       return new ResponseEntity<>(ordersForResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "Find order by id with lists of ordered products")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity <HibOrderForResponse> getOrderById(@PathVariable Long id) {
        Optional hibOptPromo = orderRep.findById(id);
        if (hibOptPromo.isPresent()) {
            HibOrder order = (HibOrder) hibOptPromo.get();
            List<HibOrderedProduct> orderedProduct = orderRep.getHibOrdersProduct(order.getOrderId());
            return new ResponseEntity<>(convertToHibOrderForResponse(order, orderedProduct), HttpStatus.OK);
        } else return new ResponseEntity<>(new HibOrderForResponse(), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new order")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HibOrderForResponse> createOrder(@RequestBody HibOrderCreateRequest request) {
//        HibOrder order = new HibOrder();
//        setFields(order, request);
//        HibOrder savedOrder = orderRep.saveAndFlush(order);
//       //orderRep.saveOrderedProducts(request.getProducts());
//        List<HibOrderedProduct> orderedProduct = orderRep.getHibOrdersProduct(savedOrder.getOrderId());
//        return new ResponseEntity<>(convertToHibOrderForResponse(savedOrder, orderedProduct), HttpStatus.OK);

        Order order = new Order();
        order.setStatusId(1L);
        fillTheOrder(order, request);
        Order savedOrder = jdbcOrderDao.save(order);

        Optional hibOptPromo = orderRep.findById(savedOrder.getOrderId());
        HibOrder hibOrder = (HibOrder) hibOptPromo.get();
        List<HibOrderedProduct> orderedProduct = orderRep.getHibOrdersProduct(hibOrder.getOrderId());
        return new ResponseEntity<>(convertToHibOrderForResponse(hibOrder, orderedProduct), HttpStatus.OK);

    }

    @ApiOperation(value = "Update some info of the order by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HibOrderForResponse> updateOrder(@PathVariable("id")  Long orderId, @RequestBody HibOrderCreateRequest request) {
       Optional hibOptPromo = orderRep.findById(orderId);
//        HibOrder order = new HibOrder();
//        if (hibOptPromo.isPresent()) {
//            order = (HibOrder) hibOptPromo.get();
//            setFields(order, request);
//            orderRep.save(order);
//            List<HibOrderedProduct> hibOrderedProducts = orderRep.getHibOrdersProduct(orderId);
//            setHibFields(hibOrderedProducts, request, orderId);
//            for (HibOrderedProduct hibOrderedProd : hibOrderedProducts) {
//                orderProdRep.save(hibOrderedProd);
//            }
//            return new ResponseEntity<>(convertToHibOrderForResponse(order, hibOrderedProducts), HttpStatus.OK);
//        } else return new ResponseEntity<>(new HibOrderForResponse(), HttpStatus.OK);

        Order order = jdbcOrderDao.findById(orderId);
        setClearFields(order, request);
        jdbcOrderDao.update(order);
        //HibOrder hibOrder = orderDao.findById(orderId);
        HibOrder hibOrder = (HibOrder) hibOptPromo.get();
        List<HibOrderedProduct> orderedProduct = orderRep.getHibOrdersProduct(orderId);
        return new ResponseEntity<>(convertToHibOrderForResponse(hibOrder, orderedProduct), HttpStatus.OK);

    }

    @ApiOperation(value = "Oh no! It can delete order from DB by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteOrder (@PathVariable ("id") Long orderId) {
        Optional hibOptPromo = orderRep.findById(orderId);
        if (hibOptPromo.isPresent()) {
            HibOrder order = (HibOrder) hibOptPromo.get();
            orderRep.delete(order);}
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

//    @GetMapping("/search")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<List<HibOrder>> searchOrder(@ModelAttribute SearchCriteria criteria) {
//        return null;
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

    private HibOrderForResponse convertToHibOrderForResponse(HibOrder order, List<HibOrderedProduct> orderedProduct) {
        HibOrderForResponse orderForResponse = new HibOrderForResponse(order.getOrderId(), order.getPersDiscountId(), order.getCurrencyCode(), order.getStatusId());
        orderForResponse.setCustomer(custDao.findById(order.getCustomerId()));

        List<OrderedProduct> ordProducts = new ArrayList<>();
        Long totalPrice = 0L;
        for (HibOrderedProduct product: orderedProduct) {
            OrderedProduct ordProd = new OrderedProduct(product.getProductId(),product.getCount(),product.getPromoId(),product.getPrice(), product.getProductName());
            totalPrice = totalPrice + product.getCount()*product.getPrice();
            if(product.getPromoId()!=0) {
                HibPromo promo = promoDao.findById(product.getPromoId());
                ordProd.setPromoCode(promo.getPromoCode());
                ordProd.setDiscount(promo.getDiscount());
            }
            ordProducts.add(ordProd);
        }
        orderForResponse.setProducts(ordProducts);
        orderForResponse.setTotalPrice(totalPrice);
        return orderForResponse;
    }

    private void setHibFields(List<HibOrderedProduct> oldProducts, HibOrderCreateRequest req, Long orderId) {
        List<OrderedProduct> products = req.getProducts();
        for (OrderedProduct product: products) {
            boolean isExist = false;
            for (HibOrderedProduct oldProd: oldProducts) {
                if (oldProd.getProductId() == product.getProductId()) {
                    if (product.getCount()!=0) oldProd.setCount(product.getCount());
                    if (product.getPromoId()!=0) oldProd.setPromoId(product.getPromoId());
                    isExist = true;
                    break;
                }
            }
            if(!isExist) {
                oldProducts.add(new HibOrderedProduct(orderId,
                        product.getProductId(), product.getProductName(),
                        product.getCount(), product.getPromoId(), product.getPrice()));
            }
        }
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
