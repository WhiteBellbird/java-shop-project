package domain;

import exception.OrderAlreadyProcessingException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Order {
    private String orderId;
    private User user;
    private CartItem cartItem;
    private String address;
    private OrderStatus status;
    private LocalDateTime orderDate;

    public Order(String orderId, User user, CartItem cartItem, String address, OrderStatus status, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.user = user;
        this.cartItem = cartItem;
        this.status = status;
        this.address = address;
        this.orderDate = orderDate;
    }

    public static Order craeteOrder(User user, CartItem cartItem,
                                    String address,
                                    LocalDateTime orderDate) {
        String newOrderId = UUID.randomUUID().toString();
        Order order = new Order(newOrderId, user, cartItem, address, OrderStatus.PENDING, orderDate);
		return order;
    }

    public void changeAddress(String newAddress) {
        address = newAddress;
    }

    public String getOrderId() {
        return orderId;
    }

    public User getUser() {
        return user;
    }

    public void cancelOrder() {
        LocalDateTime orderDate = LocalDateTime.now().minusHours(12);
        if (orderDate.isBefore(this.orderDate)) {
            throw new OrderAlreadyProcessingException("Order has been cancelled");
        }
        this.status = OrderStatus.CANCELLED;

    }


    public CartItem getCartItem() {
        return cartItem;
    }

    public String getAddress() {
        return address;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Boolean isPending() {
        return status == OrderStatus.PENDING;
    }
    public  Boolean isComplete() {
        return status == OrderStatus.COMPLETED;
    }

    public  Boolean isCancelled() {
        return status == OrderStatus.CANCELLED;
    }

    public Boolean isProcessed() {
        return status == OrderStatus.PROCESSING;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }
}
