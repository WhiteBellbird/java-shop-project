package test;

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
	    OrderRepository orderRepository = new OrderRepositoryImpl();
	    CartRepository cartRepository = new CartRepositoryImpl();
	    ProductRepository productRepository = new ProductRepositoryImpl();
	    UserRepository userRepository = new UserRepositoryImpl();

	    OrderService orderService = new OrderServiceImpl(orderRepository, cartRepository,
	                            productRepository, userRepository);
	    
	    
	    
	}
}
