package fresh.fish.controller.requests;

import fresh.fish.domain.jdbc_template.OrderedProduct;

import java.sql.Timestamp;
import java.util.List;

public class ProductTuOrderSearchCriteria {

    private Long productId;
    private Long amount;
    private String promoCode;

    public ProductTuOrderSearchCriteria() {
    }

    public ProductTuOrderSearchCriteria(Long productId, Long amount, String promoCode) {
        this.productId = productId;
        this.amount = amount;
        this.promoCode = promoCode;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }
}
