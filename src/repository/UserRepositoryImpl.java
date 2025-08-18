package repository;

import domain.User;

import java.time.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.nio.file.Path;
import java.nio.file.Paths;


public class UserRepositoryImpl implements UserRepository{
	// src 폴더 밖에 있는 data 폴더안에 있는 users.dat에 path 지정 
	private final Path DATA_FILE = Paths.get("userData", "users.dat");
	//각 유저의 로그인/로그아웃 기록이 생성되야한다 그러기에 repository 에서 아래 방법으로 폴더와 파일을 생성하는건 hard coding & 옳바르지않다. 
	//class 를 만들고 field 를 login & logout 으로 틀을 만들자  
	class UserLogData{
		public String username;
		private Path pathLogin;
		private Path pathLogout;
		private List<LocalDateTime> listLogin;
		private List<LocalDateTime> listLogout;
		
		public UserLogData(String username) {
			this.username = username;	
			this.pathLogin = Paths.get("userLogData", username + "Login.dat");
			this.pathLogout = Paths.get("userLogData", username + "Logout.dat");
			//List<LocalDateTime> listLogin = new ArrayList<LocalDateTime>();
			//List<LocalDateTime> listLogout = new ArrayList<LocalDateTime>();
		}
		public Path getPathIn() {
			return this.pathLogin;
		}
		public Path getPathOut() {
			return this.pathLogout;
		}
		public List<LocalDateTime> getLoginList(){
			return this.listLogin;
		}
		public List<LocalDateTime> getLogoutList(){
			return this.listLogout;
		}
		/*
		public List<LocalDateTime> overrideLoginList(List list){
			this.listLogin.clear();
			this.listLogin.addAll(list);
			return this.listLogin;
		}
		public List<LocalDateTime> overrideLogoutList(List list){
			this.listLogout.clear();
			this.listLogout.addAll(list);
			return this.listLogout;
		}
		*/
	}
	
	
	// users.dat를 위한 폴더(/data)가 없다면 생성해준다.
	public UserRepositoryImpl(){
		try {
			Files.createDirectories(DATA_FILE.getParent());
		}catch (IOException e) {
			System.out.println("데이터 파일을 위한 폴더 생성 불가");
		}
	}
	List<User> users = new ArrayList<User>();
	List<LocalDateTime> login = new ArrayList<LocalDateTime>(); // just a dummyList to serialize and de-serialize 
	List<LocalDateTime> logout = new ArrayList<LocalDateTime>(); // just a dummyList to serialize and de-serialize
	
	@Override
	public User saveUser(User user) {
		users = FileManager.readObject(DATA_FILE);
		users.add(user);
		FileManager.writeObject(DATA_FILE, users);
		return user;
	}
	@Override
	public User findUserByEmail(String email){
		users = FileManager.readObject(DATA_FILE);
		return users.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
	}
	@Override
	public User findUserByUserId(String userId) {
		users = FileManager.readObject(DATA_FILE);
		return users.stream().filter(u -> u.getUserId().equals(userId)).findFirst().orElse(null);
	}
	@Override
	public User authorizeUser(User user) {
		users = FileManager.readObject(DATA_FILE);
		for(User clone : users) {
			if(clone.equals(user)) {
				clone.giveManagerAuthentication();
				FileManager.writeObject(DATA_FILE, users);
				return clone;
			}
		}
		return null;
	}
	@Override
	public User replaceUser(User previousUser, User changedUser) {
		users = FileManager.readObject(DATA_FILE);
		for (int i = 0; i < users.size(); i++) {
		    if (users.get(i).equals(previousUser)) {
		        users.set(i, changedUser);  // replaces the element in the list
		        FileManager.writeObject(DATA_FILE, users);
		        return changedUser;
		    }
		}
		return null;
	}
	@Override
	public void resetData(){
		List <User> forReset = new ArrayList<User>();
		FileManager.writeObject(DATA_FILE, forReset);
	}
	@Override
	public List<User> getUsersList() {
		users = FileManager.readObject(DATA_FILE);
		return users;
	}
	@Override
	public LocalDateTime saveLoginTime(User user1, LocalDateTime now) {
		UserLogData LogData = new UserLogData(user1.getUsername());
		
		login = FileManager.readObject(LogData.getPathIn());
		
		login.add(now);
		FileManager.writeObject(LogData.getPathIn(), login);
		return now;
	}
	@Override
	public LocalDateTime saveLogoutTime(User user1, LocalDateTime now) {
		UserLogData logData = new UserLogData(user1.getUsername());
		logout = FileManager.readObject(logData.getPathOut());
		logout.add(now);
		FileManager.writeObject(logData.getPathOut(), logout);
		return now;
	}
	@Override
	public List<LocalDateTime> getLoginTime(User user1) {
		UserLogData logData = new UserLogData(user1.getUsername());
		login = FileManager.readObject(logData.getPathIn());
		return this.login;
	}
	@Override
	public List<LocalDateTime> getLogoutTime(User user1) {
		UserLogData logData = new UserLogData(user1.getUsername());
		logout = FileManager.readObject(logData.getPathOut());
		return this.logout;
	}
	@Override
	public List<LocalDateTime> findLoginByDay(User user1,int year, int month, int day) {
		UserLogData logData = new UserLogData(user1.getUsername());
		List<LocalDateTime> section = new ArrayList<>();
		login = FileManager.readObject(logData.getPathIn());
		
		//section = login.stream().filter(u -> (u.getDayOfMonth() == day && u.getMonthValue() == month && u.getYear() == year)).toList();
		section = login.stream().filter(u -> (u.getDayOfMonth() == day && u.getMonthValue() == month && u.getYear() == year)).collect(Collectors.toList());
		return section;
	}
	@Override
	public List<LocalDateTime> findLoginByMonth(User user1,int year, int month) {
		UserLogData logData = new UserLogData(user1.getUsername());
		List<LocalDateTime> section = new ArrayList<>();
		login = FileManager.readObject(logData.getPathIn());
		
		//section = login.stream().filter(u -> (u.getDayOfMonth() == day && u.getMonthValue() == month && u.getYear() == year)).toList();
		section = login.stream().filter(u -> (u.getMonthValue() == month && u.getYear() == year)).collect(Collectors.toList());
		return section;
	}
	@Override
	public List<LocalDateTime> findLogoutByDay(User user1,int year, int month, int day) {
		UserLogData logData = new UserLogData(user1.getUsername());
		List<LocalDateTime> section = new ArrayList<>();
		logout = FileManager.readObject(logData.getPathOut());
		
		//section = login.stream().filter(u -> (u.getDayOfMonth() == day && u.getMonthValue() == month && u.getYear() == year)).toList();
		section = logout.stream().filter(u -> (u.getDayOfMonth() == day && u.getMonthValue() == month && u.getYear() == year)).collect(Collectors.toList());
		return section;
	}
	@Override
	public List<LocalDateTime> findLogoutByMonth(User user1,int year, int month) {
		UserLogData logData = new UserLogData(user1.getUsername());
		List<LocalDateTime> section = new ArrayList<>();
		logout = FileManager.readObject(logData.getPathOut());
		
		//section = login.stream().filter(u -> (u.getDayOfMonth() == day && u.getMonthValue() == month && u.getYear() == year)).toList();
		section = logout.stream().filter(u -> (u.getMonthValue() == month && u.getYear() == year)).collect(Collectors.toList());
		return section;
	}
}
