package fresh.fish.controller.requests;

import fresh.fish.domain.jdbc_template.OrderedProduct;

import java.util.List;

public class HibOrderCreateRequest {

    private Long orderId;
    private Long customerId;
    private Long persDiscountId;

    private String currencyCode;

    private Long statusId;

    private List<String> statusHistory;
    private List <OrderedProduct> products;
    private List <Long> promoId;

    public HibOrderCreateRequest() {
    }

    public HibOrderCreateRequest(Long orderId, Long customerId, Long persDiscountId, String currencyCode, Long statusId, List<String> statusHistory, List<OrderedProduct> products, List<Long> promoId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.persDiscountId = persDiscountId;
        this.currencyCode = currencyCode;
        this.statusId = statusId;
        this.statusHistory = statusHistory;
        this.products = products;
        this.promoId = promoId;
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

    public List<String> getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(List<String> statusHistory) {
        this.statusHistory = statusHistory;
    }

    public List<OrderedProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderedProduct> products) {
        this.products = products;
    }

    public List<Long> getPromoId() {
        return promoId;
    }

    public void setPromoId(List<Long> promoId) {
        this.promoId = promoId;
    }
}
