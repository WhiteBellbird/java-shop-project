package test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import domain.User;
import repository.UserRepository;
import repository.UserRepositoryImpl;

import java.io.*;

public class UserRepositoryTest {
	static UserRepositoryImpl repo = new UserRepositoryImpl();
	public static void main(String[] args){
		
		
		
		
		repo.resetData();
		createUsers();
		updateUser();
		replaceUser();		
		displayData();
		
		
	
	}
	
	public static void createUsers() {
		repo.saveUser(new User("001", "KwonLee", "kwon@gmail.com","1234",  null, null));
		repo.saveUser(new User("002", "SusanLee", "su@gmail.com", "1234", null, null));
		repo.saveUser(new User("003", "BruceLee", "Bruce@gmail.com","1234",  null, null));
		repo.saveUser(new User("004", "RobertLee", "Robert@gmail.com", "1234",  null, null));
	}
	public static void displayData() {
		System.out.println("업데이트한 데이타 출력");
		repo.display();
	}
	public static void replaceUser() {
		repo.replaceUser(repo.findUserByEmail("su@gmail.com"), repo.findUserByUserId("001"));
	}
	public static void updateUser() {
		repo.updateUser(repo.findUserByUserId("001"));
	}
}

