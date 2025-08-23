package test;


import controller.*;
import domain.User;
import service.*;
import repository.*;

public class UserValidationTest {
	
	public static void main(String[] args) {
		UserRepository userRepository = new UserRepositoryImpl();
		UserService userService = new UserServiceImpl(userRepository,null);
		UserController ctrl = new UserController(userService);
		
		
		User user1 = userService.createUser("susanLee", "susan9973@gmail.com", "Asdf1234!", null, null);
		User user2 = userService.createUser("bruceLee", "bruce9973@gmail.com", "Asdf1234!", null, null);
		
		
		System.out.println(ctrl.createUser("susanLee", "susan9973@gmail.com", "Asdf1234!", null, null)); 
		System.out.println(ctrl.createUser("bruceLee", "bruce9973@gmail.com", "Asdf1234!", null, null)); 
		
		
	}
}
