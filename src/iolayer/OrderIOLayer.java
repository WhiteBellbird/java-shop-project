package iolayer;

import controller.OrderController;
import domain.Order;
import exception.ShopException;
import helper.IOHelper;
import service.SessionService;

import java.util.List;
import java.util.Scanner;

public class OrderIOLayer {

    private Scanner scanner;
    private OrderController orderController;
    private SessionService sessionService;

    public OrderIOLayer(Scanner scanner,
                        OrderController orderController,
                        SessionService sessionService) {
        this.scanner = scanner;
        this.sessionService = sessionService;
        this.orderController = orderController;
    }

    public void showOrderMenu() {
        while (true) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│         🛒 주문하기                   │");
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│  1. 단일 상품 주문                     │");
            System.out.println("│  2. 장바구니 전체 주문                  │");
            System.out.println("│  0. 이전 메뉴로 돌아가기                 │");
            System.out.println("└─────────────────────────────────────┘");

            System.out.print("선택: _");
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
                    break;
            }
        }
    }

    public void displayOrderList() {
        while (true) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│         📜 주문내역                   │");
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│  1. 주문 내역 조회                     │");
            System.out.println("│  2. 주문 취소                         │");
            System.out.println("│  0. 이전 메뉴로 돌아가기                 │");
            System.out.println("└─────────────────────────────────────┘");

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
        IOHelper.printFirstLine();
        try {
            System.out.print("상품명: ");
            String productName = scanner.nextLine();

            System.out.print("결제 금액: ");
            int amount = Integer.parseInt(scanner.nextLine());

            System.out.print("수량: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            System.out.print("배송지: ");
            String address = scanner.nextLine();

            Order order = orderController.createOrder(sessionService.getLoggedInUser(),
                    productName, amount, quantity, address);
            System.out.println(order);
            System.out.println("주문이 성공적으로 처리되었습니다.");
        } catch (ShopException e) {
            System.out.println("주문을 처리하는 중 오류가 발생했습니다: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("❌ 숫자를 입력해야 하는 부분에 잘못된 값이 들어왔습니다.");
        } finally {
            IOHelper.printEndLine();
        }
    }

    private void inputCreateAllOrders() {
        IOHelper.printFirstLine();
        try {
            System.out.print("총 결제 가능 금액: ");
            int totalAmount = Integer.parseInt(scanner.nextLine());

            System.out.print("배송지: ");
            String address = scanner.nextLine();

            List<Order> allOrders = orderController.createAllOrders(sessionService.getLoggedInUser(),
                    totalAmount, address);
            for (Order order : allOrders) {
                System.out.println(order);
            }
            System.out.println("장바구니의 모든 상품이 주문되었습니다.");
        } catch (ShopException e) {
            System.out.println("오류가 발생했습니다: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("❌ 숫자를 입력해야 하는 부분에 잘못된 값이 들어왔습니다.");
        } finally {
            IOHelper.printEndLine();
        }
    }

    private void inputCancelOrder() {
        IOHelper.printFirstLine();
        try {
            System.out.print("취소할 주문 ID: ");
            String orderId = scanner.nextLine();

            Order order = orderController.cancelOrder(orderId);
            System.out.println(order);
            System.out.println("주문이 취소되었습니다.");
        } catch (ShopException e) {
            System.out.println("주문 취소 중 오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();
        }
    }

    private void inputDisplayOrders() {
        IOHelper.printFirstLine();
        try {
            List<Order> orders = orderController.displayOrderList(sessionService.getLoggedInUser());
            for (int i = 0; i < orders.size(); i++) {
                System.out.printf("%d. ", i + 1);
                System.out.println(orders.get(i));
            }
        } catch (ShopException e) {
            System.out.println("주문 내역을 출력하던 중 오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();
        }
    }
}
