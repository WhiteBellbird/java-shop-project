package repository;

import domain.CartItem;
import domain.Order;

import java.util.List;
import java.util.Map;

public interface OrderRepository {

    public Order saveOrder(Order order);

    public Order replaceOrder(Order previousOrder, Order changedOrder);

    public Order updateOrder(Order order);
    
    public List<Order> getOrder();
    
    public Order getOrderByOrderId(String orderId);
    
    public void rollback();
    
    public void commit();
}
