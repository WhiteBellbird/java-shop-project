package iolayer;

import controller.ProductController;
import controller.UserController;
import domain.User;
import exception.ShopException;
import helper.IOHelper;
import service.SessionService;

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
            System.out.println("\n--- 회원 검색 ---");
            System.out.println("1. 회원 전체 조회");
            System.out.println("2. 회원 강제 탈퇴");

            System.out.println("0. 돌아가기");
            System.out.print("\n메뉴를 선택하세요: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    displayAllUser();
                    break;
                case 2:
                    withdrawUserByAdmin();
                    break;
                case 0:
                    return;
                default:
                    break;
            }
        }
    }

    public void adminManagementUserMenu() {
        while (true) {
            System.out.println("\n--- 상품 관리 메뉴 ---");
            System.out.println("1. 신규 상품 등록");
            System.out.println("2. 상품 정보 수정");
            System.out.println("3. 상품 삭제");
            System.out.println("4. 재고 관리 (입고 처리)");
            System.out.println("5. 상품 목록 조회");
            System.out.println("0. 뒤로 가기");
            System.out.print("\n메뉴를 선택하세요: ");
            int choice = scanner.nextInt();
            switch (choice) {

            }
        }
    }


    private void displayAllUser() {
        IOHelper.printFirstLine();
        System.out.println("모든 유저를 조회합니다.");
        userController.findAllUsers(sessionService.getLoggedInUser());
        IOHelper.printEndLine();
    }

    private void updateManager() {
        IOHelper.printFirstLine();
        System.out.println("user를 manager로 권한부여합니다");
        try {
            User user = userController.updateManager(sessionService.getLoggedInUser());
            System.out.println("성공적으로 매니저로 승격되었습니다.");
            sessionService.updateSessionUser(user);
        } catch (ShopException e) {
            System.out.println("오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();
        }
    }

    private void withdrawUserByAdmin() {
        IOHelper.printFirstLine();
        System.out.print("탈퇴시킬 고객네임: ");
        String username = scanner.nextLine();
        try {
            userController.withdrawUserByAdmin(sessionService.getLoggedInUser(), username);
            System.out.println("성공적으로 퇴출완료");
        } catch (Exception e) {
            System.out.println("오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();
        }
    }
}
