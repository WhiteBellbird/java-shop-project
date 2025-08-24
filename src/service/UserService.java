package service;

import domain.User;

import java.util.List;

public interface UserService {

	User createUser(String username, String email, String password, String address, String phone);
	
//	boolean CheckPassword(String firstInput, String SecondInput);
	
//	boolean validateChoice(String choice);
	
	User updateManager(User user);

	
	User findUser(String username, String password);
	
	User changePassword(User user,String currentPwd, String newPwd);
	
	User updateUser(User user, String email, String address, String phone);
	
	Boolean withdrawal(User user,String password);
	
	
	
	// 관리자용
	List<User> displayAllUsersByAdmin(User user);
	
	User displayUserByAdmin(User user, String username); // 고객 정보 찾기
	
	//List<LocalDateTime> displayLogin(String username);
	
	//List<LocalDateTime> displayLogout(String username);
	
	List<User> withdrawUserByAdmin(User user, String willDeleteUsername);
}
