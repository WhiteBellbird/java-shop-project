package service;

import java.io.IOException;
import java.util.ArrayList;

import exception.ShopException;
import repository.AdminPasswordRepository;

public class AdminPasswordServiceImpl implements AdminPasswordService {
	AdminPasswordRepository apr;
	
	public AdminPasswordServiceImpl(AdminPasswordRepository adminRepo) {
		this.apr = adminRepo;
	}
	@Override
	public ArrayList<String> getPassword(ArrayList<String> currentPassword) {
		if(!currentPassword.equals(apr.getPassword())){
			throw new ShopException("관리자용 비밀번호가 일치하지않습니다.");
		}
		return apr.getPassword();
	}
	@Override
	public void setPassword(ArrayList<String> currentPassword, ArrayList<String> newPassword) throws IOException {
		if(!currentPassword.equals(apr.getPassword())) {
			throw new ShopException("관리자용 비밀번호가 일치하지않습니다.");
		}
		apr.setPassword(currentPassword, newPassword);
	}
	@Override
	public void initializePassword(ArrayList<String> currentPassword) throws IOException{
		if(!currentPassword.equals(apr.getPassword())) {
			throw new ShopException("관리자용 비밀번호가 일치하지않습니다.");
		}
		apr.initializePassword();
	}
	@Override
	public void checkManagerByPassword(ArrayList<String> currentPassword) {	
		if(!currentPassword.equals(apr.getPassword())) {
			throw new ShopException("관리자용 비밀번호가 일치하지않습니다.");
		}
	}
}
