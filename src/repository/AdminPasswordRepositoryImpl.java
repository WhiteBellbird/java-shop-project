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
	private String password;

	
	AdminPasswordRepositoryImpl() throws IOException {
		try {
			Files.createDirectories(DATA_FILE.getParent());
		}catch(IOException e) {
			System.out.println("데이터 파일을 위한 폴더 생성 불가");
		}
		load();
	} 
	private void load() throws IOException {
		String read = Files.readString(DATA_FILE);
		if(!read.isBlank() || !read.isEmpty() ||/*read != null 뺴야하나?*/read != null) {
			password = encoder.decode(read);
		}else{
			this.password = "password1234"; // test 용 코드
			byte[] strBytes = encoder.encode(this.password).getBytes();
			Files.write(DATA_FILE, strBytes);
		}
	}
	@Override
	public String getPassword() {
		return this.password;
	}
	@Override
	public void setPassword(String currentPassword, String newPassword) throws IOException {
		this.password = newPassword;
		byte[] strByte = encoder.encode(this.password).getBytes();
		Files.write(DATA_FILE, strByte);
	}
	@Override
	public void initializePassword() throws IOException {
		this.password = "password1234";
		byte[] strByte = encoder.encode(this.password).getBytes();
		Files.write(DATA_FILE, strByte);
	}
}
