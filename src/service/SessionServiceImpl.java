package service;
import java.util.*;

import domain.User;
<<<<<<< HEAD
import exception.ShopException;
=======
import exception.UserAuthenticationException;
>>>>>>> origin/lsek/dev
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
<<<<<<< HEAD
        this.userService = new UserServiceImpl(userRepository);
        sessionIdList = new HashMap<>(); 
=======
        sessionIdList = new HashMap<>();
>>>>>>> origin/lsek/dev
    }

    @Override
	public void login(String username, String password) {
<<<<<<< HEAD
    	// Exception?
		User user = findUserByUsername(username);
		String encoded = passwordEncoder.encode(password);
		System.out.println(encoded);
		if(user.getPassword().equals(encoded)) {
			sessionIdList.put(user.getUsername(), user);
		}else {
			throw new ShopException("비밀번호 불일치 - login");
=======
    	
		User user = Optional.of(findUserByUsername(username)).orElseThrow(() -> new UserNotfoundException(String.format("username " +
				"%s not found", username)));
		String decoded = passwordEncoder.decode(user.getPassword());
		if (!password.equals(decoded)) {
			throw new UserAuthenticationException(String.format("password does not match"));
>>>>>>> origin/lsek/dev
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
		return sessionIdList.values().stream().findFirst().get();
	}

	@Override
	public void updateSessionUser(User user) {
		sessionIdList.put(user.getUsername(), user);
	}
	private User findUserByUsername(String username) {
		//userService로 display 해봄 repository 가 의심스러워서
		Optional<User> userByUsername = Optional.of(userRepository.findUserByUsername(username));
		return userByUsername
				.orElseThrow(() -> new UserNotfoundException(String.format("username %s not found", username)));
	}
}
