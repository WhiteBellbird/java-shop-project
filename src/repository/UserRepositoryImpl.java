package repository;

import domain.User;
import persistence.FileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class UserRepositoryImpl implements UserRepository {

	// DATA_FILE 경로
	private final Path DATA_FILE = Paths.get("userData", "users.dat");

	// 현재 작업 리스트 & commit 백업 리스트
	private List<User> users;
	private List<User> tmpUsers;

	// UserLogData 내부 클래스
	class UserLogData {
		public String username;
		private Path pathLogin;
		private Path pathLogout;

		public UserLogData(String username) {
			this.username = username;
			this.pathLogin = Paths.get("userData", username + "Login.dat");
			this.pathLogout = Paths.get("userData", username + "Logout.dat");
		}

		public Path getPathIn() {
			return this.pathLogin;
		}

		public Path getPathOut() {
			return this.pathLogout;
		}
	}

	// 생성자
	public UserRepositoryImpl() {
		try {
			Files.createDirectories(DATA_FILE.getParent());
		} catch (IOException e) {
			System.out.println("데이터 파일을 위한 폴더 생성 불가");
		}
		load();
	}

	// 초기 데이터 로딩
	private void load() {
		List<User> read = FileManager.readObject(DATA_FILE);
		if (read != null) {
			users = read;
			tmpUsers = deepCopy(read);
		} else {
			users = new ArrayList<>();
			tmpUsers = new ArrayList<>();
		}
	}

	// 깊은 복사 (얕은 복사로 충분)
	private List<User> deepCopy(List<User> source) {
		return new ArrayList<>(source);
	}

	// commit: 현재 상태를 파일에 저장하고 tmpUsers 갱신
	@Override
	public void commit() {
		FileManager.writeObject(DATA_FILE, users);
		tmpUsers = deepCopy(users);
	}

	// rollback: tmpUsers 상태로 되돌린 후 파일에 덮어쓰기
	@Override
	public void rollback() {
		users = deepCopy(tmpUsers);
		FileManager.writeObject(DATA_FILE, users);
	}

	@Override
	public User saveUser(User user) {
		users.add(user);
		return user;
	}

	@Override
	public User findUserByEmail(String email) {
		return users.stream()
				.filter(u -> u.getEmail() != null)
				.filter(u -> u.getEmail().trim().equalsIgnoreCase(email.trim()))
				.findFirst()
				.orElse(null);
	}

	@Override
	public User findUserByUserId(String userId) {
		return users.stream().filter(u -> u.getUserId().equals(userId)).findFirst().orElse(null);
	}

	@Override
	public User authorizeUser(User user) {
		for (User clone : users) {
			if (clone.equals(user)) {
				clone.giveManagerAuthentication();
				return clone;
			}
		}
		return null;
	}

	@Override
	public User replaceUser(User previousUser, User changedUser) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(previousUser)) {
				users.set(i, changedUser);
				return changedUser;
			}
		}
		users.add(changedUser);
		return changedUser;
	}

	@Override
	public void resetData() {
		users.clear();
	}

	@Override
	public List<User> getUsersList() {
		return users;
	}

	// 로그인/로그아웃 기록 저장
	@Override
	public LocalDateTime saveLoginTime(String email, LocalDateTime now) {
		User user = findUserByEmail(email);
		UserLogData logData = new UserLogData(user.getUsername());
		List<LocalDateTime> login = FileManager.readObject(logData.getPathIn());
		if (login == null) login = new ArrayList<>();
		login.add(now);
		FileManager.writeObject(logData.getPathIn(), login);
		return now;
	}

	@Override
	public LocalDateTime saveLogoutTime(String email, LocalDateTime now) {
		User user = findUserByEmail(email);
		UserLogData logData = new UserLogData(user.getUsername());
		List<LocalDateTime> logout = FileManager.readObject(logData.getPathOut());
		if (logout == null) logout = new ArrayList<>();
		logout.add(now);
		FileManager.writeObject(logData.getPathOut(), logout);
		return now;
	}

	// 로그인/로그아웃 기록 조회
	@Override
	public List<LocalDateTime> getLoginTime(String email) {
		User user = findUserByEmail(email);
		UserLogData logData = new UserLogData(user.getUsername());
		List<LocalDateTime> login = FileManager.readObject(logData.getPathIn());
		if (login == null) login = new ArrayList<>();
		return login;
	}

	@Override
	public List<LocalDateTime> getLogoutTime(String email) {
		User user = findUserByEmail(email);
		UserLogData logData = new UserLogData(user.getUsername());
		List<LocalDateTime> logout = FileManager.readObject(logData.getPathOut());
		if (logout == null) logout = new ArrayList<>();
		return logout;
	}

	@Override
	public List<LocalDateTime> findLoginByDay(String email, int year, int month, int day) {
		return getLoginTime(email).stream()
				.filter(dt -> dt.getYear() == year && dt.getMonthValue() == month && dt.getDayOfMonth() == day)
				.collect(Collectors.toList());
	}

	@Override
	public List<LocalDateTime> findLoginByMonth(String email, int year, int month) {
		return getLoginTime(email).stream()
				.filter(dt -> dt.getYear() == year && dt.getMonthValue() == month)
				.collect(Collectors.toList());
	}

	@Override
	public List<LocalDateTime> findLogoutByDay(String email, int year, int month, int day) {
		return getLogoutTime(email).stream()
				.filter(dt -> dt.getYear() == year && dt.getMonthValue() == month && dt.getDayOfMonth() == day)
				.collect(Collectors.toList());
	}

	@Override
	public List<LocalDateTime> findLogoutByMonth(String email, int year, int month) {
		return getLogoutTime(email).stream()
				.filter(dt -> dt.getYear() == year && dt.getMonthValue() == month)
				.collect(Collectors.toList());
	}
}
