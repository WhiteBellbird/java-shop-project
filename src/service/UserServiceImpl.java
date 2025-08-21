package service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domain.User;
import exception.InvalidatedInputException;
import exception.ShopException;
import exception.UserDuplicatedException;
import exception.UserNotfoundException;
import repository.UserRepository;

public class UserServiceImpl implements UserService {
	
	private final UserRepository repository;
	
	public UserServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public User createUser(String username, String email, String password, String address, String phone) 
			throws ShopException  {
		//isRegisteredUserEmail(email); // 유저 이메일이 우리의 파일에 이미 저장되어 있는지
		//validateCreateUser(username, email, password); // 입력한 입력값들이 실제로 타당한 값인지? 빈값이 없다든가
		// UUID.randomUUID()는 랜덤한 String 문자열을 생성하는 static 메서드입니다.
		// 유저 객체를 생성
		User newUser = new User(UUID.randomUUID().toString(), username, email, password, "SILVER", address, 0, null, phone, false, LocalDateTime.now(), null, null);
		// 유저 객체를 생성하고 파일에서 생성된 유저 값을 불러옴
		User saved = repository.saveUser(newUser);
		// 아 참고로, 결과 값을 출력할 때, 비밀번호 같은 민감한 데이터를 출력하지 않도록 합시다.
		//System.out.println(saved.toString());
		return saved;
	}
	
	@Override
	public User updateManager(String userId) throws ShopException {
		User findUser = repository.findUserByUserId(userId);
		// 유저를 찾을 수 없다면 예외처리
		if(findUser == null) {
			throw new UserNotfoundException(String.format("%s 아이디의 유저를 찾을 수 없습니다.",userId));
		}
		// 유저를 관리자로 승격
		findUser.giveManagerAuthentication(); 
		// 리포지토리에서 유저를 업데이트
		User updated = repository.authorizeUser(findUser);
		// 결과 값 출력할 때 비밀번호 같은 민감한 데이터를 출력하지 않도록 합시다.
		return updated;
	}
	

	// DisplayUsers 를 유저 서비스에서 출력하는건 SRP 위반이야.
	// 비즈니스 로직만 처리하는게 유저 서비스의 궁극적인 역할이야
	// 유저 리포지토리에서 조회한 결과를 그대로 그냥 return 해줘서 이 서비스의 역할을 지키는 것이 맞다고 생각해.
	// 다시 한 번, 말하지만 유저 서비스는 '출력'을 담당하는 것이 아닌 '객체의 변화'에 스탠스를 가지는 클래스라는 점
	// 서비스는 리포지토리에서 조회한걸 그대로 리턴해주고, 출력은 IO Layer 에서 해줬으면 해.
	

	@Override
	public User displayUser(String username) {
		User user = repository.findUserByUsername(username);
		return user;
	}
	@Override
	public List<User> withdrawUser( String username) {
		try {
			if(repository.findUserByUsername(username) == null) {
				throw new ShopException("유저가 존재하지 않습니다/ 찾을 수 없습니다");
			}
			repository.delete(repository.findUserByUsername(username));
			repository.commit();
			return repository.getUsersList();
		}catch(ShopException e){
			repository.rollback();
			System.out.println(e.getClass() + ": 퇴출과정중 에러 발생");
		}
		return null;
	}
	@Override
	public User findUser(String username, String password) {
		if(repository.findUserByUsername(username) == null || repository.findUserByUsername(username).getPassword() != password) {
			throw new ShopException("옳바르지 않은 유저네임이거나 패스워드가 틀렸습니다.");
		}
		return repository.findUserByUsername(username);
	}
	@Override
	public List<User> displayAllUsers(String adminUsername, String adminPassword) {
		if(repository.findUserByUsername(adminUsername) == null || repository.findUserByUsername(adminUsername).getPassword() != adminPassword) {
			throw new ShopException("옳바르지 않은 유저네임이거나 패스워드가 틀렸습니다.");
		}
		return repository.getUsersList();
	}
	@Override
	public User changePassword(String username, String password) {
		try {
			if(repository.findUserByUsername(username) == null || repository.findUserByUsername(username).getPassword() != password) {
				throw new ShopException("옳바르지 않은 유저네임이거나 패스워드가 틀렸습니다.");
			}
			User user = repository.findUserByUsername(username);
			repository.delete(user);
			user.updatePassword(password);
			repository.saveUser(user);
			
			repository.commit();
		}catch(ShopException e) {
			repository.rollback();
			System.out.println(e.getClass() + "패스워드 변경중 문제 발생");
		}
		return null;
	}
	@Override
	public User updateUser(User previousUser, User changedUser) {
		try {
			if(repository.findUserByUserId(previousUser.getUserId()) == null) {
				throw new ShopException("옳바르지 않은 유저이거나 찾을수 없습니다.");
			}
			repository.replaceUser(previousUser, changedUser);
			
			repository.commit();
		}catch(ShopException e) {
			repository.rollback();
			System.out.println(e.getClass() + "고객정보 변경중 문제 발생");
		}
		return null;
	}
	@Override
	public User withdrawl(String username, String password) {
		try {
			if(repository.findUserByUsername(username) == null || repository.findUserByUsername(username).getPassword() != password) {
				throw new ShopException("옳바르지 않은 유저네임이거나 패스워드가 틀렸습니다.");
			}
			User user = repository.findUserByUsername(username);
			repository.delete(user);
			
			repository.commit();
		}catch(ShopException e) {
			repository.rollback();
			System.out.println(e.getClass() + "회원 탈퇴중 문제 발생");
		}
		return null;
	}

}
