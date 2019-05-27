package fresh.fish.controller.requests;

import java.sql.Timestamp;
import java.util.Objects;

public class WaitingListCreateRequest {

    private Long waitingId;
    private Long customerId;
    private Long productId;
    private Long count;

    public WaitingListCreateRequest() {
    }

    public WaitingListCreateRequest(Long waitingId, Long customerId, Long productId, Long count) {
        this.waitingId = waitingId;
        this.customerId = customerId;
        this.productId = productId;
        this.count = count;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaitingListCreateRequest that = (WaitingListCreateRequest) o;
        return Objects.equals(waitingId, that.waitingId) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(waitingId, customerId, productId, count);
    }

    @Override
    public String toString() {
        return "WaitingListCreateRequest{" +
                "waitingId=" + waitingId +
                ", customerId=" + customerId +
                ", productId=" + productId +
                ", count=" + count +
                '}';
    }
}
