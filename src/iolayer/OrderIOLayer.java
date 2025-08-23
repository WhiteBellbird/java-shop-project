package iolayer;

import controller.OrderController;
import exception.ShopException;
import service.OrderService;

import java.util.Scanner;

public class OrderIOLayer {


    private Scanner scanner;
    private OrderController orderController;
    private OrderService orderService;
    public OrderIOLayer(Scanner scanner, OrderController orderController, OrderService orderService) {
        this.scanner = scanner;
        this.orderController = orderController;
        this.orderService = orderService;
    }

    public void order() {
        System.out.println("Please enter your username:");
        System.out.println("Please enter your username:");
        System.out.println("Please enter your username:");
        System.out.println("Please enter your username:");
        System.out.println("Please enter your username:");
        System.out.println("Please enter your username: 0");
//        if (!orderValidationController.validateCreateOrder()) {
//            System.out.println("invalid input");
//            return;
//        }
        try {
//            Order order = orderService.createOrder(nuul, null, null);
//            System.out.println("order = " + order);
        } catch (ShopException e) {
            System.out.println(e.getMessage());
            return;
        }
        // 출력
    }
}
