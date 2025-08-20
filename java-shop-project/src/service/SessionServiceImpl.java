package service;
import java.util.*;
import repository.*;

public class SessionServiceImpl implements SessionService{
	private HashMap<String, String> sessionIdList;
	private UserRepository userRepository;

    public SessionServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
		sessionIdList = new HashMap<>();
    }

    @Override
	public void successlogin(String username, String password) {

	}

	@Override
	public void successLogout(String username) {

	}

	@Override
	public long loginCount() {
		return sessionIdList.size();
	}	
}
