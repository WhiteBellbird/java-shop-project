package iolayer;

import domain.User;
import helper.IOHelper;
import service.SessionService;

import java.util.Scanner;

public class MainLayer {
    /**
     * 1. 회원가입
     * 2. 로그인
     * 3. 상품 둘러보기
     * 4. 프로그램 종료
     */
    private Scanner scanner;
    private ProductIOLayer productIOLayer;
    private SessionService sessionService;
    private UserIOLayer userIOLayer;

    public MainLayer(Scanner scanner, OrderIOLayer orderIOLayer,
                     ProductIOLayer productIOLayer,
                     SessionService sessionService, UserIOLayer userIOLayer) {
        this.scanner = scanner;
        this.productIOLayer = productIOLayer;
        this.sessionService = sessionService;
        this.userIOLayer = userIOLayer;
    }

    public void main() {
        do {
            // MainIOController 호출하면 됌
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
            int choice = scanner.nextInt();

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
            }
        } while (true);
    }

    public void mainMenu() {
        while (true) {


            User loggedInUser = sessionService.getLoggedInUser();
            String name = loggedInUser.isAdmin() ? loggedInUser.getUsername() + " 관리자" : loggedInUser.getUsername();

            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║         Java Shopping Mall                 ║");
            System.out.println("║       환영합니다, [" + name + "]님              ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("1. 상품 둘러보기");
            System.out.println("2. 상품 검색");
            System.out.println("3. 장바구니 관리");
            System.out.println("4. 주문하기");
            System.out.println("5. 주문 내역");
            System.out.println("6. 마이페이지");
            System.out.println("7. 로그아웃");
            if (loggedInUser.isAdmin()) {
                System.out.println("8. [관리] 상품 관리");
                System.out.println("9. [관리] 사용자 관리");
            }
            System.out.print("메뉴를 선택하세요: _");
            int choice = scanner.nextInt();
            if (choice == 8 || choice == 9) {
                if (!loggedInUser.isAdmin()) {
                    System.out.println("없는 선택지입니다. 다시 선택해주세요.");
                }
            }
            switch (choice) {
                case 1:
                    productIOLayer.showProductsMain();
                    break;
                case 2:
                    // 로그인
                    break;
                case 3:
                    // 상품 둘러보기
                    break;
                case 4:
                    // 프로그램 종료
                    break;
                case 5:
                    // 상품 검색
                    break;
                case 6:
                    // 장바구니 관리
                    break;
                case 7:
                    this.logout();
                    return;
                case 8:
                    // [관리] 상품 관리
                    break;
                case 9:

                    break;
                default:
                    System.out.println("올바른 메뉴를 선택해주세요.");
            }
        }
    }


    private void login() {
        IOHelper.printFirstLine();
        System.out.print("유저이름을 입력하세요:");
        String userName = scanner.nextLine();
        System.out.print("비밀번호를 입력하세요:");
        String password = scanner.nextLine();
        sessionService.login(userName, password);
        IOHelper.printEndLine();
        this.mainMenu();
    }

    private void logout() {
        IOHelper.printFirstLine();
        sessionService.logout();
        IOHelper.printEndLine();
    }
}
