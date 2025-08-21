package service;

import domain.User;

public interface SessionService {
	void login(String username, String password);
	
	void logout(String username);
	
	//String getSessionId(String UserId);

	User getLoginUser(String username);
	
	long loginCount();

	User getLoggedInUser();
}
