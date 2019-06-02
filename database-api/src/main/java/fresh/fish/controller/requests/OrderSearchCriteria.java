package fresh.fish.controller.requests;

import fresh.fish.domain.jdbc_template.OrderedProduct;

import java.util.List;

public class OrderSearchCriteria {

    private Long customerId;
    private Long persDiscountId;

    private String currencyCode;

    private List <ProductTuOrderSearchCriteria> products;

    public OrderSearchCriteria() {
    }

    public OrderSearchCriteria(Long customerId, Long persDiscountId, String currencyCode, List<ProductTuOrderSearchCriteria> products) {
        this.customerId = customerId;
        this.persDiscountId = persDiscountId;
        this.currencyCode = currencyCode;
        this.products = products;
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

    public List<ProductTuOrderSearchCriteria> getProducts() {
        return products;
    }

    public void setProducts(List<ProductTuOrderSearchCriteria> products) {
        this.products = products;
    }
}
