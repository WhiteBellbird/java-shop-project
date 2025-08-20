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

	public User createUser(String username, String email, String password, String address, String phone) throws ShopException;
	
	public User updateManager(String userId) throws ShopException, ClassNotFoundException;
	
	public User findUser(String username, String password);
	
	public User changePassword(String username, String paswword);
	
	public User updateUser(User previousUser, User changedUser);
	
	public User withdrawl(String username, String password);
	
	
	
	// 관리자용
	public List<User> displayAllUsers(String adminUsername, String adminPassword);
	
	public User displayUser(String username); // 고객 정보 찾기
	
	//public List<LocalDateTime> displayLogin(String username);
	
	//public List<LocalDateTime> displayLogout(String username);
	
	public List<User> withdrawUser(String username);
}
