package controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domain.*;
import exception.InvalidatedInputException;
import exception.ShopException;
import service.*;

public class UserController {

	private final UserService userService;

	public UserController(UserService userservice) {
		this.userService = userservice;
	}

	public User createUser(String username, String email, String password, String address, String phone) {
		if (username == null || username.trim().isEmpty()) {
			throw new InvalidatedInputException("Username cannot be empty.");
		}
		if (email == null || !email.contains("@")) {
			throw new InvalidatedInputException("Invalid email format.");
		}
		if (password == null || password.length() < 5) {
			throw new InvalidatedInputException("Password must be at least 5 characters.");
		}

		Pattern patternUpper = Pattern.compile("[A-Z]");
		Pattern patternSpecial = Pattern.compile("[!@#$%^&*]");
		Matcher matcherUpper = patternUpper.matcher(password);
		Matcher matcherSpecial = patternSpecial.matcher(password);

		if (!matcherUpper.find() || !matcherSpecial.find()) {
			throw new InvalidatedInputException("Password must contain at least one uppercase letter and one special character (!,@,#,$,%,^,&,*).");
		}

		if (address == null || address.trim().isEmpty()) {
			throw new InvalidatedInputException("Address cannot be empty.");
		}
		if (phone == null || phone.trim().isEmpty()) {
			throw new InvalidatedInputException("Phone cannot be empty.");
		}

		return userService.createUser(username, email, password, address, phone);
	}

	// session service 에서 유저 가져온다
	public User updateManager(User user) throws ShopException {
		if (user == null) {
			throw new InvalidatedInputException("User cannot be null.");
		}
		return userService.updateManager(user);
	}

	@Deprecated
	public User displayUser(User user) throws ShopException {
		if (user == null || user.getUsername() == null || user.getPassword() == null) {
			throw new InvalidatedInputException("User credentials cannot be empty.");
		}
		return userService.findUser(user.getUsername(), user.getPassword());
	}

	public List<User> withdrawUserByAdmin(User user, String username) {
		if (user == null) {
			throw new InvalidatedInputException("Admin user cannot be null.");
		}
		if (username == null || username.trim().isEmpty()) {
			throw new InvalidatedInputException("Target username cannot be empty.");
		}
		return userService.withdrawUserByAdmin(user, username);
	}

	public User findUser(User user) throws ShopException {
		if (user == null || user.getUsername() == null || user.getPassword() == null) {
			throw new InvalidatedInputException("User credentials cannot be empty.");
		}
		return userService.findUser(user.getUsername(), user.getPassword());
	}

	public List<User> findAllUsers(User user) throws ShopException {
		if (user == null) {
			throw new InvalidatedInputException("Admin user cannot be null.");
		}
		return userService.displayAllUsersByAdmin(user);
	}

	public User changePassword(User user, String currentPwd, String newPwd) throws ShopException {
		if (user == null) {
			throw new InvalidatedInputException("User cannot be null.");
		}
		if (currentPwd == null || currentPwd.trim().isEmpty()) {
			throw new InvalidatedInputException("Current password cannot be empty.");
		}
		if (newPwd == null || newPwd.length() < 5) {
			throw new InvalidatedInputException("New password must be at least 5 characters.");
		}
		return userService.changePassword(user, currentPwd, newPwd);
	}

	public User updateUser(User user, String email, String address, String phone) throws ShopException {
		if (user == null) {
			throw new InvalidatedInputException("User cannot be null.");
		}
		if (email == null || !email.contains("@")) {
			throw new InvalidatedInputException("Invalid email format.");
		}
		if (address == null || address.trim().isEmpty()) {
			throw new InvalidatedInputException("Address cannot be empty.");
		}
		if (phone == null || phone.trim().isEmpty()) {
			throw new InvalidatedInputException("Phone cannot be empty.");
		}
		return userService.updateUser(user, email, address, phone);
	}

	public void withdrawal(User user,String password) throws ShopException {
		if (user == null) {
			throw new InvalidatedInputException("User cannot be null.");
		}

		userService.withdrawal(user,password);
	}
}
