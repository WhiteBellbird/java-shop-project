package service;
import java.io.IOException;

import exception.ShopException;
import repository.*;

public interface AdminPasswordService {
	
	public String getPassword(String currentPassword);
	
	public void setPassword(String currentPassword, String newPassword) throws IOException;
	
	public void initializePassword(String currentPassword) throws IOException;
	
	//매니져가 되려면 궁극적인 (encode 된)비밀번호가 필요함 또는 만약에 중요한 함수를 부를떄 안정장치로 비밀번호로 매니져신분 다시 확인.
	public void checkManagerByPassword(String userInput);
}
