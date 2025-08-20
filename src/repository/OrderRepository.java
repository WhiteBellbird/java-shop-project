package repository;

import domain.CartItem;
import domain.Order;

import java.util.Map;

public interface OrderRepository {

    public Order saveOrder(Map<String, CartItem> items);

    public Order updateOrder();

    public Order getOrder();
}
