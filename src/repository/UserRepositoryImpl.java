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

public class UserRepositoryImpl implements UserRepository{
	// src 폴더 밖에 있는 data 폴더안에 있는 users.dat에 path 지정 
	private final Path DATA_FILE = Paths.get("userData", "users.dat");
	private final Path LOGIN_DATA_FILE = Paths.get("userData", "loginTime.dat");
	private final Path LOGOUT_DATA_FILE = Paths.get("userData", "logoutTime.dat");
	
	// users.dat를 위한 폴더(/data)가 없다면 생성해준다.
	public UserRepositoryImpl(){
		try {
			Files.createDirectories(DATA_FILE.getParent());
		} catch (IOException e) {
			System.out.println("데이터 파일을 위한 폴더 생성 불가");
		}
	}
	List<User> users = new ArrayList<User>();
	List<LocalDateTime> login = new ArrayList<LocalDateTime>();
	List<LocalDateTime> logout = new ArrayList<LocalDateTime>();
	
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
	public LocalDateTime saveLoginTime(LocalDateTime now) {
		login = FileManager.readObject(LOGIN_DATA_FILE);
		login.add(LocalDateTime.now());
		FileManager.writeObject(LOGIN_DATA_FILE, login);
		return now;
	}
	@Override
	public LocalDateTime saveLogoutTime(LocalDateTime now) {
		logout = FileManager.readObject(LOGOUT_DATA_FILE);
		logout.add(LocalDateTime.now());
		FileManager.writeObject(LOGIN_DATA_FILE, logout);
		return now;
	}
	@Override
	public List<LocalDateTime> getLoginTime() {
		login = FileManager.readObject(LOGIN_DATA_FILE);
		return this.login;
	}
	@Override
	public List<LocalDateTime> getLogoutTime() {
		logout = FileManager.readObject(LOGOUT_DATA_FILE);
		return this.logout;
	}
	@Override
	public List<LocalDateTime> findLoginByDay(int year, int month, int day) {
		List<LocalDateTime> section = new ArrayList<>();
		login = FileManager.readObject(LOGIN_DATA_FILE);
		
		//section = login.stream().filter(u -> (u.getDayOfMonth() == day && u.getMonthValue() == month && u.getYear() == year)).toList();
		section = login.stream().filter(u -> (u.getDayOfMonth() == day && u.getMonthValue() == month && u.getYear() == year)).collect(Collectors.toList());
		return section;
	}
	@Override
	public List<LocalDateTime> findLoginByMonth(int year, int month) {
		List<LocalDateTime> section = new ArrayList<>();
		login = FileManager.readObject(LOGIN_DATA_FILE);
		
		//section = login.stream().filter(u -> (u.getDayOfMonth() == day && u.getMonthValue() == month && u.getYear() == year)).toList();
		section = login.stream().filter(u -> (u.getMonthValue() == month && u.getYear() == year)).collect(Collectors.toList());
		return section;
	}
	@Override
	public List<LocalDateTime> findLogoutByDay(int year, int month, int day) {
		List<LocalDateTime> section = new ArrayList<>();
		logout = FileManager.readObject(LOGOUT_DATA_FILE);
		
		//section = login.stream().filter(u -> (u.getDayOfMonth() == day && u.getMonthValue() == month && u.getYear() == year)).toList();
		section = logout.stream().filter(u -> (u.getDayOfMonth() == day && u.getMonthValue() == month && u.getYear() == year)).collect(Collectors.toList());
		return section;
	}
	@Override
	public List<LocalDateTime> findLogoutByMonth(int year, int month) {
		List<LocalDateTime> section = new ArrayList<>();
		logout = FileManager.readObject(LOGOUT_DATA_FILE);
		
		//section = login.stream().filter(u -> (u.getDayOfMonth() == day && u.getMonthValue() == month && u.getYear() == year)).toList();
		section = logout.stream().filter(u -> (u.getMonthValue() == month && u.getYear() == year)).collect(Collectors.toList());
		return section;
	}
}
