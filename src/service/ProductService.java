package service;

import java.util.List;

import domain.Product;
import exception.ProductNotfoundException;

public interface ProductService {

	List<Product> getAllProducts();
	
	Product getProductById(String productId) throws ProductNotfoundException;
	
	void addProduct(Product product);
	
	void updateProduct(Product product) throws ProductNotfoundException;
	
	void deleteProduct(String productId) throws ProductNotfoundException;


	void reduceStockByProductId(String productId, int quantity) throws ProductNotfoundException;
}
