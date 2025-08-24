package test;

import domain.Order;
import domain.Product;
import domain.User;
import helper.PasswordEncoder;
import helper.PasswordEncoderImpl;
import repository.CartRepository;
import repository.CartRepositoryImpl;
import repository.OrderRepository;
import repository.OrderRepositoryImpl;
import repository.ProductRepository;
import repository.ProductRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.*;

public class OrderServiceTest {
	
	public static void main(String[] args) {
		UserRepository userRepository = new UserRepositoryImpl();
		PasswordEncoder passwordEncoder = new PasswordEncoderImpl();
		UserService userService = new UserServiceImpl(userRepository, passwordEncoder);
	    OrderRepository orderRepository = new OrderRepositoryImpl();
	    CartRepository cartRepository = new CartRepositoryImpl();
	    ProductRepository productRepository = new ProductRepositoryImpl();
	    ProductService productService = new ProductServiceImpl(productRepository);

	    CartService cartService = new CartServiceImpl(cartRepository, userRepository, productRepository);
	    
	    OrderService orderService = new OrderServiceImpl(orderRepository, cartRepository,
	                            productRepository, userRepository);
	    
	    
	    Product product = productService.createProduct("apple", "fruit", 1000, 1000, null);
	    Product product2 = productService.createProduct("grapes", "fruit", 1000, 1000, null);
	    Product product3 = productService.createProduct("kiwi", "fruit", 1000, 1000, null);
	    Product product4 = productService.createProduct("samsung", "electronic", 1000, 1000, null);
	    Product product5 = productService.createProduct("lg", "electronic", 1000, 1000, null);
	    
	 
	    User user = userService.createUser("TEST2", "TEST2@gmail.com", "Asdf1234!", null, "010-3456-4567");
	    
	    
	    cartService.addProductByCart(user.getUserId(), product.getProductId(), 2);
	    cartService.addProductByCart(user.getUserId(), product2.getProductId(), 2);
	    cartService.addProductByCart(user.getUserId(), product3.getProductId(), 2);
	    
	    orderService.createOrder(user.getUserId(), product.getProductId(), 20000, 2, "홍은동");
	    
	}
}
