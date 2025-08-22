package service;

import domain.Order;
import domain.User;
import exception.InvalidatedInputException;
import exception.ShopException;
import exception.UserDuplicatedException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public interface UserService {

	User createUser(String username, String email, String password, String address, String phone);
	
<<<<<<< HEAD
	public boolean CheckPassword(String firstInput, String SecondInput);
	
	boolean validateChoice(String choice);
	
	public User updateManager(String userId);
=======
	User updateManager(String userId);
>>>>>>> 8833fa85db58a26c0fee3d2edf3d625d4a2a23b5
	
	User findUser(String username, String password);
	
	User changePassword(String username, String paswword);
	
	User updateUser(User previousUser, User changedUser);
	
	Boolean withdrawl(String username, String password);
	
	
	
	// 관리자용
	List<User> displayAllUsers(String adminUsername, String adminPassword);
	
	User displayUser(String username); // 고객 정보 찾기
	
	//List<LocalDateTime> displayLogin(String username);
	
	//List<LocalDateTime> displayLogout(String username);
	
	List<User> withdrawUser(String username);
}
