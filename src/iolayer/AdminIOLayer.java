package iolayer;

import controller.ProductController;
import controller.UserController;
import domain.Product;
import domain.User;
import exception.ShopException;
import helper.IOHelper;
import service.SessionService;

import java.util.List;
import java.util.Scanner;

public class AdminIOLayer {

    private Scanner scanner;
    private UserController userController;
    private SessionService sessionService;
    private ProductController productController;

    public AdminIOLayer(Scanner scanner, UserController userController,
                        SessionService sessionService, ProductController productController) {
        this.scanner = scanner;
        this.userController = userController;
        this.sessionService = sessionService;
        this.productController = productController;
    }

    public void adminManageProductMenu() {
        while (true) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│         👤 회원 검색                  │");
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│  1. 회원 전체 조회                     │");
            System.out.println("│  2. 회원 강제 탈퇴                     │");
            System.out.println("│  3. 권한 부여                         │");
            System.out.println("│  0. 돌아가기                          │");
            System.out.println("└─────────────────────────────────────┘");
            System.out.print("메뉴를 선택하세요: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 제거
            switch (choice) {
                case 1:
                    displayAllUser();
                    break;
                case 2:
                    withdrawUserByAdmin();
                    break;
                case 3:
                    updateManager();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
                    break;
            }
        }
    }

    public void adminManagementUserMenu() {
        while (true) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│         🛍 상품 관리                  │");
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│  1. 신규 상품 등록                     │");
            System.out.println("│  2. 상품 정보 수정                     │");
            System.out.println("│  3. 상품 삭제                         │");
            System.out.println("│  4. 재고 관리 (입고 처리)               │");
            System.out.println("│  0. 뒤로 가기                         │");
            System.out.println("└─────────────────────────────────────┘");
            System.out.print("메뉴를 선택하세요: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 제거
            switch (choice) {
                case 1:
                    createProduct();
                    break;
                case 2:
                    updateProduct();
                    break;
                case 3:
                    deleteProduct();
                    break;
                case 4:
                    managementStockMenu();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
                    break;
            }
        }
    }

    private void updateManager() {
        IOHelper.printFirstLine();
        System.out.println("user를 manager로 권한부여합니다");
        System.out.print("업데이트할 유저네임을 입력하세요: ");
        String username = scanner.nextLine();
        try {
            User user = userController.updateManager(sessionService.getLoggedInUser(), username);
            System.out.println("성공적으로 권한부여 완료");
            System.out.println(user);
            sessionService.updateSessionUser(user);
        } catch (ShopException e) {
            System.out.println("오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();
        }
    }


    private void managementStockMenu() {
        while (true) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│         📦 재고 관리                  │");
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│  1. 재고 입고                          │");
            System.out.println("│  2. 재고 출고                          │");
            System.out.println("│  0. 뒤로 가기                          │");
            System.out.println("└─────────────────────────────────────┘");
            System.out.print("메뉴를 선택하세요: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 제거
            switch (choice) {
                case 1:
                    addStock();
                    break;
                case 2:
                    reduceStock();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
            }
        }
    }

    private void createProduct() {
        IOHelper.printFirstLine();
        System.out.println("신규 상품을 등록합니다.");
        System.out.print("상품명: ");
        String name = scanner.nextLine();
        System.out.print("가격: ");
        int price = scanner.nextInt();
        scanner.nextLine(); // 개행 제거
        System.out.print("설명: ");
        String description = scanner.nextLine();
        System.out.print("카테고리: ");
        String category = scanner.nextLine();
        System.out.print("재고: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // 개행 제거

        try {
            Product product = productController.createProduct(name, category, price, stock, description);
            System.out.println(product);
            System.out.println("상품이 성공적으로 등록되었습니다.");
        } catch (ShopException e) {
            System.out.println("상품 등록 중 오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();
        }
    }

    private void updateProduct() {
        IOHelper.printFirstLine();
        System.out.println("상품 정보를 수정합니다.");
        System.out.print("수정할 상품명: ");
        String originProductName = scanner.nextLine();
        System.out.print("새로운 상품명: ");
        String newProductName = scanner.nextLine();
        System.out.print("새로운 카테고리: ");
        String category = scanner.nextLine();
        System.out.print("새로운 가격: ");
        int price = scanner.nextInt();
        scanner.nextLine(); // 개행 제거
        System.out.print("새로운 설명: ");
        String description = scanner.nextLine();

        try {
            Product updatedProduct = productController.updateProduct(originProductName, newProductName, category, price, description);
            System.out.println("상품 정보가 성공적으로 수정되었습니다: " + updatedProduct);
        } catch (ShopException e) {
            System.out.println("상품 정보 수정 중 오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();
        }
    }

    private void addStock() {
        IOHelper.printFirstLine();
        System.out.println("상품 재고를 관리합니다.");
        System.out.print("재고를 관리할 상품명: ");
        String productName = scanner.nextLine();
        System.out.print("추가할 재고 수량: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // 개행 제거

        try {
            Product product = productController.addProductStock(productName, stock);
            System.out.println(product);
            System.out.println("재고가 성공적으로 추가되었습니다.");
        } catch (ShopException e) {
            System.out.println("재고 추가 중 오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();
        }
    }

    private void reduceStock() {
        IOHelper.printFirstLine();
        System.out.println("상품 재고를 관리합니다.");
        System.out.print("재고를 관리할 상품명: ");
        String productName = scanner.nextLine();
        System.out.print("감소시킬 재고 수량: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // 개행 제거

        try {
            Product product = productController.subtractProductStock(productName, stock);
            System.out.println(product);
            System.out.println("재고가 성공적으로 감소되었습니다.");
        } catch (ShopException e) {
            System.out.println("재고 감소 중 오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();
        }
    }

    private void deleteProduct() {
        IOHelper.printFirstLine();
        System.out.print("삭제할 상품을 입력하세요: ");
        String productName = scanner.nextLine();

        try {
            Boolean result = productController.deleteProduct(productName);
            if (result) {
                System.out.println("상품이 성공적으로 삭제되었습니다.");
            } else {
                System.out.println("상품 삭제에 실패했습니다. 상품명을 다시 확인해주세요.");
            }
        } catch (ShopException e) {
            System.out.println("상품 삭제 중 오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();
        }
    }

    private void displayAllUser() {
        IOHelper.printFirstLine();
        System.out.println("모든 유저를 조회합니다.");
        List<User> allUsers = userController.findAllUsers(sessionService.getLoggedInUser());
        for (int i = 0; i < allUsers.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, allUsers.get(i));
        }
        IOHelper.printEndLine();
    }

    private void withdrawUserByAdmin() {
        IOHelper.printFirstLine();
        System.out.print("탈퇴시킬 고객네임: ");
        String username = scanner.nextLine();

        try {
            List<User> users = userController.withdrawUserByAdmin(sessionService.getLoggedInUser(), username);
            System.out.println("성공적으로 퇴출완료");
            for (int i = 0; i < users.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, users.get(i));
            }
        } catch (Exception e) {
            System.out.println("오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();
        }
    }
}
