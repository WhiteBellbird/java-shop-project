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
}

