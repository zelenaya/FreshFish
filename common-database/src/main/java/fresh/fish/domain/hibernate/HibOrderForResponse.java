package fresh.fish.domain.hibernate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fresh.fish.domain.jdbc_template.OrderedProduct;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;



public class HibOrderForResponse {


    private Long orderId;
    private HibCustomer customer;
    private Long persDiscountId;
    private String currencyCode;
    private Long statusId;

    private Long totalPrice;

    private List<OrderedProduct> products;

    public HibOrderForResponse() {
    }

    public HibOrderForResponse(Long orderId, HibCustomer customer, Long persDiscountId, String currencyCode, Long statusId, Long totalPrice, List<OrderedProduct> products) {
        this.orderId = orderId;
        this.customer = customer;
        this.persDiscountId = persDiscountId;
        this.currencyCode = currencyCode;
        this.statusId = statusId;
        this.totalPrice = totalPrice;
        this.products = products;
    }

    public HibOrderForResponse(Long orderId, Long persDiscountId, String currencyCode, Long statusId) {
        this.orderId = orderId;
        this.persDiscountId = persDiscountId;
        this.currencyCode = currencyCode;
        this.statusId = statusId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public HibCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(HibCustomer customer) {
        this.customer = customer;
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

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderedProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderedProduct> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HibOrderForResponse that = (HibOrderForResponse) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(persDiscountId, that.persDiscountId) &&
                Objects.equals(currencyCode, that.currencyCode) &&
                Objects.equals(statusId, that.statusId) &&
                Objects.equals(totalPrice, that.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customer, persDiscountId, currencyCode, statusId, totalPrice);
    }

    @Override
    public String toString() {
        return "HibOrderForResponse{" +
                "orderId=" + orderId +
                ", customer=" + customer +
                ", persDiscountId=" + persDiscountId +
                ", currencyCode='" + currencyCode + '\'' +
                ", statusId=" + statusId +
                ", totalPrice=" + totalPrice +
                ", products=" + products +
                '}';
    }
}
