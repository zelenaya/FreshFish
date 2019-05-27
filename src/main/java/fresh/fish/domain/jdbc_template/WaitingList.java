package fresh.fish.domain.jdbc_template;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Objects;

@Repository
@Transactional
public class WaitingList {

    private Long waitingId;
    private Long customerId;
    private Long productId;
    private Long count;

    private Timestamp dateCreated;
    private Timestamp dateClose;

    public WaitingList() {
    }

    public WaitingList(Long waitingId, Long customerId, Long productId, Long count, Timestamp dateCreated, Timestamp dateClose) {
        this.waitingId = waitingId;
        this.customerId = customerId;
        this.productId = productId;
        this.count = count;
        this.dateCreated = dateCreated;
        this.dateClose = dateClose;
    }

    public Long getWaitingId() {
        return waitingId;
    }

    public void setWaitingId(Long waitingId) {
        this.waitingId = waitingId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaitingList that = (WaitingList) o;
        return Objects.equals(waitingId, that.waitingId) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(count, that.count) &&
                Objects.equals(dateCreated, that.dateCreated) &&
                Objects.equals(dateClose, that.dateClose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(waitingId, customerId, productId, count, dateCreated, dateClose);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
