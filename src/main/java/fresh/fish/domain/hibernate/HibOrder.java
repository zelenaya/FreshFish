package fresh.fish.domain.hibernate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fresh.fish.domain.jdbc_template.OrderedProduct;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
public class HibOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private HibCustomer customer;

    @Column(name = "pers_discount_id")
    private Long persDiscountId;

    @Column(name = "order_currency_code")
    private String currencyCode;

    @Column(name = "order_status_id")
    private Long statusId;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<HibProduct> products = Collections.emptySet();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "order_promo",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "promo_id")
    )
    private Set <HibPromo> promos;

    public HibOrder() {
    }

    public HibOrder(HibCustomer customer, Long persDiscountId, String currencyCode, Long statusId, Set<HibProduct> products, Set<HibPromo> promos) {
        this.customer = customer;
        this.persDiscountId = persDiscountId;
        this.currencyCode = currencyCode;
        this.statusId = statusId;
        this.products = products;
        this.promos = promos;
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

    public Set<HibProduct> getProducts() {
        return products;
    }

    public void setProducts(Set<HibProduct> products) {
        this.products = products;
    }

    public Set<HibPromo> getPromos() {
        return promos;
    }

    public void setPromos(Set<HibPromo> promos) {
        this.promos = promos;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HibOrder hibOrder = (HibOrder) o;
        return Objects.equals(orderId, hibOrder.orderId) &&
                Objects.equals(customer, hibOrder.customer) &&
                Objects.equals(persDiscountId, hibOrder.persDiscountId) &&
                Objects.equals(currencyCode, hibOrder.currencyCode) &&
                Objects.equals(statusId, hibOrder.statusId) &&
                Objects.equals(products, hibOrder.products) &&
                Objects.equals(promos, hibOrder.promos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customer, persDiscountId, currencyCode, statusId, products, promos);
    }
}
