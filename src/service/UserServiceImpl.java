package service;

import java.util.UUID;

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
		// TODO Auto-generated method stub
		isRegisteredUserEmail(email); // 유저 이메일이 우리의 파일에 이미 저장되어 있는지
		validateCreateUser(username, email, password); // 입력한 입력값들이 실제로 타당한 값인지? 빈값이 없다든가
		// UUID.randomUUID()는 랜덤한 String 문자열을 생성하는 static 메서드입니다.
		// 유저 객체를 생성
		User newUser = new User(UUID.randomUUID().toString(), username, email, password, address, phone);
		// 유저 객체를 생성하고 파일에서 생성된 유저 값을 불러옴
		User saved = repository.saveUser(newUser);
		// 아 참고로, 결과 값을 출력할 때, 비밀번호 같은 민감한 데이터를 출력하지 않도록 합시다.
		return saved;
	}
	
	@Override
	public User updateManager(String userId) throws ShopException {
		User findUser = repository.findUserByUserId(userId); // exception issue
		// 유저를 찾을 수 없다면 예외처리
		if(findUser == null) {
			throw new UserNotfoundException(String.format("%s 아이디의 유저를 찾을 수 없습니다.",userId));
		}
		// 유저를 관리자로 승격
		findUser.giveManagerAuthentication();
		// 리포지토리에서 유저를 업데이트
		User updated = repository.updateUser(findUser);
		// 결과 값 출력할 때 비밀번호 같은 민감한 데이터를 출력하지 않도록 합시다.
		return updated;
	}

	private void isRegisteredUserEmail(String email) throws UserDuplicatedException {;
		// TODO Auto-generated method stub
		User user = repository.findUserByEmail(email); // exception issue
		if(user != null) {
			throw new UserDuplicatedException(String.format("%s은 이미 등록된 사용자"
					+ "입니다.",email));
		}
	}
	
	private void validateCreateUser(String username, String email, String password) 
	throws InvalidatedInputException {
		if(username.isEmpty() || email.isEmpty() || password.isEmpty()) {
			throw new InvalidatedInputException("[CreateUser] 유저 생성 입력값이"
					+ " 잘못되었습니다.");
		}
	}

}
