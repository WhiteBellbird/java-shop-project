package controller;

import exception.CustomIllegalArgumentException;
import service.OrderService;

public class OrderValidationController {

    private final OrderService orderService;

    public OrderValidationController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ✅ 검증 + 주문 로직
    public void createOrder(String userId, String productName, int amount, int quantity, String address) {
        validateCreateOrder(userId, productName, amount, quantity, address);

        orderService.createOrder(userId, productName, amount, quantity, address);
        System.out.println("✅ 주문이 완료되었습니다.");
    }

    public void createAllOrders(String userId, int totalAmount, String address) {
        validateCreateOrder(userId, totalAmount, address);

        orderService.createAllOrders(userId, totalAmount, address);
    }

    public void cancelOrder(String orderId) {
        validateCreateOrder(orderId);

        orderService.cancelOrder(orderId);
    }

    public void displayOrderList(String userId) {
        validateCreateOrder(userId);

        orderService.displayOrderList(userId);
    }

    // 검증 메서드
    private void validateCreateOrder(String userId, String productName, int amount, int quantity, String address) {
        if (userId == null || userId.isEmpty()) {
            throw new CustomIllegalArgumentException("❌ UserId는 필수입니다.");
        }
        if (productName == null || productName.isEmpty()) {
            throw new CustomIllegalArgumentException("❌ 상품명을 입력하세요.");
        }
        if (amount <= 0) {
            throw new CustomIllegalArgumentException("❌ 금액은 0보다 커야 합니다.");
        }
        if (quantity <= 0) {
            throw new CustomIllegalArgumentException("❌ 수량은 0보다 커야 합니다.");
        }
        if (address == null || address.isEmpty()) {
            throw new CustomIllegalArgumentException("❌ 주소는 필수입니다.");
        }
    }

    private void validateCreateOrder(String userId, int totalAmount, String address) {
        if (userId == null || userId.isEmpty()) {
            throw new CustomIllegalArgumentException("❌ UserId는 필수입니다.");
        }

        if (totalAmount <= 0) {
            throw new CustomIllegalArgumentException("❌ 금액은 0보다 커야 합니다.");
        }

        if (address == null || address.isEmpty()) {
            throw new CustomIllegalArgumentException("❌ 주소는 필수입니다.");
        }
    }

    private void validateCreateOrder(String orderId) {
        if (orderId == null || orderId.isEmpty()) {
            throw new CustomIllegalArgumentException("❌ OrderId는 필수입니다.");
        }
    }




}
