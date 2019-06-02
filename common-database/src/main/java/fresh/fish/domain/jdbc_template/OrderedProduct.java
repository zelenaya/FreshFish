package fresh.fish.domain.jdbc_template;

import java.util.Objects;

public class OrderedProduct {

    private Long productId;
    private String productName;

    private Long count;
    private Long promoId;
    private String promoCode;
    private Long discount;
    private Long price;

    public OrderedProduct() {
    }

    public OrderedProduct(Long productId, Long count, String promoCode) {
        this.productId = productId;
        this.count = count;
        this.promoCode = promoCode;
    }

    public OrderedProduct(Long productId, String productName, Long count, Long promoId, String promoCode, Long discount, Long price) {
        this.productId = productId;
        this.productName = productName;
        this.count = count;
        this.promoId = promoId;
        this.promoCode = promoCode;
        this.discount = discount;
        this.price = price;
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

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
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
        OrderedProduct that = (OrderedProduct) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(count, that.count) &&
                Objects.equals(promoId, that.promoId) &&
                Objects.equals(promoCode, that.promoCode) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, count, promoId, promoCode, discount, price);
    }

    @Override
    public String toString() {
        return "OrderedProduct{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", count=" + count +
                ", promoId=" + promoId +
                ", promoCode='" + promoCode + '\'' +
                ", discount=" + discount +
                ", price=" + price +
                '}';
    }
}
