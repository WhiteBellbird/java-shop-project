package repository;

import java.util.List;

import domain.User;

public interface UserRepository {

	User saveUser(User user);
	
	User findUserByEmail(String email);
	
	User findUserByUserId(String userId);
	
	User updateUser(User user);
	
	User replaceUser(User previousUser, User changedUser);

	public void resetData();
	
	public void display();
	
	
}
