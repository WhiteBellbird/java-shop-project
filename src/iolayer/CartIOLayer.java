package iolayer;

import controller.CartValidationController;
import domain.User;
import exception.ShopException;
import java.util.Scanner;

public class CartIOLayer {

    private  CartValidationController cartController;
    private  Scanner scanner;

    public CartIOLayer(CartValidationController cartController) {
        this.cartController = cartController;
        this.scanner = new Scanner(System.in);
    }

    public void startCartOperations(User user) {
        System.out.println("--- 장바구니 관리 ---");
        while (true) {
            displayMenu();
            String choice = scanner.nextLine();
            
            try {
                switch (choice) {
                    case "1":
                        addProduct(user);
                        break;
                    case "2":
                        updateProductQuantity(user);
                        break;
                    case "3":
                        removeProduct(user);
                        break;
                    case "4":
                        clearCart(user);
                        break;
                    case "5":
                        calcTotalPrice(user);
                        break;
                    case "6":
                        System.out.println("메인 메뉴로 돌아갑니다.");
                        return;
                    default:
                        System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                }
            } catch (ShopException e) {
                System.out.println("오류 발생: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("잘못된 수량 입력입니다. 숫자를 입력해주세요.");
            }
        }
    }
    
    private void displayMenu() {
        System.out.println("\n┌────────────────────────────────────┐");
        System.out.println("│         🛒 장바구니 관리            │");
        System.out.println("├────────────────────────────────────┤");
        System.out.println("│  1. 상품 추가                      │");
        System.out.println("│  2. 수량 변경                      │");
        System.out.println("│  3. 상품 삭제                      │");
        System.out.println("│  4. 장바구니 비우기                 │");
        System.out.println("│  5. 총 가격 확인                    │");
        System.out.println("│  6. 돌아가기                       │");
        System.out.println("└────────────────────────────────────┘");
        System.out.print("선택: ");
    }
    
    private void addProduct(User user) throws ShopException {
        System.out.print("추가할 상품 ID: ");
        String productId = scanner.nextLine();
        System.out.print("수량: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        cartController.상품추가(user.getUserId(), productId, quantity);
        System.out.println("상품이 성공적으로 추가되었습니다.");
    }

    private void updateProductQuantity(User user) throws ShopException {
        System.out.print("수량을 변경할 상품 ID: ");
        String productId = scanner.nextLine();
        System.out.print("새로운 수량: ");
        int newQuantity = Integer.parseInt(scanner.nextLine());
        cartController.상품수량변경(user.getUserId(), productId, newQuantity);
        System.out.println("상품의 수량이 " + newQuantity + "개로 변경되었습니다.");
    }
    
    private void removeProduct(User user) throws ShopException {
        System.out.print("삭제할 상품 ID: ");
        String productId = scanner.nextLine();
        cartController.상품삭제(user.getUserId(), productId);
        System.out.println("상품이 장바구니에서 삭제되었습니다.");
    }

    private void clearCart(User user) throws ShopException {
        cartController.장바구니비우기(user.getUserId());
        System.out.println("장바구니가 성공적으로 비워졌습니다.");
    }
    
    private void calcTotalPrice(User user) throws ShopException {
        int totalPrice = cartController.장바구니총가격계산(user.getUserId());
        System.out.println("장바구니의 총 가격은 " + totalPrice + "원입니다.");
    }
}
