package test;



import java.time.LocalDateTime;

import java.util.List;



import domain.Product;

import exception.ShopException;

import repository.ProductRepositoryImpl;

import service.ProductServiceImpl;



public class ProductServiceTest {



static ProductRepositoryImpl repo = new ProductRepositoryImpl();

static ProductServiceImpl service = new ProductServiceImpl(repo);



public static void main(String[] args) {

repo.resetData();

Product product1 = createProduct("P1", "모니터", "전자기기", 200000, 5, "모니터에요", LocalDateTime.now());

Product product2 = createProduct("P2", "키보드", "전자기기", 150000, 5, "키보드에요", LocalDateTime.now());

displayAllProducts();

updateProduct(product1.getProductId(), "노트북", "전자기기", 100000, 5, "노트북이에요", LocalDateTime.now());

displayAllProducts();

deleteProduct(product2.getProductId());

deleteProduct("P999");

displayAllProducts();

createProduct("P3","마우스", "전자기기", 1500000, 10, "마우스에요", LocalDateTime.now());

displayAllProducts();


}



private static void updateProduct(String productId, String name, String category, int price, int stock, String description,

LocalDateTime date) {

try {

Product updatedProduct = new Product(productId, name, category, price, stock, description, LocalDateTime.now());

service.updateProduct(updatedProduct);

System.out.println("상품 정보 수정 성공: ID " + productId);

} catch (ShopException e) {

System.out.println("상품 수정 실패: " + e.getMessage());

}


}



private static Product createProduct(String productId, String name, String category, int price, int stock, String description,

LocalDateTime date) {

try {

Product newProduct = new Product(productId, name, category, price, stock, description, date);

service.addProduct(newProduct);

System.out.println("상품 생성 성공: " + newProduct);

return newProduct;

} catch (ShopException e) {

System.out.println("상품 생성 실패: " + e.getMessage());

return null;

}

}



public static void deleteProduct(String productId) {

try {

service.deleteProduct(productId);

System.out.println("상품 삭제 성공: ID " + productId);

} catch (ShopException e) {

System.out.println("상품 삭제 실패: " + e.getMessage());

}

}


public static void displayAllProducts() {

System.out.println("=== 전체 상품 목록 ===");

try {

List<Product> products = service.getAllProducts();

if (products.isEmpty()) {

System.out.println("등록된 상품이 없습니다.");

} else {

products.forEach(System.out::println);

}

} catch (ShopException e) {

System.out.println("상품 목록 조회 실패: " + e.getMessage());

}

}

}