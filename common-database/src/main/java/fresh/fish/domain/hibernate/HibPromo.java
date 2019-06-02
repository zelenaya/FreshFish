package fresh.fish.domain.hibernate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "promo")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "promoId")
public class HibPromo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promo_id")
    private Long promoId;

    @Column(name = "promo_name")
    private String promoName;

    @Column(name = "promo_description")
    private String promoDescription;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promo_product_id")
    private HibProduct product;

    @Column(name = "limited_amount")
    private Long limitedAmount;

    @Column(name = "discount")
    private Long discount;

    @Column(name = "promo_code")
    private String promoCode;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "date_close")
    private Timestamp dateClose;

    @Column(name = "update_by_user_id")
    private Long updateByUser;

    @JsonBackReference
    @ManyToMany(mappedBy = "promos", fetch = FetchType.EAGER)
    private Set<HibOrder> orders = Collections.emptySet();

    public HibPromo() {
    }

    public HibPromo(Long promoId, String promoName, String promoDescription, HibProduct product, Long limitedAmount, Long discount, String promoCode, Timestamp dateCreated, Timestamp dateClose, Long updateByUser) {
        this.promoId = promoId;
        this.promoName = promoName;
        this.promoDescription = promoDescription;
        this.product = product;
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

    public HibProduct getProduct() {
        return product;
    }

    public void setProduct(HibProduct product) {
        this.product = product;
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
        HibPromo promo = (HibPromo) o;
        return Objects.equals(promoId, promo.promoId) &&
                Objects.equals(promoName, promo.promoName) &&
                Objects.equals(promoDescription, promo.promoDescription) &&
                Objects.equals(product, promo.product) &&
                Objects.equals(limitedAmount, promo.limitedAmount) &&
                Objects.equals(discount, promo.discount) &&
                Objects.equals(promoCode, promo.promoCode) &&
                Objects.equals(dateCreated, promo.dateCreated) &&
                Objects.equals(dateClose, promo.dateClose) &&
                Objects.equals(updateByUser, promo.updateByUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(promoId, promoName, promoDescription, product, limitedAmount, discount, promoCode, dateCreated, dateClose, updateByUser);
    }

//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
//    }

    @Override
    public String toString() {
        return "HibPromo{" +
                "promoId=" + promoId +
                ", promoName='" + promoName + '\'' +
                ", promoDescription='" + promoDescription + '\'' +
                ", product=" + product +
                ", limitedAmount=" + limitedAmount +
                ", discount=" + discount +
                ", promoCode='" + promoCode + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateClose=" + dateClose +
                ", updateByUser=" + updateByUser +
                '}';
    }
}
