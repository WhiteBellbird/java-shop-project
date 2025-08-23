package iolayer;

import controller.ProductController;
import domain.Product;
import helper.IOHelper;

import java.util.List;
import java.util.Scanner;

public class ProductIOLayer {

    private Scanner scanner;
    private ProductController productController;
    public ProductIOLayer(Scanner scanner, ProductController productController) {
        this.scanner = scanner;
        this.productController = productController;
    }

    public void showProductsMain() {
        while (true) {
            System.out.println("┌────────────────────────────────────┐");
            System.out.println("│         🛍️ 상품 둘러보기            │");
            System.out.println("├────────────────────────────────────┤");
            System.out.println("│  1. 전체 상품 보기                   │");
            System.out.println("│  2. 카테고리별 보기                  │");
            System.out.println("│  3. 가격대별 보기                    │");
            System.out.println("│  4. 베스트셀러                      │");
            System.out.println("│  5. 신상품                          │");
            System.out.println("│  6. 상품 상세보기                    │");
            System.out.println("│  7. 돌아가기                        │");
            System.out.println("└────────────────────────────────────┘");
            System.out.println();
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    this.findAllProducts();
                    break;
                case 2:
                    this.showCategoryMain();
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
                    return;
                default:
                    System.out.println("올바른 메뉴를 선택해주세요.");
            }
        }
    }

    public void showCategoryMain() {

    }


    public void findAllProducts() {
        IOHelper.printFirstLine();
        List<Product> products = productController.getProducts();
        System.out.println("제품 목록을 출력합니다.");
        for (int i = 0; i < products.size(); i++) {
            System.out.printf("%d. ", i + 1);
            System.out.println(products.get(i));
        }
        IOHelper.printEndLine();
    }
}
