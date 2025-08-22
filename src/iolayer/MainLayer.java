package iolayer;

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
    public MainLayer(Scanner scanner, OrderIOLayer orderIOLayer,
                     ProductIOLayer productIOLayer,
                     SessionService sessionService) {
        this.scanner = scanner;
        this.orderIOLayer = orderIOLayer;
        this.productIOLayer = productIOLayer;
        this.sessionService = sessionService;
    }

    public void main() {
        while (true) {
            System.out.println("1. 회원가입");
            System.out.println("2. 로그인");
            System.out.println("3. 상품 둘러보기");
            System.out.println("4. 프로그램 종료");
            if ("4".equals(scanner.nextLine())) {
                return;
            }
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 3) {
//                productIOLayer.showProduct();
            }
            if (choice == 2) {
                login();
            }
        }
    }

    private void login() {
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }
}
