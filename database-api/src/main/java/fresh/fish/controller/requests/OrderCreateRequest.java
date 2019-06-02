package fresh.fish.controller.requests;

import fresh.fish.domain.jdbc_template.OrderedProduct;

import java.util.List;

public class OrderCreateRequest {

    private Long orderId;
    private Long customerId;
    private Long persDiscountId;

    private String currencyCode;
    private Long statusId;

    private List <OrderedProduct> products;

    public OrderCreateRequest() {
    }

    public OrderCreateRequest(Long orderId, Long customerId, Long persDiscountId, String currencyCode, Long statusId, List<String> statusHistory, List<OrderedProduct> products, List<Long> promoId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.persDiscountId = persDiscountId;
        this.currencyCode = currencyCode;
        this.statusId = statusId;
        this.products = products;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getPersDiscountId() {
        return persDiscountId;
    }

    public void setPersDiscountId(Long persDiscountId) {
        this.persDiscountId = persDiscountId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public List<OrderedProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderedProduct> products) {
        this.products = products;
    }

}
