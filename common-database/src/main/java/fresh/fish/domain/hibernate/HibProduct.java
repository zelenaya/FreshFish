package fresh.fish.domain.hibernate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "products")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "productId")
public class HibProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "prod_title")
    private String prodTitle;

    @Column(name = "prod_name")
    private String prodName;

    @Column(name = "prod_describing")
    private String prodDescription;

    @Column(name = "measure")
    private String measure;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "lot")
    private String lot;

    @Column(name = "production_place")
    private String productionPlace;

    @Column(name = "delivery_date")
    private Timestamp deliveryDate;

    @Column(name = "cost_price")
    private Long costPrice;

    @Column(name = "price")
    private Long price;

    @Column(name = "url_photo")
    private String urlPhoto;

    @Column(name = "is_aviable")
    private boolean isAvailable;

    @JsonManagedReference
    @ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
    private Set<HibOrder> orders = Collections.emptySet();

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "product")
    private Set<HibPromo> promos = Collections.emptySet();




//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "product")
//    private Set<HibWaitingList> hibWaitingLists = Collections.emptySet();

    public HibProduct() {
    }



    public HibProduct(Long productId, String prodTitle, String prodName, String prodDescription, String measure, Long amount, String lot, String productionPlace, Timestamp deliveryDate, Long costPrice, Long price, String urlPhoto, boolean isAvailable, Set<HibOrder> orders, Set<HibPromo> promos) {
        this.productId = productId;
        this.prodTitle = prodTitle;
        this.prodName = prodName;
        this.prodDescription = prodDescription;
        this.measure = measure;
        this.amount = amount;
        this.lot = lot;
        this.productionPlace = productionPlace;
        this.deliveryDate = deliveryDate;
        this.costPrice = costPrice;
        this.price = price;
        this.urlPhoto = urlPhoto;
        this.isAvailable = isAvailable;
//        this.orders = orders;
//        this.promos = promos;
    }

    public HibProduct(Long productId, String prodTitle, String prodName, String prodDescription, String measure, Long amount, String lot, String productionPlace, Timestamp deliveryDate, Long costPrice, Long price) {
        this.productId = productId;
        this.prodTitle = prodTitle;
        this.prodName = prodName;
        this.prodDescription = prodDescription;
        this.measure = measure;
        this.amount = amount;
        this.lot = lot;
        this.productionPlace = productionPlace;
        this.deliveryDate = deliveryDate;
        this.costPrice = costPrice;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProdTitle() {
        return prodTitle;
    }

    public void setProdTitle(String prodTitle) {
        this.prodTitle = prodTitle;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdDescription() {
        return prodDescription;
    }

    public void setProdDescription(String prodDescription) {
        this.prodDescription = prodDescription;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getProductionPlace() {
        return productionPlace;
    }

    public void setProductionPlace(String productionPlace) {
        this.productionPlace = productionPlace;
    }

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Long costPrice) {
        this.costPrice = costPrice;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Set<HibOrder> getOrders() {
        return orders;
    }

    public void setOrders(Set<HibOrder> orders) {
        this.orders = orders;
    }

    public Set<HibPromo> getPromos() {
        return promos;
    }

    public void setPromos(Set<HibPromo> promos) {
        this.promos = promos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HibProduct hibProduct = (HibProduct) o;
        return isAvailable == hibProduct.isAvailable &&
                Objects.equals(productId, hibProduct.productId) &&
                Objects.equals(prodTitle, hibProduct.prodTitle) &&
                Objects.equals(prodName, hibProduct.prodName) &&
                Objects.equals(prodDescription, hibProduct.prodDescription) &&
                Objects.equals(measure, hibProduct.measure) &&
                Objects.equals(amount, hibProduct.amount) &&
                Objects.equals(lot, hibProduct.lot) &&
                Objects.equals(productionPlace, hibProduct.productionPlace) &&
                Objects.equals(deliveryDate, hibProduct.deliveryDate) &&
                Objects.equals(costPrice, hibProduct.costPrice) &&
                Objects.equals(price, hibProduct.price) &&
                Objects.equals(urlPhoto, hibProduct.urlPhoto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, prodTitle, prodName, prodDescription, measure, amount, lot, productionPlace, deliveryDate, costPrice, price, urlPhoto, isAvailable);
    }

//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
//    }


    @Override
    public String toString() {
        return "HibProduct{" +
                "productId=" + productId +
                ", prodTitle='" + prodTitle + '\'' +
                ", prodName='" + prodName + '\'' +
                ", prodDescription='" + prodDescription + '\'' +
                ", measure='" + measure + '\'' +
                ", amount=" + amount +
                ", lot='" + lot + '\'' +
                ", productionPlace='" + productionPlace + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", costPrice=" + costPrice +
                ", price=" + price +
                ", urlPhoto='" + urlPhoto + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
