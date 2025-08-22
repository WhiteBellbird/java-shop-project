package controller;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domain.*;
import exception.InvalidatedInputException;
import exception.ShopException;
import exception.UserDuplicatedException;
import exception.UserNotfoundException;
import repository.*;
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
		return userService.updateManager(user.getUserId());		
	}
	public List<User> displayAllUser(User user) throws ShopException{
		return userService.displayAllUsers(user.getUsername(), user.getPassword());
	}
	public List<User> withdrawUser(String username) throws ShopException{
		return userService.withdrawUser(username);
	}
	public User findUser(User user) throws ShopException{
		return userService.findUser(user.getUsername(), user.getPassword());
	}
	public User changePassword(String username, String password) throws ShopException{
		return userService.changePassword(username, password);
	}
	public User updateUser(User user, User user2) throws ShopException{
		return userService.updateUser(user, user2);
	}
	public void withdrawl(User user) throws ShopException{
		userService.withdrawl(user.getUsername(), user.getPassword());
	}	
	public boolean checkPassword(String password, String password2) throws ShopException{
		return userService.CheckPassword(password, password2);
	}
	public boolean validateChoice(String choice) throws ShopException{
		if(choice.charAt(0) != 'y' || choice.charAt(0) != 'Y' || choice.charAt(0) != 'n' || choice.charAt(0) != 'N' || choice.isBlank() || choice.isEmpty()) {
    		throw new ShopException("Y/y 또는 N/n 으로 대답해주시기 바랍니다");
    	}
		return userService.validateChoice(choice);
	}
}























