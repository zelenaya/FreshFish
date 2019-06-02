package fresh.fish.service;

import fresh.fish.domain.jdbc_template.Customer;
import fresh.fish.domain.jdbc_template.Order;
import fresh.fish.domain.jdbc_template.OrderedProduct;

import java.util.List;

public class OrderService {

    private Customer customer;
    private Order order;

    public OrderService() {
    }

    public OrderService(Customer customer, Order order) {
        this.customer = customer;
        this.order = order;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderService{" +
                "customer=" + customer +
                ", order=" + order +
                '}';
    }
}
