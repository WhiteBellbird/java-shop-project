package repository;

import domain.CartItem;
import domain.Order;

import java.util.List;
import java.util.Map;

public interface OrderRepository {

    Order saveOrder(Order order);

    Order replaceOrder(Order previousOrder, Order changedOrder);

    Order updateOrder(Order order);
    
    List<Order> getOrder();
    
    Order getOrderByOrderId(String orderId);

    List<Order> getOrderByUserId(String userId);

    void rollback();
    
    void commit();

    void clearAll();
}
