package repository;

import java.util.List;

import domain.User;

public interface UserRepository {

	User saveUser(User user);
	
	User findUserByEmail(String email);
	
	User findUserByUserId(String userId);
	
	User updateUser(User user);
	
	User changeUser(User previousUser, User changedUser);

	public void display() throws ClassNotFoundException;
}
