package controller;

import domain.Order;
import domain.User;
import exception.InvalidatedInputException;
import exception.ShopException;
import service.OrderService;

import java.util.List;

public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public Order createOrder(User user, String productName, int amount, int quantity, String address) throws ShopException {
        if (user == null) {
            throw new InvalidatedInputException("User cannot be null.");
        }
        if (productName == null || productName.trim().isEmpty()) {
            throw new InvalidatedInputException("Product name cannot be empty.");
        }
        if (amount <= 0) {
            throw new InvalidatedInputException("Amount must be greater than 0.");
        }
        if (quantity <= 0) {
            throw new InvalidatedInputException("Quantity must be greater than 0.");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new InvalidatedInputException("Address cannot be empty.");
        }

        return orderService.createOrder(user.getUserId(), productName, amount, quantity, address);
    }

    public List<Order> createAllOrders(User user, int totalAmount, String address) throws ShopException {
        if (user == null) {
            throw new InvalidatedInputException("User cannot be null.");
        }
        if (totalAmount <= 0) {
            throw new InvalidatedInputException("Total amount must be greater than 0.");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new InvalidatedInputException("Address cannot be empty.");
        }

        return orderService.CreateAllOrders(user.getUserId(), totalAmount, address);
    }

    public List<Order> displayOrderList(User user) throws ShopException {
        if (user == null) {
            throw new InvalidatedInputException("User cannot be null.");
        }

        return orderService.DisplayOrderList(user.getUserId());
    }

    public Order cancelOrder(String orderId) throws ShopException {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new InvalidatedInputException("OrderId cannot be empty.");
        }

        return orderService.cancelOrder(orderId);
    }
}
