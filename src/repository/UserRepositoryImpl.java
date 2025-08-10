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
	private final Path DATA_FILE = Paths.get("data", "users.dat");
	// user 정보 List
	List <User> users = new ArrayList<User>();
	// users.dat를 위한 폴더(/data)가 없다면 생성해준다.
	public UserRepositoryImpl(){
		try {
			Files.createDirectories(DATA_FILE.getParent());
		} catch (IOException e) {
			System.out.println("데이터 파일을 위한 폴더 생성 불가");
		}
	}
	@Override
	public User saveUser(User user) {
		try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(DATA_FILE))){
			users.add(user);
			oos.writeObject(users);
			System.out.println("유저정보 데이터파일에 입력 완료");
		} catch (IOException e) {
			System.out.println("유저 정보 save 불가");
		}
		return user;
	}
	@Override
	public User findUserByEmail(String email) throws ClassNotFoundException {
		try(ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(DATA_FILE))){
			Object obj = ois.readObject();
			List <User> usersForDisplay = (List<User>) obj;
			for(User user : usersForDisplay) {
				if(user.getEmail().equalsIgnoreCase(email)) {
					return user;
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public User findUserByUserId(String userId) throws ClassNotFoundException {
		try(ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(DATA_FILE))){
			Object obj = ois.readObject();
			List<User> usersForDisplay = (List<User>) obj;
			return usersForDisplay.stream().filter(u -> u.getUserId().equals(userId)).findFirst().orElse(null);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public User updateUser(User user) {
		try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(DATA_FILE))){
			for(User userr : users) {
				if(userr.equals(user)) { // equals() Override 버젼
					userr.giveManagerAuthentication();
					oos.writeObject(users);
					return userr;
				}
			}	
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public User changeUser(User previousUser, User changedUser) {
		try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(DATA_FILE))){
			for (int i = 0; i < users.size(); i++) {
			    if (users.get(i).equals(previousUser)) {
			        users.set(i, changedUser);  // replaces the element in the list
			        oos.writeObject(users);
			        return changedUser;
			    }
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void display() throws ClassNotFoundException {
		try(ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(DATA_FILE))){
			Object obj = ois.readObject();
			List<User> usersForDisplay = (List<User>) obj;
			for(User user : usersForDisplay) {
				System.out.println(user);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
