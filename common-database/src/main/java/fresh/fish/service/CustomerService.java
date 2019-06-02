package fresh.fish.service;

import fresh.fish.domain.jdbc_template.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "customerService")
public class CustomerService {

    private Customer customer;
    private User user;

    private List <Role> roles;
    private List<Order> orders;
    private List<OrderedProduct> orderedProducts;

    public CustomerService() {
    }

    public CustomerService(Customer customer) {
        this.customer = customer;
    }

    public CustomerService(Customer customer, User user, List<Role> roles, List<Order> orders, List<OrderedProduct> orderedProducts) {
        this.customer = customer;
        this.user = user;
        this.roles = roles;
        this.orders = orders;
        this.orderedProducts = orderedProducts;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }


}
















