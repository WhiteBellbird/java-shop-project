package test;



import java.time.LocalDateTime;

import java.util.List;

import java.util.Optional;



import domain.Product;



import repository.ProductRepositoryImpl;



public class ProductRepositoryTest {



static ProductRepositoryImpl repo = new ProductRepositoryImpl();

public static void main(String[] args) {

// 메서드 전체를 수정해주세요

createProducts();

displayAllProducts();

displayFindById("P1");

updateProducts("P1", "노트북", 150000, 10);

deleteProducts("P2");

displayAllProducts();


}





private static void updateProducts(String productId, String name, int price, int stock) {

Optional<Product> products = repo.findById(productId);

if (products.isPresent()) {

Product product = products.get();

product.setName(name);

product.setPrice(price);

product.setStock(stock);

repo.save(product);

System.out.println("상품 " + productId + " 정보 수정 완료.");

} else {

System.out.println("ID " + productId + " 상품을 찾을 수 없어 수정하지 못했습니다.");

}


}



private static void deleteProducts(String productId) { {

Optional<Product> products = repo.findById(productId);

if (products.isPresent()) {

repo.delete(products.get());

System.out.println("상품 " + productId + " 삭제 완료.");

} else {

System.out.println("ID " + productId + "상품을 찾을 수 없습니다.");

}

}


}



private static void displayFindById(String productId) {

Optional<Product> findProduct = repo.findById(productId);

findProduct.ifPresentOrElse(

product -> System.out.println("찾은 상품: " + product),

() -> System.out.println("ID " + productId + " 상품을 찾을 수 없습니다.")

);

}


private static void displayAllProducts() {

List<Product> products = repo.findAll();

products.forEach(System.out::println);

}


private static void createProducts() {

repo.save(new Product("P1","모니터","전자기기", 200000, 5, "모니터에요",LocalDateTime.now()));

repo.save(new Product("P2","키보드","전자기기", 150000, 5, "키보드에요",LocalDateTime.now()));

repo.save(new Product("P3","마우스","전자기기", 50000, 5, "마우스에요",LocalDateTime.now()));

}

}