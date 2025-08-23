package service;

import domain.Order;
import domain.Product;

import java.util.HashMap;
import java.util.List;

public interface OrderService {

    Order createOrder(String userId, String productName, int amount, int quantity, String address);



    void createAllOrders(String userId, int userTotalAmount, String address);

    List<Order> displayOrderList(String userId);

    void cancelOrder(String orderId);

    @Deprecated
    void createSomeOrders(String userId);
}
