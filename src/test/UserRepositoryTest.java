package test;

import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import domain.User;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.*;
import java.io.*;

public class UserRepositoryTest {
	static UserRepositoryImpl repo = new UserRepositoryImpl();
	
	public static void main(String[] args){	
		repo.resetData();
		createUsers();
		updateUser();
		replaceUser();		
		saveLog();
		findLog();
		
		//클래스 안에서도 list 에 잘 알맞게 저장되도록 너무 dummy 로만 사용하지않기 - 다행히 리스트에 저장이 잘되서 출력 가능 
		
	
	}
	
	public static void createUsers() {
		repo.saveUser(new User("001", "Kwon Lee", "kwon9973@gmail.com", "Asdf1234!", "SILVER", null, 0, null, null, false, null, null, null, false));
		repo.saveUser(new User("002", "Susan Lee", "su@gmail.com", "Asdf1234!", "SILVER", null, 0, null, null, false, null, null, null, false));
		repo.saveUser(new User("003", "Bruce Lee", "bu@gmail.com", "Asdf1234!", "SILVER", null, 0, null, null, false, null, null, null, false));
		repo.saveUser(new User("004", "Robert Lee", "ro@gmail.com", "Asdf1234!", "SILVER", null, 0, null, null, false, null, null, null, false));
	}
	public static void replaceUser() {
		repo.replaceUser(repo.findUserByEmail("su@gmail.com"), repo.findUserByUserId("001"));
	}
	public static void updateUser() {
		repo.authorizeUser(repo.findUserByUserId("001"));
	}
	public static void saveLog() {
		repo.saveUser(new User("001", "Kwon Lee", "kwon9973@gmail.com", "Asdf1234!", "SILVER", null, 0, null, null, false, null, null, null, false));
		repo.saveUser(new User("002", "Susan Lee", "su@gmail.com", "Asdf1234!", "SILVER", null, 0, null, null, false, null, null, null, false));
		repo.saveUser(new User("003", "Bruce Lee", "bu@gmail.com", "Asdf1234!", "SILVER", null, 0, null, null, false, null, null, null, false));
		repo.saveUser(new User("004", "Robert Lee", "ro@gmail.com", "Asdf1234!", "SILVER", null, 0, null, null, false, null, null, null, false));
		
		repo.saveLoginTime(repo.findUserByUserId("001"), LocalDateTime.now());
		repo.saveLoginTime(repo.findUserByUserId("001"), LocalDateTime.of(2025, 7, 3, 13, 45));
		repo.saveLoginTime(repo.findUserByUserId("001"), LocalDateTime.of(2025, 8, 3, 13, 45));
		
		repo.saveLoginTime(repo.findUserByUserId("002"), LocalDateTime.of(2025, 8, 3, 13, 45));
		repo.saveLoginTime(repo.findUserByUserId("002"), LocalDateTime.now());
	}
	public static void findLog() {
		repo.saveUser(new User("001", "Kwon Lee", "kwon9973@gmail.com", "Asdf1234!", "SILVER", null, 0, null, null, false, null, null, null, false));
		repo.saveUser(new User("002", "Susan Lee", "su@gmail.com", "Asdf1234!", "SILVER", null, 0, null, null, false, null, null, null, false));
		repo.saveUser(new User("003", "Bruce Lee", "bu@gmail.com", "Asdf1234!", "SILVER", null, 0, null, null, false, null, null, null, false));
		repo.saveUser(new User("004", "Robert Lee", "ro@gmail.com", "Asdf1234!", "SILVER", null, 0, null, null, false, null, null, null, false));
		System.out.println(repo.findLoginByMonth(repo.findUserByUserId("001"), 2025, 8));
		System.out.println(repo.findLoginByMonth(repo.findUserByUserId("002"), 2025, 8)); 
	}
}

