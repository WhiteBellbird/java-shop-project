package controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domain.*;
import exception.InvalidatedInputException;
import exception.ShopException;
import service.*;

public class UserValidationController{
	private UserService userService;
	public UserValidationController(UserService userservice){
		this.userService = userservice;
	}
	public User createUser(String username, String email, String password, String address, String phone){
		Pattern pattern = Pattern.compile("[A-Z]");
		Pattern pattern2 = Pattern.compile("[!@#$%^&*]");
		Matcher matcher = pattern.matcher(password);
		Matcher matcher2 = pattern2.matcher(password);
		
		if(username.length() < 4 || password.length() < 4 || !email.contains("@") || !matcher.find() || !matcher2.find()) {
			throw new InvalidatedInputException("필수사항:\n아이디 또는 비밀번호 5자리 이상\n비밀번호 최소 한글자는 대문자 그리고 특수문저 !,@,#,$,%,^,&,* 중 포함\n이메일에 '@' 포함");
		}
		return userService.createUser(username, email, password, address, phone);
	}
	
	// session service 에서 유저 가져온다
	public User updateManager(User user) throws ShopException {
		return userService.updateManager(user);
	}
	@Deprecated
	public User displayUser(User user) throws ShopException{
		return userService.findUser(user.getUsername(), user.getPassword());
	}
	public List<User> withdrawUserByAdmin(User user, String username) {
		return userService.withdrawUserByAdmin(user,username);
	}
	public User findUser(User user) throws ShopException{
		return userService.findUser(user.getUsername(), user.getPassword());
	}
	public List<User> findAllUsers(User user) throws ShopException{
		return userService.displayAllUsersByAdmin(user);
	}
	public User changePassword(User user, String currentPwd, String newPwd) throws ShopException{
		return userService.changePassword(user,currentPwd, newPwd);
	}

	public User updateUser(User user, String email, String address, String phone) throws ShopException {
		Pattern pattern2 = Pattern.compile("[!@#$%^&*]");

		if(!email.contains("@") && !pattern2.matcher(email).matches()) {
			throw new InvalidatedInputException("it's not matched email type.");
		}
		if (address.isEmpty() || phone.isEmpty()) {
			throw new InvalidatedInputException("it's not matched phone || address type.");
		}
		return userService.updateUser(user, email, address, phone);
	}
	public void withdrawal(User user) throws ShopException{
		userService.withdrawal(user);
	}	
//	public boolean checkPassword(String password, String password2) throws ShopException{
//		return userService.CheckPassword(password, password2);
//	}
//	public boolean validateChoice(String choice) throws ShopException{
//		if(((choice.charAt(0) != 'y' && choice.charAt(0) != 'Y') && (choice.charAt(0) != 'n' && choice.charAt(0) != 'N')) || choice.isBlank() || choice.isEmpty()) {
//    		throw new ShopException("Y/y 또는 N/n 으로 대답해주시기 바랍니다");
//    	}
//		return userService.validateChoice(choice);
//	}
}























