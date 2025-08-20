package service;
import java.util.*;

import domain.User;
import exception.UserNotfoundException;
import repository.*;

public class SessionServiceImpl implements SessionService{
	private HashMap<String, User> sessionIdList;
	private UserRepository userRepository;

    public SessionServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
		sessionIdList = new HashMap<>();
    }

    @Override
	public void successlogin(String username, String password) {
		User user = findUserByUsername(username);
		if(user.getPassword().equals(password)) {
			sessionIdList.put(user.getUsername(), user);
		}
	}

	@Override
	public void successLogout(String username) {
		User userByUsername = findUserByUsername(username);
		if(userByUsername != null) {
			sessionIdList.remove(userByUsername.getUsername());
		}
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

	private User findUserByUsername(String username) {
		Optional<User> userByUsername = Optional.of(userRepository
				.findUserByUsername(username));
		return userByUsername
				.orElseThrow(() -> new UserNotfoundException(String.format("username %s not found", username)));
	}
}
