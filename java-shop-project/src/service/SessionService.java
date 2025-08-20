package service;

public interface SessionService {
	void successlogin(String username, String password);
	
	void successLogout(String username);
	
	//String getSessionId(String UserId);
	
	long loginCount();
}
