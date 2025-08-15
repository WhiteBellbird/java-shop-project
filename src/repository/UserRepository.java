package repository;

import java.util.List;

import domain.User;

public interface UserRepository {

	User saveUser(User user);
	
	User findUserByEmail(String email);
	
	User findUserByUserId(String userId);
	
	User authorizeUser(User user);
	
	User replaceUser(User previousUser, User changedUser);
	
	List<User> getUsersList();

	public void resetData();
	
}
