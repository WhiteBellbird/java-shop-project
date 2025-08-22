package iolayer;



import java.time.LocalDateTime;

import java.util.List;

import java.util.Scanner;



import controller.ProductValidationController;

import domain.Product;

import exception.ShopException;

import service.ProductService;



public class ProductIOLayer {



private Scanner scanner;

private ProductValidationController productValidationController;

private ProductService productService;


public ProductIOLayer(Scanner scanner, ProductValidationController productValidationController, ProductService productService) {

this.scanner = scanner;

this.productValidationController = productValidationController;

this.productService = productService;

}

public void run() {

while(true) {

displayMenu();

try {

int choice = Integer.parseInt(scanner.nextLine());

switch(choice) {

case 0:

System.out.println("프로그램을 종료합니다.");

return;

case 1:

viewAllProduct();

break;

case 2:

searchProductById();

break;

case 3:

addProduct();

break;

case 4:

updateProduct();

break;

case 5:

deleteProduct();

break;

default:

System.out.println("잘못된 선택입니다.");

break;

}

}catch(NumberFormatException e) {

System.out.println("숫자를 입력해주세요.");

}catch(ShopException e) {

System.out.println(e.getMessage());

}

}

}



private void deleteProduct() {

System.out.println("\n=== 상품 삭제 ===");

System.out.print("삭제할 상품ID: ");

String productId = scanner.nextLine();

try {

productService.deleteProduct(productId);

System.out.println("상품이 삭제되었습니다.");

}catch(ShopException e) {

System.out.println(e.getMessage());

}


}

private void updateProduct() {

System.out.println("\n=== 상품 수정 ===");

System.out.print("수정할 상품ID: ");

String productId = scanner.nextLine();

try {

Product nowProduct = productService.getProductById(productId);

System.out.println("현재 상품: "+nowProduct);

System.out.print("새 상품명 ("+ nowProduct.getName()+ "):");

String name = scanner.nextLine();

System.out.print("새 가테고리 ("+ nowProduct.getCategory()+ "):");

String category = scanner.nextLine();

int newPrice = nowProduct.getPrice();

System.out.print("새 가격 ("+ nowProduct.getPrice()+"): ");

String priceStr = scanner.nextLine();

if(!priceStr.isEmpty()) {

newPrice = Integer.parseInt(priceStr);

}

int newStock = nowProduct.getStock();

System.out.print("새 재고 (" + nowProduct.getStock() + "): ");

String stockStr = scanner.nextLine();

if (!stockStr.isEmpty()) {

newStock = Integer.parseInt(stockStr);

}

System.out.print("새 상품설명 ("+ nowProduct.getDescription()+ "):");

String description = scanner.nextLine();


String newName = name.isEmpty() ? nowProduct.getName() : name;

String newCategory = category.isEmpty() ? nowProduct.getCategory() : category;

String newDescription = description.isEmpty() ? nowProduct.getDescription() : description;


if(productValidationController.validateCreateProduct(productId, newName, newCategory, newPrice, newStock, newDescription)) {

Product product = new Product(productId, newName, newCategory, newPrice, newStock, newDescription, nowProduct.getRegisrationDate());

productService.updateProduct(product);

System.out.println("상품이 수정되었습니다." + product);

}

}catch(ShopException e){

System.out.println(e.getMessage());

}

}

private void addProduct() {

System.out.println("\n=== 상품 등록 ===");

try {

System.out.print("상품ID: ");

String productId = scanner.nextLine();

System.out.print("상품명: ");

String name = scanner.nextLine();

System.out.print("카테고리: ");

String category = scanner.nextLine();

System.out.print("가격: ");

int price = Integer.parseInt(scanner.nextLine());

System.out.print("재고: ");

int stock =Integer.parseInt(scanner.nextLine());

System.out.println("상품 설명: ");

String description = scanner.nextLine();


if(productValidationController.validateCreateProduct(productId, name, category, price, stock, description)) {

Product product = new Product(productId, name, category, price, stock, description, LocalDateTime.now());

productService.addProduct(product);

System.out.println("상품이 등록되었습니다. "+ product);

}

}catch(ShopException e) {

System.out.println(e.getMessage());

}

}



private void searchProductById() {

System.out.println("검색할 상품ID를 입력해주세요.");

String productId = scanner.nextLine();

try {

Product product = productService.getProductById(productId);

System.out.println("ID 검색 상품: "+product);

}catch(ShopException e) {

System.out.println(e.getMessage());

}


}

private void viewAllProduct() {

System.out.println("\n=== 모든 상품 목록 조회 ===");

try {

List<Product> products = productService.getAllProducts();

if(products.isEmpty()) {

System.out.println("등록되어있는 상품이 없습니다.");

}else {

products.forEach(System.out::println);

}

}catch(ShopException e) {

System.out.println(e.getMessage());

}


}

private void displayMenu() {

System.out.println("\n=== 상품 관리 메뉴 ===");

System.out.println("1. 모든 상품 조회");

System.out.println("2. 상품ID로 조회");

System.out.println("3. 상품 등록");

System.out.println("4. 상품 수정");

System.out.println("5. 상품 삭제");

System.out.println("0. 종료");

}

}