package service;
import java.util.*;
import repository.*;

public class SessionServiceImpl implements SessionService{
	private HashMap<String, String> sessionIdList = new HashMap<>();
	
	@Override
	public void successlogin(String userId) {
		String sessionId = UUID.randomUUID().toString();
		sessionIdList.put(userId, sessionId);
	}

	@Override
	public void successLogout(String userId) {
		sessionIdList.remove(userId);
	}

	@Override
	public long loginCount() {
		return sessionIdList.size();
	}	
}
