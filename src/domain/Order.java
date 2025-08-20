package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {
    private String orderId;
    private User user;
    private Cart cart;
    private String address;
    private String status;
    private LocalDateTime orderDate;
  
    public Order(String orderId, User user, Cart cart, String address, String status , LocalDateTime orderDate) {
        this.orderId = orderId;
        this.user = user;
        this.cart = cart;
        this.status = status;
        this.address = address;
        this.orderDate = orderDate;
    }
    public void changeAddress(String newAddress){
        address = newAddress;
    }
    public String getOrderId() {
        return orderId;
    }
    public User getUser() {
        return user;
    }
    public Cart getCart() {
        return cart;
    }
    public String getAddress() {
        return address;
    }
    public void setStatus(String currentStatus) {
        this.status = currentStatus;
    }
    public String getStatus() {
        return status;
    }
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
}
