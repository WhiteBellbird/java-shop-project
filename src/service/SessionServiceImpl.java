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
        
       User user = Optional.of(findUserByUsername(username)).orElseThrow(() -> new UserNotfoundException(String.format("username " +
             "%s not found", username)));
       String decoded = passwordEncoder.decode(user.getPassword());
       if (!password.equals(decoded)) {
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
       return sessionIdList.values().stream().findFirst().get();
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
