package service;

import domain.Product;

import java.util.HashMap;

public interface OrderService {



    void IndividualOrder();

    void AllOrder(String sessionId);

    void DisplayOrderList(String orderId);

    void CancelOrder();
}
