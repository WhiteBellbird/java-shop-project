package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import domain.Product;
import persistence.FileManager;

public class ProductRepositoryImpl implements ProductRepository {

	private final Path DATA_FILE = Paths.get("productData", "products.dat");
	private List<Product> products;
	private List<Product> tmpProducts;
	private int sequence = 0;

	public ProductRepositoryImpl() {
		try {
			Files.createDirectories(DATA_FILE.getParent());
			load();               // 초기 데이터 로딩
			initializeSequence(); // 시퀀스 초기화
		} catch (IOException e) {
			System.out.println("데이터 파일을 위한 폴더 생성 불가");
		}
	}

	private void load() {
		List<Product> read = FileManager.readObject(DATA_FILE);
		if (read != null) {
			products = read;
			tmpProducts = deepCopy(read);
		} else {
			products = new ArrayList<>();
			tmpProducts = new ArrayList<>();
		}
	}

	private void initializeSequence() {
		int maxId = 0;
		for (Product product : products) {
			try {
				int currentId = Integer.parseInt(product.getProductId().substring(1));
				if (currentId > maxId) {
					maxId = currentId;
				}
			} catch (NumberFormatException e) {
				System.out.println("잘못된 ID 입니다: " + product.getProductId());
			}
		}
		sequence = maxId + 1; // 다음 상품 ID를 위해 1 증가
	}

	@Override
	public List<Product> findAll() {
		return new ArrayList<>(products); // 깊은 복사 반환
	}

	@Override
	public Product findById(String productId) {
		for (Product product : products) {
			if (product.getProductId().equals(productId)) {
				return product;
			}
		}
		return null;
	}

	@Override
	public Product save(Product product) {
		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).getProductId().equals(product.getProductId())) {
				products.set(i, product); // 기존 객체 교체
				return product;
			}
		}
		products.add(product); // 없으면 새로 추가
		return product;
	}

	@Override
	public void delete(String productId) {
		products.removeIf(p -> p.getProductId().equals(productId));
	}

	@Override
	public int getNextProductId() {
		return sequence++;
	}

	@Override
	public void commit() {
		FileManager.writeObject(DATA_FILE, products);
		tmpProducts = deepCopy(products);
	}

	@Override
	public void rollback() {
		products = deepCopy(tmpProducts);
		FileManager.writeObject(DATA_FILE, products);
	}

	private List<Product> deepCopy(List<Product> source) {
		return new ArrayList<>(source);
	}
}
