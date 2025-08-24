package iolayer;

import controller.ProductController;
import domain.Product;
import exception.ShopException;
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
                    this.showCategory();
                    break;
                case 3:
                    this.showPrice();
                    break;
                case 4:
                    this.showBestSeller();
                    break;
                case 5:
                    this.lastestProduct();
                    break;
                case 6:
                    this.showProductDetails();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("올바른 메뉴를 선택해주세요.");
            }
        }
    }

    private void showProductDetails() {
        IOHelper.printFirstLine();
        System.out.print("상품명을 입력하세요: ");
        String productName = scanner.nextLine();
        try {
            Product product = productController.getProductByProductName(productName);
            System.out.printf("%s 제품의 상세정보\n", product.getName());
            System.out.println(product);
        } catch (ShopException e) {
            System.out.println("오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();
        }
    }

    private void lastestProduct() {
        IOHelper.printFirstLine();
        System.out.println("신상품은 아래입니다.");
        try {
            Product product = productController.getProductByRegistrationDate();
            System.out.println(product);
        } catch (ShopException e) {
            System.out.println("오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();

        }

    }

    private void showPrice() {
        IOHelper.printFirstLine();
        System.out.println("가격별로 상품을 나열합니다.");
        List<Product> products = productController.getProductsByPrice();
        for (int i = 0; i < products.size(); i++) {
            System.out.printf("%d. ", i + 1);
            System.out.println(products.get(i));
        }
        IOHelper.printEndLine();
    }

    private void showBestSeller() {
        IOHelper.printFirstLine();
        System.out.println("베스트 셀러를 나열합니다.");
        List<Product> seller = productController.getProductsByBestSeller();
        for (int i = 0; i < seller.size(); i++) {
            System.out.printf("%d. ", i + 1);
            System.out.println(seller.get(i));
        }
        IOHelper.printEndLine();
    }

    private void showCategory() {
        IOHelper.printFirstLine();
        System.out.print("카테고리 명을 입력하세요: ");
        String categoryName = scanner.nextLine();
        List<Product> products = productController.getProductsByCategory(categoryName);
        for (int i = 0; i < products.size(); i++) {
            System.out.printf("%d. ", i + 1);
            System.out.println(products.get(i));
        }
        IOHelper.printEndLine();
    }



    private void findAllProducts() {
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
