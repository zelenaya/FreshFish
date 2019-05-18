package fresh.fish.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;
import java.util.Objects;

public class PersonalDiscount {

    private Long persDiscountId;
    private Long customerId;
    private Long discountId;

    private Timestamp dateCreated;
    private Timestamp dateClose;
    private Long updateByUser;

    public PersonalDiscount() {
    }

    public PersonalDiscount(Long persDiscountId, Long customerId, Long discountId, Timestamp dateCreated, Timestamp dateClose, Long updateByUser) {
        this.persDiscountId = persDiscountId;
        this.customerId = customerId;
        this.discountId = discountId;
        this.dateCreated = dateCreated;
        this.dateClose = dateClose;
        this.updateByUser = updateByUser;
    }

    public Long getPersDiscountId() {
        return persDiscountId;
    }

    public void setPersDiscountId(Long persDiscountId) {
        this.persDiscountId = persDiscountId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
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
        PersonalDiscount that = (PersonalDiscount) o;
        return Objects.equals(persDiscountId, that.persDiscountId) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(discountId, that.discountId) &&
                Objects.equals(dateCreated, that.dateCreated) &&
                Objects.equals(dateClose, that.dateClose) &&
                Objects.equals(updateByUser, that.updateByUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persDiscountId, customerId, discountId, dateCreated, dateClose, updateByUser);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
