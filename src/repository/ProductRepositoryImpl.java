package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import domain.Product;
import persistence.FileManager;

public class ProductRepositoryImpl implements ProductRepository {

	private final Path DATA_FILE = Paths.get("productData", "products.dat");
	private List<Product> products;
	private List<Product> tmpProducts;

	public ProductRepositoryImpl() {
		try {
			Files.createDirectories(DATA_FILE.getParent());
			load();               // 초기 데이터 로딩
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


	@Override
	public List<Product> findAll() {
		return new ArrayList<>(products); // 깊은 복사 반환
	}

	@Override
	public Optional<Product> findById(String productId) {
		return products.stream().filter(p -> p.getProductId().equals(productId)).findFirst();
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
	public Optional<Product> findByName(String productName) {
		return products.stream().filter(product -> product.getName().equals(productName)).findFirst();
	}

	@Override
	public List<Product> findByCategory(String categoryName) {
		return products.stream().filter(p -> p.getCategory().equals(categoryName)).collect(Collectors.toList());
	}

	@Override
	public List<Product> findByPrice() {
		Comparator<Product> priceComparator = Comparator.comparing(Product::getPrice);
		return products.stream().sorted(priceComparator).collect(Collectors.toList());
	}

	@Override
	public Optional<Product> findByLastestProduct() {
		Comparator<Product> lastest = Comparator.comparing(Product::getRegistrationDate).reversed();
		return products.stream().sorted(lastest).findFirst();
	}

	@Override
	public void delete(Product product) {
		products.remove(product);
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

    @Override
    public void clearAll() {
        products.clear();
        tmpProducts.clear();
        FileManager.writeObject(DATA_FILE, products);
    }

    private List<Product> deepCopy(List<Product> source) {
		return new ArrayList<>(source);
	}
}
