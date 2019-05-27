package fresh.fish.domain.jdbc_template;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Objects;

public class Order {

    private Long orderId;
    private Long customerId;
    private Long persDiscountId;

    private String currencyCode;

    private Long statusId;

    private List <String> statusHistory;
    private List <OrderedProduct> products;
    private List <Long> promoId;

    public Order() {
    }

    public Order(Long orderId, Long customerId, Long persDiscountId, String currencyCode, Long statusId, List<OrderedProduct> products) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.persDiscountId = persDiscountId;
        this.currencyCode = currencyCode;
        this.statusId = statusId;
        this.products = products;
    }

    public Order(Long orderId, Long customerId, Long persDiscountId, String currencyCode, Long statusId, List<String> statusHistory, List<OrderedProduct> products, List<Long> promoId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) &&
                Objects.equals(customerId, order.customerId) &&
                Objects.equals(persDiscountId, order.persDiscountId) &&
                Objects.equals(currencyCode, order.currencyCode) &&
                Objects.equals(statusId, order.statusId) &&
                Objects.equals(statusHistory, order.statusHistory) &&
                Objects.equals(products, order.products) &&
                Objects.equals(promoId, order.promoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customerId, persDiscountId, currencyCode, statusId, statusHistory, products, promoId);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }



}
