package fresh.fish.controller.requests;

import java.util.Objects;

public class PromoCreateRequest {

    private Long promoId;
    private String promoName;
    private String promoDescription;
    private Long productId;
    private Long limitedAmount;
    private Long discount;
    private String promoCode;

    public PromoCreateRequest() {
    }

    public PromoCreateRequest(Long promoId, String promoName, String promoDescription, Long productId, Long limitedAmount, Long discount, String promoCode) {
        this.promoId = promoId;
        this.promoName = promoName;
        this.promoDescription = promoDescription;
        this.productId = productId;
        this.limitedAmount = limitedAmount;
        this.discount = discount;
        this.promoCode = promoCode;
    }

    public Long getPromoId() {
        return promoId;
    }

    public void setPromoId(Long promoId) {
        this.promoId = promoId;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public String getPromoDescription() {
        return promoDescription;
    }

    public void setPromoDescription(String promoDescription) {
        this.promoDescription = promoDescription;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getLimitedAmount() {
        return limitedAmount;
    }

    public void setLimitedAmount(Long limitedAmount) {
        this.limitedAmount = limitedAmount;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromoCreateRequest that = (PromoCreateRequest) o;
        return Objects.equals(promoId, that.promoId) &&
                Objects.equals(promoName, that.promoName) &&
                Objects.equals(promoDescription, that.promoDescription) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(limitedAmount, that.limitedAmount) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(promoCode, that.promoCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(promoId, promoName, promoDescription, productId, limitedAmount, discount, promoCode);
    }

    @Override
    public String toString() {
        return "PromoCreateRequest{" +
                "promoId=" + promoId +
                ", promoName='" + promoName + '\'' +
                ", promoDescription='" + promoDescription + '\'' +
                ", productId=" + productId +
                ", limitedAmount=" + limitedAmount +
                ", discount=" + discount +
                ", promoCode='" + promoCode + '\'' +
                '}';
    }
}
