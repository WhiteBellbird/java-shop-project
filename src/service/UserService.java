package service;

import domain.User;
import exception.InvalidatedInputException;
import exception.ShopException;
import exception.UserDuplicatedException;

public interface UserService {

	public User createUser(String username, String email, String password, String address, String phone) throws ShopException;
	
	public User login(String email, String password) throws InvalidatedInputException;
	
	public User logout(String email, String password);
	
	public User updateManager(String userId) throws ShopException, ClassNotFoundException;
	
}
