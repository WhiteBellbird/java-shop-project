package test;

import exception.ShopException;
import repository.UserRepositoryImpl;
import service.*;
import domain.*;
import repository.*;

public class UserServiceTest {
	static UserRepositoryImpl repo = new UserRepositoryImpl();
	static UserService service = new UserServiceImpl(new UserRepositoryImpl());
	
	
	public static void main(String[] args) {
		
		
		repo.resetData();
		creatUser();
		updateUser();
		
		
	}
	public static void creatUser() {
		try {
			User user1 = service.createUser("susanLee", "susan9973@gmail.com", "Asdf1234!", null, null);
			User user2 = service.createUser("bruceLee", "bruce9973@gmail.com", "Asdf1234!", null, null);	
		} catch (ShopException e) {
			e.printStackTrace();
		}
	}
	public static void updateUser() {
		try {
			service.updateManager(repo.findUserByEmail("susan9973@gmail.com").getUserId());
		} catch (ClassNotFoundException | ShopException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
