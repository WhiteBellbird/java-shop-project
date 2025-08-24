package service;

import java.util.List;

import domain.Product;

public interface ProductService {

	List<Product> getAllProducts();

	List<Product> getProductsByCategory(String categoryName);

	List<Product> getProductsByPrice();

	List<Product> getProductsByBestSeller();

	Product findProductByRegistrationDate();
	
	Product getProductByProductName(String productName);
	
	Product createProduct(String name, String category, int price, int stock, String description);

	Product addProductStock(String productName, int stock);

    Product subtractProductStock(String productName, int stock);

    Product updateProduct(String originProductName, String newProductName,
                          String category , int price, String description);
	
	Boolean deleteProduct(String productName);
}
