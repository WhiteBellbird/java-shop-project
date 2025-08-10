package service;

import domain.User;
import exception.ShopException;
import exception.UserDuplicatedException;

public interface UserService {

	public User createUser(String username, String email, String password, String address, String phone) throws ShopException;
	
	public User updateManager(String userId) throws ShopException, ClassNotFoundException;
}
