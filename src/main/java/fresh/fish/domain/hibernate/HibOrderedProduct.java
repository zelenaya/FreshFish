package fresh.fish.domain.hibernate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

//@Entity
//@Table(name = "customers")
public class HibOrderedProduct {

    private Long orderId;
    private Long productId;
    private Long count;
    private Long price;

    public HibOrderedProduct() {
    }

    public HibOrderedProduct(Long orderId, Long productId, Long count, Long price) {
        this.orderId = orderId;
        this.productId = productId;
        this.count = count;
        this.price = price;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HibOrderedProduct that = (HibOrderedProduct) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(count, that.count) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, count, price);
    }

    @Override
    public String toString() {
        return "OrderedProduct{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
