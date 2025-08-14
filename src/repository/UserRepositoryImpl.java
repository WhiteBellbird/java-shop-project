package repository;

import domain.User;

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
import java.util.*;

public class UserRepositoryImpl implements UserRepository{
	// src 폴더 밖에 있는 data 폴더안에 있는 users.dat에 path 지정 
	private final Path DATA_FILE = Paths.get("userData", "users.dat");
	// users.dat를 위한 폴더(/data)가 없다면 생성해준다.
	public UserRepositoryImpl(){
		try {
			Files.createDirectories(DATA_FILE.getParent());
		} catch (IOException e) {
			System.out.println("데이터 파일을 위한 폴더 생성 불가");
		}
	}
	List<User> users = new ArrayList<User>();
	
	
	
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
	public void display(){
		users = FileManager.readObject(DATA_FILE);
		for(User clone : users) {
			System.out.println(clone);
		}
	}
	//needs a fix***********************************************
	@Override
	public void resetData(){
		List <User> forReset = new ArrayList<User>();
		FileManager.writeObject(DATA_FILE, forReset);
	}
}
