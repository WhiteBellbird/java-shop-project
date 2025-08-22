package repository;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public interface AdminPasswordRepository {

	// 현재 관리자 비밀번호 받고 현재 비밀번호값 return. 
	public ArrayList<String> getPassword();
	
	// 현재 관리자 비밀번호 받고 비밀변호 변환
	public void setPassword(ArrayList<String> currentPassword, ArrayList<String> newPassword) throws IOException;
	
	// 패스워드 초기화 - default 값으로 변환
	public void initializePassword() throws IOException;
}
