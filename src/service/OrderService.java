package service;

import domain.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(String userId, String productName, int amount, int quantity, String address);

    Boolean CreateSomeOrders(String userId);

    List<Order> CreateAllOrders(String userId,int totalAmount,String address);

    List<Order> DisplayOrderList(String userId);

    Order cancelOrder(String orderId);
}
