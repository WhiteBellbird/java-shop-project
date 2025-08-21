package controller;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domain.*;
import exception.InvalidatedInputException;
import exception.ShopException;
import exception.UserDuplicatedException;
import exception.UserNotfoundException;
import repository.*;
import service.*;

class Result <t> {
	private boolean ok;
	private t data;
	private String error;
	
	public Result(boolean ok, t data, String error){
		this.ok = ok;
		this.data = data;
		this.error = error;
	}
	static <t> Result<t> good(t data) {
		return new Result(true, data, null);
	}
	static <t> Result<t> bad(String error) {
		return new Result(false, null, error);
	}
	@Override
	public String toString() {
		return ok ? "ok: " + data : "bad: " + error;
	}
}







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
	public User updateManager(User user){
		return user;
	}
}























