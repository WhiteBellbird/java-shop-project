package service;

import domain.User;

public interface SessionService {
	void login(String username, String password);
	
	void logout();
	
	//String getSessionId(String UserId);

	User getLoginUser(String username);
	
	long loginCount();

	User getLoggedInUser();

	void updateSessionUser(User user);

    Boolean isLoggedIn();
}
