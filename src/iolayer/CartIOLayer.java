package iolayer;

import controller.CartController;
import domain.Cart;
import domain.CartItem;
import domain.User;
import exception.ShopException;
import helper.IOHelper;
import service.SessionService;

import java.util.Scanner;

public class CartIOLayer {

    private CartController cartController;
    private Scanner scanner;
    private SessionService sessionService;

    public CartIOLayer(CartController cartController,
                       SessionService sessionService) {
        this.cartController = cartController;
        this.sessionService = sessionService;
        this.scanner = new Scanner(System.in);
    }

    public void cartMenu(User user) {
        System.out.println("--- 장바구니 관리 ---");
        while (true) {
            displayMenu();
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        addProduct();
                        break;
                    case "2":
                        updateProductQuantity();
                        break;
                    case "3":
                        removeProduct();
                        break;
                    case "4":
                        clearCart();
                        break;
                    case "5":
                        calcTotalPrice();
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

    private void updateProductQuantity() {
        IOHelper.printFirstLine();
        System.out.print("수량을 변경할 상품 이름: ");
        String productName = scanner.nextLine();
        System.out.print("새로운 수량: ");
        int newQuantity = Integer.parseInt(scanner.nextLine());

        try {
            CartItem cartItem = cartController.addProductQuantityByCart(sessionService.getLoggedInUser(), productName, newQuantity);
            System.out.println("상품의 수량이 " + cartItem.getQuantity() + "개로 변경되었습니다.");
        } catch (ShopException e) {
            System.out.println("오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();
        }
    }

    private void addProduct() {
        IOHelper.printFirstLine();
        try {
            System.out.print("추가할 상품 이름: ");
            String productName = scanner.nextLine();
            System.out.print("수량: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            cartController.addProductByCart(sessionService.getLoggedInUser(), productName, quantity);
            System.out.println("상품이 성공적으로 추가되었습니다.");
        } catch (ShopException e) {
            System.out.println("오류가 발생했습니다: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("잘못된 수량 입력입니다. 숫자를 입력해주세요.");
        } finally {
            IOHelper.printEndLine();
        }
    }

    private void removeProduct() {
        IOHelper.printFirstLine();
        try {
            System.out.print("삭제할 상품 이름: ");
            String productName = scanner.nextLine();
            cartController.removeProductByCart(sessionService.getLoggedInUser(), productName);
            System.out.println("상품이 장바구니에서 삭제되었습니다.");
        } catch (ShopException e) {
            System.out.println("오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();
        }
    }

    private void clearCart() {
        IOHelper.printFirstLine();
        try {
            Cart cart = cartController.clearCart(sessionService.getLoggedInUser());
            System.out.println("장바구니가 성공적으로 비워졌습니다.");
        } catch (ShopException e) {
            System.out.println("오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();
        }
    }

    private void calcTotalPrice() {
        IOHelper.printFirstLine();
        try {
            int totalPrice = cartController.calcTotalPriceInCart(sessionService.getLoggedInUser());
            System.out.println("장바구니의 총 가격은 " + totalPrice + "원입니다.");
        } catch (ShopException e) {
            System.out.println("오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();
        }
    }
}
