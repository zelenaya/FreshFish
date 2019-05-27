package fresh.fish.domain.jdbc_template;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;
import java.util.Objects;

public class Promo {

    private Long promoId;
    private String promoName;
    private String promoDescription;
    private Long productId;
    private Long limitedAmount;
    private Long discount;
    private String promoCode;

    private Timestamp dateCreated;
    private Timestamp dateClose;
    private Long updateByUser;

    public Promo() {
    }

    public Promo(Long promoId, String promoName, String promoDescription, Long productId, Long limitedAmount, Long discount, String promoCode, Timestamp dateCreated, Timestamp dateClose, Long updateByUser) {
        this.promoId = promoId;
        this.promoName = promoName;
        this.promoDescription = promoDescription;
        this.productId = productId;
        this.limitedAmount = limitedAmount;
        this.discount = discount;
        this.promoCode = promoCode;
        this.dateCreated = dateCreated;
        this.dateClose = dateClose;
        this.updateByUser = updateByUser;
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

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getDateClose() {
        return dateClose;
    }

    public void setDateClose(Timestamp dateClose) {
        this.dateClose = dateClose;
    }

    public Long getUpdateByUser() {
        return updateByUser;
    }

    public void setUpdateByUser(Long updateByUser) {
        this.updateByUser = updateByUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promo promo = (Promo) o;
        return Objects.equals(promoId, promo.promoId) &&
                Objects.equals(promoName, promo.promoName) &&
                Objects.equals(promoDescription, promo.promoDescription) &&
                Objects.equals(productId, promo.productId) &&
                Objects.equals(limitedAmount, promo.limitedAmount) &&
                Objects.equals(discount, promo.discount) &&
                Objects.equals(promoCode, promo.promoCode) &&
                Objects.equals(dateCreated, promo.dateCreated) &&
                Objects.equals(dateClose, promo.dateClose) &&
                Objects.equals(updateByUser, promo.updateByUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(promoId, promoName, promoDescription, productId, limitedAmount, discount, promoCode, dateCreated, dateClose, updateByUser);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
