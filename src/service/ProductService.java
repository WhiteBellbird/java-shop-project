package service;

import java.util.List;

import domain.Product;

public interface ProductService {

	List<Product> getAllProducts();
	
	Product getProductByProductName(String productName);
	
	Product createProduct(String name, String category, int price, int stock, String description);
	
	Boolean updateProduct(Product product);
	
	Boolean deleteProduct(String productName);


	Product reduceStockByProductId(String productName, int quantity);
}
