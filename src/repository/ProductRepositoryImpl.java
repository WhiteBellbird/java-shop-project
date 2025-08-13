package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import domain.Product;

public class ProductRepositoryImpl implements ProductRepository{
	private final Path DATA_FILE = Paths.get("productData", "products.dat");
	private int sequence = 0;
	
	public ProductRepositoryImpl() {
		try {
			Files.createDirectories(DATA_FILE.getParent());
			initializeSequence();
		} catch (IOException e) {
			System.out.println("데이터 파일을 위한 폴더 생성 불가");
		}
	}
	//파일에 저장된 상품들의 ID 중 최댓값을 찾아 시퀀스를 초기화하는 메서드
	private void initializeSequence() {
		 List<Product> products = FileManager.readObject(DATA_FILE);
	        if (products != null && !products.isEmpty()) {
	            int maxId = 0;
	            for (Product product : products) {
	                try {
	                    int currentId = Integer.parseInt(product.getProductId().substring(1));
	                    if (currentId > maxId) {
	                        maxId = currentId;
	                    }
	                } catch (NumberFormatException e) {
	                	System.out.println("잘못된 ID 입니다.");
	                    // ID 형식이 'P123'과 같지 않을 경우 처리
	                }
	            }
	            this.sequence = maxId;
	        }		
	}

	@Override
	public List<Product> findAll() {
		List<Product> products = FileManager.readObject(DATA_FILE);
		return products != null ? products : new ArrayList<>();
	}

	@Override
	public Product findById(String productId) {
		List<Product> products = findAll();
		for(Product product : products) {
			if(product.getProductId().equals(productId)) {
				return product;
			}
		}		
		return null;
	}

	@Override
	public void save(Product product) {
		List<Product> products = findAll();
		for(int i=0; i<products.size(); i++) {
			if(products.get(i).getProductId().equals(product.getProductId())) {
				products.remove(i); //같은 id 삭제
				break;
			}
		}
		products.add(product);	//상품 추가
		FileManager.writeObject(DATA_FILE, products);     //데이터 저장
		
	}

	@Override
	public void delete(String productId) {
		List<Product> products = findAll();
		for(int i=0; i<products.size(); i++) {
			if(products.get(i).getProductId().equals(productId)) {
				products.remove(i); // 상품 삭제
				break;
			}
		}		
		FileManager.writeObject(DATA_FILE, products);     //데이터 저장
		
	}

	@Override
	public int getNextProductId() {
		return sequence++;	//상품 ID에 반환
	}
	@Override
	public void resetData() {
		List<Product> forReset = new ArrayList<>();
		FileManager.writeObject(DATA_FILE, forReset);
	}

	
	
}
