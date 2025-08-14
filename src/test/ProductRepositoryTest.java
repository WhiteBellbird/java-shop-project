package test;

import java.time.LocalDateTime;
import java.util.List;

import domain.Product;

import repository.ProductRepositoryImpl;

public class ProductRepositoryTest {

	static ProductRepositoryImpl repo = new ProductRepositoryImpl();
	public static void main(String[] args) {
		
		repo.resetData();
		createProducts();
		displayAllProducts();
		displayFindById("P1");
		updateProducts("P1", "휴대폰", 100000);
		deleteProducts("P2");
	}
	private static void deleteProducts(String productId) {
		System.out.println("삭제 전 상품 개수: " + repo.findAll().size());
        repo.delete(productId);
        System.out.println("삭제 후 상품 개수: " + repo.findAll().size());
	}
	private static void updateProducts(String productId, String productName, int price) {
		Product update = repo.findById(productId);
		if(update != null) {
			System.out.println("수정 전: "+update);
			update.setName(productName);
			update.setPrice(price);
            repo.save(update);
            System.out.println("수정 후: " + repo.findById(productId));
		}
	}
	private static void displayFindById(String productId) {
		Product found = repo.findById(productId);
		if(found != null) {
			System.out.println("찾은 상품:" + found);
		}else {
			System.out.println(productId+"에 맞는 상품아이디가 없습니다.");
		}
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
