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
		saveLog();
		findLog();
		
		//클래스 안에서도 list 에 잘 알맞게 저장되도록 너무 dummy 로만 사용하지않기 - 다행히 리스트에 저장이 잘되서 출력 가능 
		
	}
	
	public static void createUsers() {
		repo.saveUser(new User("001", "Kwon Lee", "kwon9973@gmail.com", "Asdf1234!", "SILVER", null, 0, null, null, false, null, null, null));
		repo.saveUser(new User("002", "Susan Lee", "susan@gmail.com", "Asdf1234!", "SILVER", null, 0, null, null, false, null, null, null));
		repo.saveUser(new User("003", "Bruce Lee", "brutal@gmail.com", "Asdf1234!", "SILVER", null, 0, null, null, false, null, null, null));
		repo.saveUser(new User("004", "Robert Lee", "rogate@gmail.com", "Asdf1234!", "SILVER", null, 0, null, null, false, null, null, null));
	}
	public static void replaceUser() {
		System.out.println(repo.replaceUser(repo.findUserByEmail("susan@gmail.com"), repo.findUserByUserId("001")));
	}
	public static void updateUser() {
		repo.authorizeUser(repo.findUserByUserId("001"));
	}
	public static void saveLog() {
		repo.saveLoginTime("Kwon Lee", LocalDateTime.now());
		repo.saveLoginTime("Susan Lee", LocalDateTime.of(2025, 8, 3, 13, 45));
		repo.saveLoginTime("Robert Lee", LocalDateTime.of(2025, 8, 3, 13, 45));
		
		repo.saveLogoutTime("Kwon Lee", LocalDateTime.of(2025, 8, 3, 13, 45));
		repo.saveLogoutTime("Susan Lee", LocalDateTime.now());
	}
	public static void findLog() {
		//리포에 저장한건 바로 못쓰는게 당연한것 - 리포에서 바로 쓸수 있는 방법 구상하기 
		System.out.println(repo.findLoginByMonth("Kwon Lee", 2025, 8)); // repository 에 hashMap 르 <UserId/User , List<LocalDateTime> loginList> 으로 login list 를 저장해야하나 고민  
		System.out.println(repo.findLoginByMonth("Susan Lee", 2025, 8)); // repository 에서 써주고 그떄부터 비교
	}
}

