package test;

import java.time.LocalDateTime;
import java.util.List;

import domain.Product;

import repository.ProductRepositoryImpl;

public class ProductRepositoryTest {

	static ProductRepositoryImpl repo = new ProductRepositoryImpl();
	public static void main(String[] args) {
		// 메서드 전체를 수정해주세요
		createProducts();
		displayAllProducts();
		displayFindById("P1");
		updateProducts("P1", "휴대폰", 100000);
	}
	private static void deleteProducts(Product product) {
//		System.out.println("삭제 전 상품 개수: " + repo.findAll().size());
//        System.out.println("삭제 후 상품 개수: " + repo.findAll().size());
	}
	private static void updateProducts(String productId, String productName, int price) {
	}
	private static void displayFindById(String productId) {

	}
	private static void displayAllProducts() {
		List<Product> products = repo.findAll();
		products.forEach(System.out::println);
	}
	private static void createProducts() {
		repo.save(new Product("P1","모니터","전자기기", 200000, 5, "모니터에요",0,LocalDateTime.now()));
		repo.save(new Product("P2","키보드","전자기기", 150000, 5, "키보드에요",0,LocalDateTime.now()));
		repo.save(new Product("P3","마우스","전자기기", 50000, 5, "마우스에요",0,LocalDateTime.now()));
	}
}
