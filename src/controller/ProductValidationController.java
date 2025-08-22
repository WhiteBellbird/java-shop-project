package controller;

public class ProductValidationController {
	public boolean validateCreateProduct(String productId, String name, String category, int price, int stock, String description) {
		if(productId.isEmpty() || name.isEmpty() || category.isEmpty() || price <= 0 || stock <= 0 || description.isEmpty()) {
			return false;
		}
		return true;
	}
}
