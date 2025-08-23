package iolayer;

import domain.User;
import service.OrderService;
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
    private OrderIOLayer orderIOLayer;
    private ProductIOLayer productIOLayer;
    private SessionService sessionService;
    private UserIOLayer userIOLayer;
    public MainLayer(Scanner scanner, OrderIOLayer orderIOLayer,
                     ProductIOLayer productIOLayer,
                     SessionService sessionService, UserIOLayer userIOLayer) {
        this.scanner = scanner;
        this.orderIOLayer = orderIOLayer;
        this.productIOLayer = productIOLayer;
        this.sessionService = sessionService;
        this.userIOLayer = userIOLayer;
    }

    public void main() {
        do {
            // MainIOController 호출하면 됌
            System.out.println(
                    "╔════════════════════════════════════════════╗\r\n"
                            + "║     🛍️  Java Shopping Mall                 ║\r\n"
                            + "╚════════════════════════════════════════════╝\r\n"
                            + "\r\n"
                            + "1. 회원가입\r\n"
                            + "2. 로그인\r\n"
                            + "3. 상품 둘러보기\r\n"
                            + "4. 프로그램 종료\r\n"
                            + "\r\n"
                            + "메뉴를 선택하세요: _");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    userIOLayer.createUser();
                    break;
                case 2:
//                    login();
//                    User user = ss.getLoggedInUser();
//                    mainMenu(user);
                    break;
                case 3:
//                    productIOLayer.viewProducts();
                    break;
                case 4:
                    System.exit(0);
            }
        } while (true);
    }

}
