package service;

public interface SessionService {
	void successlogin(String userId);
	
	void successLogout(String userId);
	
	//String getSessionId(String UserId);
	
	long loginCount();
}
