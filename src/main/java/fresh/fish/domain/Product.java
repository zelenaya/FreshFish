package fresh.fish.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Product {

    private Long productId;
    private String prodTitle;
    private String prodName;
    private String prodDescription;

    private String measure;

    private Long amount;
    private String lot;
    private String productionPlace;
    private Timestamp deliveryDate;
    private Long costPrice;
    private Long price;

    private String urlPhoto;
    private boolean isAvailable;

    public Product() {
    }

    public Product(Long productId, String prodTitle, String prodName, String prodDescription, String measure, Long amount, String lot, String productionPlace, Timestamp deliveryDate, Long costPrice, Long price, String urlPhoto, boolean isAvailable) {
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
    }

    public Product(Long productId, String prodTitle, String prodName, String prodDescription, String measure, Long amount, String lot, String productionPlace, Timestamp deliveryDate, Long costPrice, Long price) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return isAvailable == product.isAvailable &&
                Objects.equals(productId, product.productId) &&
                Objects.equals(prodTitle, product.prodTitle) &&
                Objects.equals(prodName, product.prodName) &&
                Objects.equals(prodDescription, product.prodDescription) &&
                Objects.equals(measure, product.measure) &&
                Objects.equals(amount, product.amount) &&
                Objects.equals(lot, product.lot) &&
                Objects.equals(productionPlace, product.productionPlace) &&
                Objects.equals(deliveryDate, product.deliveryDate) &&
                Objects.equals(costPrice, product.costPrice) &&
                Objects.equals(price, product.price) &&
                Objects.equals(urlPhoto, product.urlPhoto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, prodTitle, prodName, prodDescription, measure, amount, lot, productionPlace, deliveryDate, costPrice, price, urlPhoto, isAvailable);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
