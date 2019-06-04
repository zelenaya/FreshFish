package fresh.fish.controller.requests;

import java.sql.Timestamp;
import java.util.Date;

public class ProductCreateRequest {

    private String prodTitle;
    private String prodName;
    private String prodDescription;

    private String measure;

    private Long amount;
    private String lot;
    private String productionPlace;
    private Date deliveryDate;
    private Long costPrice;
    private Long price;

    private String urlPhoto;

    public ProductCreateRequest() {
    }

    public ProductCreateRequest(String prodTitle, String prodName, String prodDescription, String measure, Long amount, String lot, String productionPlace, Timestamp deliveryDate, Long costPrice, Long price, String urlPhoto) {
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

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
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
}
