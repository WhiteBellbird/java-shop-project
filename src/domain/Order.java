package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {
    private String orderId;
    private User user;
    private ArrayList <CartItem> items;
    private String address;
    private String status;
    private LocalDateTime orderDate;

    public void OrderStatus(String newStatus){
        status = newStatus;
    }

    public void changeAddress(String newAddress){
        address = newAddress;
    }

    public Order(){

    }
}
