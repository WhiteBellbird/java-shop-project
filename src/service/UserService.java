package service;

import domain.Order;
import domain.User;
import exception.ShopException;
import exception.UserDuplicatedException;

import java.util.HashMap;

public interface UserService {

	HashMap<String, Order> orders = new HashMap<>();

	public User createUser(String username, String email, String password, String address, String phone) throws ShopException;
	
	public User updateManager(String userId) throws ShopException, ClassNotFoundException;
}
