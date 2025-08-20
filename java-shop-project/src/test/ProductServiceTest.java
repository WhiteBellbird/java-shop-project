package test;

import java.time.LocalDateTime;
import java.util.List;
import domain.Product;
import exception.ProductNotfoundException;
import repository.ProductRepositoryImpl;
import service.ProductService;
import service.ProductServiceImpl;

public class ProductServiceTest {
	static ProductRepositoryImpl repo = new ProductRepositoryImpl();
	static ProductService service = new ProductServiceImpl(repo);
	
	public static void main(String[] args) {
				
		addProduct();
		getAllProduct();
		getProductById();
		updateProduct();
		deleteProduct();		
	}

	private static void deleteProduct() {
		try {
			System.out.println("삭제 전: "+service.getAllProducts().size());
			Product p2 = service.getProductById("P2");
			service.deleteProduct("P2");
			System.out.println("삭제 후: "+service.getAllProducts().size());
		}catch(ProductNotfoundException e) {
			System.out.println("오류 발생: "+e.getMessage());
		}
		
	}

	private static void updateProduct() {
		try {
			Product productUpdate = service.getProductById("P1");
			System.out.println("수정 전: "+ productUpdate);
			productUpdate.setName("마우스");
			productUpdate.setPrice(50000);
			service.updateProduct(productUpdate);
			System.out.println("수정 후: "+service.getProductById("P1"));
		}catch(ProductNotfoundException e) {
			System.out.println("오류 발생: "+e.getMessage());
		}
	}

	private static void getProductById() {
		//조회 성공
		try {
			Product found = service.getProductById("P1");
			System.out.println("조회 상품: "+found);
		} catch (ProductNotfoundException e) {
			System.out.println("조회 실패: "+e.getMessage());
		}
		//존재하지 않는 ID 조회 실패
		try {
			service.getProductById("P99");
			System.out.println("존재하지않는 상품입니다.");
		}catch(ProductNotfoundException e) {
			System.out.println(e.getMessage());
		}
		
	}

	private static void getAllProduct() {
		List<Product> products = service.getAllProducts();
		products.forEach(System.out::println);
		System.out.println("전체 상품 조회");
	}
	
	
	//상품ID를 null로 설정하여 자동생성 테스트
	private static void addProduct() {
		Product p1 = new Product(null, "모니터", "전자기기", 200000, 5, "모니터에요", LocalDateTime.now());
		Product p2 = new Product(null, "키보드", "전자기기", 150000, 5, "키보드에요", LocalDateTime.now());
		service.addProduct(p1);
		service.addProduct(p2);
		System.out.println("상품 생성");
	}
}
