package service;

import domain.User;

public interface SessionService {
	void successlogin(String username, String password);
	
	void successLogout(String username);
	
	//String getSessionId(String UserId);

	User getLoginUser(String username);
	
	long loginCount();
}
