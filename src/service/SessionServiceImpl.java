package service;
import java.util.*;

import domain.User;
import exception.UserNotfoundException;
import helper.PasswordEncoder;
import repository.*;

public class SessionServiceImpl implements SessionService{
	private HashMap<String, User> sessionIdList;
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

    public SessionServiceImpl(UserRepository userRepository,
							  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        sessionIdList = new HashMap<>(); 
    }

    @Override
	public void login(String username, String password) {
		User user = findUserByUsername(username);
		String encoded = passwordEncoder.encode(password);
		if(user.getPassword().equals(encoded)) {
			sessionIdList.put(user.getUsername(), user);
		}
	}

	@Override
	public void logout() {
		sessionIdList.clear();
	}

	@Override
	public User getLoginUser(String username) {
		User user = sessionIdList.get(username);
        return user;
	}


	@Override
	public long loginCount() {
		return sessionIdList.size();
	}

	@Override
	public User getLoggedInUser() {
		return sessionIdList.get(0);
	}

	private User findUserByUsername(String username) {
		Optional<User> userByUsername = Optional.of(userRepository
				.findUserByUsername(username));
		return userByUsername
				.orElseThrow(() -> new UserNotfoundException(String.format("username %s not found", username)));
	}
}
