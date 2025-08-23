package service;
import java.util.*;

import domain.User;
import exception.ShopException;
import exception.UserNotfoundException;
import helper.PasswordEncoder;
import repository.*;

public class SessionServiceImpl implements SessionService{
	private HashMap<String, User> sessionIdList;
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private UserService userService;

    public SessionServiceImpl(UserRepository userRepository,
							  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = new UserServiceImpl(userRepository);
        sessionIdList = new HashMap<>(); 
    }

    @Override
	public void login(String username, String password) {
    	// Exception?
		User user = findUserByUsername(username);
		String encoded = passwordEncoder.encode(password);
		System.out.println(encoded);
		if(user.getPassword().equals(encoded)) {
			sessionIdList.put(user.getUsername(), user);
		}else {
			throw new ShopException("비밀번호 불일치 - login");
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
		//userService로 display 해봄 repository 가 의심스러워서
		Optional<User> userByUsername = Optional.of(userRepository.findUserByUsername(username));
		return userByUsername
				.orElseThrow(() -> new UserNotfoundException(String.format("username %s not found", username)));
	}
}
