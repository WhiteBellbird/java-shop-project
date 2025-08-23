package iolayer;

import controller.OrderValidationController;
import domain.Order;
import exception.ShopException;
import service.OrderService;

import java.util.Scanner;

public class OrderIOLayer {


    private Scanner scanner;
    private OrderValidationController orderValidationController;
    private OrderService orderService;
    public OrderIOLayer(Scanner scanner, OrderValidationController orderValidationController, OrderService orderService) {
        this.scanner = scanner;
        this.orderValidationController = orderValidationController;
        this.orderService = orderService;
    }


    public void Order() {
        while (true) {
            System.out.println("\n=== 주문하기 ===");
            System.out.println("1. 단일 상품 주문");
            System.out.println("2. 장바구니 전체 주문");
            System.out.println("0. 이전 메뉴로 돌아가기");
            System.out.print("선택: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    inputCreateOrder();
                    break;
                case "2":
                    inputCreateAllOrders();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("❌ 잘못된 선택입니다.");
            }
        }
    }

    public void displayOrderList(){
        while(true){
            System.out.println("\n=== 주문내역 ===");
            System.out.println("1. 주문 내역 조회");
            System.out.println("2. 주문 취소");
            System.out.println("0. 이전 메뉴로 돌아가기");
            System.out.print("선택: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    inputDisplayOrders();
                    break;
                case "2":
                    inputCancelOrder();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("❌ 잘못된 선택입니다.");
            }
        }
    }

    private void inputCreateOrder() {
        System.out.print("User ID: ");
        String userId = scanner.nextLine();

        System.out.print("상품명: ");
        String productName = scanner.nextLine();

        System.out.print("결제 금액: ");
        int amount = Integer.parseInt(scanner.nextLine());

        System.out.print("수량: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        System.out.print("배송지: ");
        String address = scanner.nextLine();

        orderValidationController.createOrder(userId, productName, amount, quantity, address);
    }

    private void inputCreateAllOrders() {
        System.out.print("User ID: ");
        String userId = scanner.nextLine();

        System.out.print("총 결제 가능 금액: ");
        int totalAmount = Integer.parseInt(scanner.nextLine());

        System.out.print("배송지: ");
        String address = scanner.nextLine();

        orderValidationController.createAllOrders(userId, totalAmount, address);
    }

    private void inputCancelOrder() {
        System.out.print("취소할 주문 ID: ");
        String orderId = scanner.nextLine();

        orderValidationController.cancelOrder(orderId);
    }

    private void inputDisplayOrders() {
        System.out.print("조회할 User ID: ");
        String userId = scanner.nextLine();

        orderValidationController.displayOrderList(userId);
    }
}
