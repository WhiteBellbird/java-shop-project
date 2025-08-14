package test;

import exception.ShopException;
import repository.UserRepositoryImpl;
import service.*;
import domain.*;
import repository.*;

public class UserServiceTest {
	static UserRepositoryImpl repo = new UserRepositoryImpl();
	static UserService service = new UserServiceImpl(new UserRepositoryImpl());
	
	static User user = new User("001" ,"susanLee", "susan9973@gmail.com", "Asdf1234!", null, null);
	
	public static void main(String[] args) {
		creatUser();
		updateUser();
	}
	public static void creatUser() {
		try {
			User user1 = service.createUser("susanLee", "susan9973@gmail.com", "Asdf1234!", null, null);
			User user2 = service.createUser("bruceLee", "bruce9973@gmail.com", "Asdf1234!", null, null);
			
			repo.saveUser(user1);
			repo.saveUser(user2);
			
		} catch (ShopException e) {
			e.printStackTrace();
		}
	}
	public static void updateUser() {
		try {
			service.updateManager(user.getUserId());
			
		} catch (ClassNotFoundException | ShopException e) {
			e.printStackTrace();
		}
	}
}
