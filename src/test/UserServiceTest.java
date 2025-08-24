package test;

import exception.ShopException;
import repository.UserRepositoryImpl;
import service.*;
import domain.*;

public class UserServiceTest {
	static UserRepositoryImpl repo = new UserRepositoryImpl();
//	static UserService service = new UserServiceImpl(new UserRepositoryImpl());
	
	
	public static void main(String[] args) {
		
		
		
		repo.resetData();
//		System.out.println(service.displayUserByAdmin("KWONLEE"));
		//service.withdrawUser(null);
		creatUser();
		updateUser();
		
		
	}
	public static void creatUser() {
		try {
//			User user1 = service.createUser("susanLee", "susan9973@gmail.com", "Asdf1234!", null, null);
//			User user2 = service.createUser("bruceLee", "bruce9973@gmail.com", "Asdf1234!", null, null);
		} catch (ShopException e) {
			e.printStackTrace();
		}
	}
	public static void updateUser() {
		try {
//			service.updateManager(service.displayUserByAdmin("KWONLEE").getUserId());
		} catch (ShopException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
