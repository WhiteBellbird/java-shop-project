package controller;

import domain.Product;
import exception.InvalidatedInputException;
import exception.ShopException;
import service.ProductService;

import java.util.List;

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    public List<Product> getProductsByPrice() {
        return productService.getProductsByPrice();
    }

    public List<Product> getProductsByBestSeller() {
        return productService.getProductsByBestSeller();
    }

    public Product getProductByRegistrationDate() {
        return productService.findProductByRegistrationDate();
    }

    public List<Product> getProductsByCategory(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new InvalidatedInputException("Category name cannot be empty.");
        }
        return  productService.getProductsByCategory(categoryName);
    }

    public Product getProductByProductName(String productName) throws ShopException {
        if (productName == null || productName.trim().isEmpty()) {
            throw new InvalidatedInputException("Product name cannot be empty.");
        }
        return productService.getProductByProductName(productName);
    }

    public Product createProduct(String name, String category, int price, int stock, String description) throws ShopException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidatedInputException("Product name cannot be empty.");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new InvalidatedInputException("Product category cannot be empty.");
        }
        if (price <= 0) {
            throw new InvalidatedInputException("Product price must be greater than 0.");
        }
        if (stock < 0) {
            throw new InvalidatedInputException("Product stock cannot be negative.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new InvalidatedInputException("Product description cannot be empty.");
        }
        return productService.createProduct(name, category, price, stock, description);
    }

    public Product addProductStock(String productName, int stock) throws ShopException {
        if (productName == null || productName.trim().isEmpty()) {
            throw new InvalidatedInputException("Product name cannot be empty.");
        }
        if (stock <= 0) {
            throw new InvalidatedInputException("Stock to add must be greater than 0.");
        }
        return productService.addProductStock(productName, stock);
    }

    public Product subtractProductStock(String productName, int stock) throws ShopException {
        if (productName == null || productName.trim().isEmpty()) {
            throw new InvalidatedInputException("Product name cannot be empty.");
        }
        if (stock <= 0) {
            throw new InvalidatedInputException("Stock to subtract must be greater than 0.");
        }
        return productService.subtractProductStock(productName, stock);
    }

    public Boolean deleteProduct(String productName) throws ShopException {
        if (productName == null || productName.trim().isEmpty()) {
            throw new InvalidatedInputException("Product name cannot be empty.");
        }
        return productService.deleteProduct(productName);
    }
}
