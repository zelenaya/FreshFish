package fresh.fish.domain.hibernate;

//import org.hibernate.annotations.Tables;
//import org.hibernate.annotations.Table;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name ="products")
@SecondaryTable(name = "order_products")

//@SecondaryTables({
//        @SecondaryTable(name="order_products", pkJoinColumns=
//                @PrimaryKeyJoinColumn(name="product_id")),
//        @SecondaryTable(name="orders", pkJoinColumns={
//                @PrimaryKeyJoinColumn(name="order_id") })
//})

public class HibOrderedProduct {
   // @Id
//    @Column(name = "order_id", table= "orders")
//    private Long orderId;

    @Column(name = "order_id", table= "order_products")
    private Long orderId;

    @Id
    @Column(name = "product_id", table= "products")
    private Long productId;

    @Column(name = "prod_name", table= "products")
    private String productName;

    @Column(name = "count", table= "order_products")
    private Long count;
    @Column(name = "promo_id", table= "order_products")
    private Long promoId;

    @Column(name = "price", table= "order_products")
    private Long price;

    public HibOrderedProduct() {
    }

    public HibOrderedProduct(Long orderId, Long productId, String productName, Long count, Long promoId, Long price) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.count = count;
        this.promoId = promoId;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getPromoId() {
        return promoId;
    }

    public void setPromoId(Long promoId) {
        this.promoId = promoId;
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
                Objects.equals(productName, that.productName) &&
                Objects.equals(count, that.count) &&
                Objects.equals(promoId, that.promoId) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, productName, count, promoId, price);
    }

    @Override
    public String toString() {
        return "HibOrderedProduct{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", productName=" + productName +
                ", count=" + count +
                ", promoId=" + promoId +
                ", price=" + price +
                '}';
    }
}
