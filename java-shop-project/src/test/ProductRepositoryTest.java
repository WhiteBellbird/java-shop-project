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
		repo.rollback();
		createProducts();
		displayAllProducts();
		displayFindById("P1");
        displayFindById("P99");	//존재하지 않는 id테스트
		deleteProducts("P2");
		updateProducts("P1", "휴대폰", 100000);
	}

	private static void displayFindById(String productId) {
		Optional<Product> found = repo.findById(productId);
        if (found.isPresent()) {
            System.out.println("찾은 상품: " + found.get());
        } else {
            System.out.println("찾는 상품이 없습니다.");
        }	
	}

	private static void deleteProducts(String productId) {
		Optional<Product> toDelete = repo.findById(productId);
        toDelete.ifPresent(product -> {
            System.out.println("삭제 전 상품 개수: " + repo.findAll().size());
            repo.delete(product);
            repo.commit();
            System.out.println("삭제 후 상품 개수: " + repo.findAll().size());
        });
	}
	private static void updateProducts(String productId, String productName, int price) {
		Optional<Product> update = repo.findById(productId);
        update.ifPresent(product -> {
            System.out.println("수정 전: " + product);
            product.setName(productName);
            product.setPrice(price);
            repo.save(product);
            repo.commit();
            System.out.println("수정 후: " + repo.findById(productId).get());
        });
	}

	private static void displayAllProducts() {
		List<Product> products = repo.findAll();
		products.forEach(System.out::println);
	}
	private static void createProducts() {
		repo.save(new Product("P1","모니터","전자기기", 200000, 5, "모니터에요",LocalDateTime.now()));
		repo.save(new Product("P2","키보드","전자기기", 150000, 5, "키보드에요",LocalDateTime.now()));
		repo.save(new Product("P3","마우스","전자기기", 50000, 5, "마우스에요",LocalDateTime.now()));
		repo.commit();
	}
}
