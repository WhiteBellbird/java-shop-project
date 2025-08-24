package domain;

import exception.OrderAlreadyProcessingException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Order implements Serializable {
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

    public static Order craeteOrder(User user, 
    								CartItem cartItem,
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
        if (LocalDateTime.now().isAfter(this.orderDate.plusHours(12))) {
            throw new OrderAlreadyProcessingException("주문은 12시간 이후에는 취소할 수 없습니다.");
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n================== [주문 정보] ==================\n");
        sb.append(String.format("주문 ID       : %s\n", orderId));
        sb.append(String.format("사용자        : %s\n", user.getUsername()));
        sb.append(String.format("상품          : %s\n", cartItem.getProduct().getName()));
        sb.append(String.format("수량          : %d\n", cartItem.getQuantity()));
        sb.append(String.format("주소          : %s\n", address));
        sb.append(String.format("상태          : %s\n", status));
        sb.append(String.format("주문 날짜     : %s\n", orderDate));
        sb.append("=================================================\n");
        return sb.toString();
    }
}
