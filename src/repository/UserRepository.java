package repository;

import java.util.List;

import domain.User;

public interface UserRepository {

	User saveUser(User user);
	
	User findUserByEmail(String email) throws ClassNotFoundException;
	
	User findUserByUserId(String userId) throws ClassNotFoundException;
	
	User updateUser(User user);
	
	User changeUser(User previousUser, User changedUser);

	public void display() throws ClassNotFoundException;
}
