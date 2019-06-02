package fresh.fish.domain.jdbc_template;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;
import java.util.Objects;

public class DiscountSize {

    private Long discountId;
    private Long amount;
    private Long discount;
    private Timestamp date_created;
    private Timestamp date_close;

    private Long updateById;

    public DiscountSize() {
    }

    public DiscountSize(Long discountId, Long amount, Long discount, Timestamp date_created, Timestamp date_close, Long updateById) {
        this.discountId = discountId;
        this.amount = amount;
        this.discount = discount;
        this.date_created = date_created;
        this.date_close = date_close;
        this.updateById = updateById;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Timestamp getDate_created() {
        return date_created;
    }

    public void setDate_created(Timestamp date_created) {
        this.date_created = date_created;
    }

    public Timestamp getDate_close() {
        return date_close;
    }

    public void setDate_close(Timestamp date_close) {
        this.date_close = date_close;
    }

    public Long getUpdateById() {
        return updateById;
    }

    public void setUpdateById(Long updateById) {
        this.updateById = updateById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountSize that = (DiscountSize) o;
        return Objects.equals(discountId, that.discountId) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(date_created, that.date_created) &&
                Objects.equals(date_close, that.date_close) &&
                Objects.equals(updateById, that.updateById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountId, amount, discount, date_created, date_close, updateById);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
