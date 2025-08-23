package controller;

import java.time.LocalDateTime;
import java.util.List;

import domain.Product;
import exception.ShopException;
import service.ProductService;

public class ProductValidationController {
    private ProductService productService;

    public ProductValidationController(ProductService productService) {
        this.productService = productService;
    }	
	public boolean validateCreateProduct(String productId, String name, String category, int price, int stock, String description) {
		if(productId.isEmpty() || name.isEmpty() || category.isEmpty() || price <= 0 || stock <= 0 || description.isEmpty()) {
			return false;
		}
		return true;
	}
    public void addProduct(String productId, String name, String category, int price, int stock, String description) throws ShopException {
        if (!validateCreateProduct(productId, name, category, price, stock, description)) {
            throw new ShopException("모든 상품 정보를 정확히 입력해야 합니다.");
        }
        Product product = new Product(productId, name, category, price, stock, description, LocalDateTime.now());
        productService.addProduct(product);
    }
    public List<Product> getAllProducts() throws ShopException {
        return productService.getAllProducts();
    }
    public Product getProductById(String productId) throws ShopException {
        return productService.getProductById(productId);
    }
    public void updateProduct(Product product) throws ShopException {
        productService.updateProduct(product);
    }
    public void deleteProduct(String productId) throws ShopException {
        productService.deleteProduct(productId);
    }
    public void reduceStock(String productId, int quantity) throws ShopException {
        productService.reduceStockByProductId(productId, quantity);
    }
	
}
