package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import helper.*;

import domain.Cart;
import exception.ShopException;
import persistence.FileManager;

public class AdminPasswordRepositoryImpl implements AdminPasswordRepository {
	
	PasswordEncoder encoder = new PasswordEncoderImpl();
	private final Path DATA_FILE = Paths.get("adminData", "admin.dat");
	private ArrayList<String> password;

	
	public AdminPasswordRepositoryImpl() {
		try {
			Files.createDirectories(DATA_FILE.getParent());
		}catch(IOException e) {
			System.out.println("데이터 파일을 위한 폴더 생성 불가");
		}
		password = new ArrayList<String>();
		load();
	} 
	private void load(){
		List<String> read = FileManager.readObject(DATA_FILE);
		if(!(read.size() == 0) || !read.isEmpty()) {
			 password.addAll(read);
		}else{
			this.password.add("password1234"); // test 용 코드
			FileManager.writeObject(DATA_FILE, password);
		}
	}
	@Override
	public ArrayList<String> getPassword() {
		return this.password;
	}
	@Override
	public void setPassword(ArrayList<String> currentPassword, ArrayList<String> newPassword) throws IOException {
		this.password = newPassword;
		FileManager.writeObject(DATA_FILE, this.password);
	}
	@Override
	public void initializePassword() throws IOException {
		this.password.add("password123");
		FileManager.writeObject(DATA_FILE, this.password);
	}
}
