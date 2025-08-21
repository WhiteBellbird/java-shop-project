package test;


import controller.*;
import domain.User;
import service.*;
import repository.*;

public class UserValidationTest {
	
	public static void main(String[] args) {
		UserRepository userRepository = new UserRepositoryImpl();
		UserService userService = new UserServiceImpl(userRepository);
		UserValidationController ctrl = new UserValidationController(userService);
		
		
		User user1 = userService.createUser("susanLee", "susan9973@gmail.com", "Asdf1234!", null, null);
		User user2 = userService.createUser("bruceLee", "bruce9973@gmail.com", "Asdf1234!", null, null);
		
		
		System.out.println(ctrl.createUser(user1)); 
		System.out.println(ctrl.createUser(user2)); 
		
		
	}
}
