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
	
	User updateManager(String userId);
	
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
