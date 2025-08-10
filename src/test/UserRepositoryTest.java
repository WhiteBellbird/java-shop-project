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
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		UserRepositoryImpl repo = new UserRepositoryImpl();
		
		User saved1 = repo.saveUser(new User("001", "KwonLee", "1234", "kwon@gmail.com", null, null));
		User saved2 = repo.saveUser(new User("002", "SusanLee", "1234", "su@gmail.com", null, null));
		User saved3 = repo.saveUser(new User("003", "BruceLee", "1234", "Bruce@gmail.com", null, null));
		User saved4 = repo.saveUser(new User("004", "RobertLee", "1234", "Robert@gmail.com", null, null));
		
		System.out.println();
		System.out.println("확인 메세지 출력: ");
		System.out.println(repo.findUserByEmail("su@gmail.com"));
		System.out.println(repo.findUserByUserId("001")); 
		System.out.println(repo.changeUser(saved2 , saved3));
		System.out.println(repo.updateUser(saved1));
		
		System.out.println();
		System.out.println("업데이트한 데이타 출력");
		repo.display();
		
		
	}
}
