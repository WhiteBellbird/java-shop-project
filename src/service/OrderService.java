package service;

import domain.Order;
import domain.Product;

import java.util.HashMap;
import java.util.List;

public interface OrderService {

    Order createOrder(String userId, String productName, int amount, int quantity, String address);

    Boolean CreateSomeOrders(String userId);

    Boolean CreateAllOrders(String userId,int totalAmount,String address);

    List<Order> DisplayOrderList(String userId);

    Boolean CancelOrder(String orderId);
}
