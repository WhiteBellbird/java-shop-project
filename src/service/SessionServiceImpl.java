package service;
import java.util.*;

import domain.User;
import exception.UserAuthenticationException;
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
    	
    	// Exception 을 던질까 말까 - userName & password 틀리면 *******************************************
		User user = Optional.of(findUserByUsername(username)).orElseThrow(() -> new UserNotfoundException(String.format("username " +
				"%s not found", username)));
		String encoded = passwordEncoder.encode(password);
		if (!user.getPassword().equals(encoded)) {
			throw new UserAuthenticationException(String.format("password does not match"));
		}
		sessionIdList.put(user.getUsername(), user);
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

	@Override
	public void updateSessionUser(User user) {
		sessionIdList.put(user.getUsername(), user);
	}

	private User findUserByUsername(String username) {
		Optional<User> userByUsername = Optional.of(userRepository
				.findUserByUsername(username));
		return userByUsername
				.orElseThrow(() -> new UserNotfoundException(String.format("username %s not found", username)));
	}
}
