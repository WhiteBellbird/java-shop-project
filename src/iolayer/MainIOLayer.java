package iolayer;

import domain.User;
import exception.ShopException;
import helper.IOHelper;
import service.SessionService;

import java.util.Scanner;

public class MainIOLayer {

    private Scanner scanner;
    private ProductIOLayer productIOLayer;
    private SessionService sessionService;
    private UserIOLayer userIOLayer;
    private OrderIOLayer orderIOLayer;
    private AdminIOLayer adminIOLayer;
    private CartIOLayer cartIOLayer;

    public MainIOLayer(Scanner scanner, OrderIOLayer orderIOLayer,
                       ProductIOLayer productIOLayer,
                       SessionService sessionService, UserIOLayer userIOLayer,
                       AdminIOLayer adminIOLayer, CartIOLayer cartIOLayer) {
        this.scanner = scanner;
        this.productIOLayer = productIOLayer;
        this.sessionService = sessionService;
        this.userIOLayer = userIOLayer;
        this.orderIOLayer = orderIOLayer;
        this.adminIOLayer = adminIOLayer;
        this.cartIOLayer = cartIOLayer;
    }

    public void main() {
        while (true) {
            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║         Java Shopping Mall                 ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("1. 회원가입");
            System.out.println("2. 로그인");
            System.out.println("3. 상품 둘러보기");
            System.out.println("4. 프로그램 종료");
            System.out.println();
            System.out.print("메뉴를 선택하세요: _");

            String input = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ 숫자를 입력해야 합니다.");
                continue;
            }

            switch (choice) {
                case 1:
                    userIOLayer.createUser();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    productIOLayer.showProductsMain();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("올바른 메뉴를 선택해주세요.");
            }
        }
    }

    public void mainMenu() {
        User loggedInUser = sessionService.getLoggedInUser();
        boolean loggedIn = true;

        while (loggedIn) {
            String name = loggedInUser.isAdmin() ? loggedInUser.getUsername() + " 관리자" : loggedInUser.getUsername();

            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║ Java Shopping Mall                         ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.println("환영합니다, [" + name + "]님");
            System.out.println();
            System.out.println("1. 상품 둘러보기");
            System.out.println("2. 장바구니 관리");
            System.out.println("3. 주문하기");
            System.out.println("4. 주문 내역");
            System.out.println("5. 마이페이지");
            System.out.println("6. 로그아웃");
            if (loggedInUser.isAdmin()) {
                System.out.println("7. [관리] 사용자 관리");
                System.out.println("8. [관리] 상품 관리");
            }
            System.out.print("메뉴를 선택하세요: _");

            String input = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ 숫자를 입력해야 합니다.");
                continue;
            }

            if ((choice == 7 || choice == 8) && !loggedInUser.isAdmin()) {
                System.out.println("올바른 메뉴를 선택해주세요.");
                continue;
            }

            switch (choice) {
                case 1:
                    productIOLayer.showProductsMain();
                    break;
                case 2:
                    cartIOLayer.cartMenu();
                    break;
                case 3:
                    orderIOLayer.showOrderMenu();
                    break;
                case 4:
                    orderIOLayer.displayOrderList();
                    break;
                case 5:
                    userIOLayer.myPage();
                    if (!sessionService.isLoggedIn()) {
                        loggedIn = false;
                    }
                    break;
                case 6:
                    logout();
                    loggedIn = false;
                    break;
                case 7:
                    adminIOLayer.adminManageProductMenu();
                    break;
                case 8:
                    adminIOLayer.adminManagementUserMenu();
                    break;
                default:
                    System.out.println("올바른 메뉴를 선택해주세요.");
            }
        }
    }

    private void login() {
        IOHelper.printFirstLine();
        System.out.print("유저이름을 입력하세요: ");
        String userName = scanner.nextLine();
        System.out.print("비밀번호를 입력하세요: ");
        String password = scanner.nextLine();

        if (userName.isEmpty() || password.isEmpty() || userName.isBlank() || password.isBlank()) {
            System.out.println("유저이름과 비밀번호는 공백일 수 없습니다.");
            return;
        }

        try {
            sessionService.login(userName, password);
            System.out.println("성공적으로 로그인 되었습니다.");
            this.mainMenu();
        } catch (ShopException e) {
            System.out.println("오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();
        }
    }

    private void logout() {
        IOHelper.printFirstLine();
        sessionService.logout();
        IOHelper.printEndLine();
    }

}
