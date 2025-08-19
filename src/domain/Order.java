package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {
    private int orderId;
    private User user;
    private Cart items;
    private String address;
    private String status;
    private LocalDateTime orderDate;

    public void OrderStatus(String newStatus){
        status = newStatus;
    }

    public void CreateOrderId(){
        //orderId = ;
    }

    public void changeAddress(String newAddress){
        address = newAddress;
    }

    public Order(int orderId, User user, Cart items, String address, String status, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.user = user;
        this.items = items;
        this.address = address;
        this.status = status;
        this.orderDate = orderDate;
    }

    //getter
    public int getOrderId() {
        return orderId;
    }

    public User getUser() {
        return user;
    }

    public Cart getItems() {
        return items;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }


}
