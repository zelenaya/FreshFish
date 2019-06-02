package fresh.fish.service;

import fresh.fish.domain.jdbc_template.Order;
import fresh.fish.domain.jdbc_template.Product;
import fresh.fish.domain.jdbc_template.Promo;

import java.util.List;

public class PromoService {

    private Promo promo;

    private List<Order> order;
    private List <Product> products;

    public PromoService() {
    }

    public PromoService(Promo promo, List<Order> order, List<Product> products) {
        this.promo = promo;
        this.order = order;
        this.products = products;
    }

    public Promo getPromo() {
        return promo;
    }

    public void setPromo(Promo promo) {
        this.promo = promo;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
