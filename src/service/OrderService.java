package service;

import domain.Product;

import java.util.HashMap;

public interface OrderService {

    void CreateSomeOrders(String userId);

    void CreateAllOrders(String userId);

    void DisplayOrderList(String userId);

    void CancelOrder(String orderId);
}
