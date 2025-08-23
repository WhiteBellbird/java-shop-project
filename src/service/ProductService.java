package service;

import java.util.List;

import domain.Product;

public interface ProductService {

	List<Product> getAllProducts();

	List<Product> getProductsByCategory(String categoryName);
	
	Product getProductByProductName(String productName);
	
	Product createProduct(String name, String category, int price, int stock, String description);

	Product addProductStock(String productName, int stock);

	Product subtractProductStock(String productName, int stock);
	
	Boolean deleteProduct(String productName);
}
