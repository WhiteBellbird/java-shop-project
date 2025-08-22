package test;

import java.io.IOException;

import controller.AdminPasswordValidationController;
import controller.UserValidationController;
import domain.User;
import iolayer.UserIOLayer;
import repository.AdminPasswordRepository;
import repository.AdminPasswordRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.AdminPasswordService;
import service.AdminPasswordServiceImpl;
import service.UserService;
import service.UserServiceImpl;

public class UserIOLayerTest {
	public static void main(String[] args) throws IOException {
		UserRepository ur = new UserRepositoryImpl();
		UserService us = new UserServiceImpl(ur);
		AdminPasswordRepository ar = new AdminPasswordRepositoryImpl();
		AdminPasswordService as = new AdminPasswordServiceImpl(ar);
		UserValidationController uc = new UserValidationController(us);
		AdminPasswordValidationController ac = new AdminPasswordValidationController(as);
		UserIOLayer uil = new UserIOLayer(uc, ac);
		
		User user = us.displayUser("SUSANLEE22");
		
		//System.out.println(us.displayAllUsers(user.getUsername(), user.getPassword()));

		uil.createUser();
		uil.updateManager(user);
		uil.findUser(user);
		
		
		uil.changePassword(user);
		uil.updateUser(user);
		
		//탈퇴 기능 문제 -- 확인 바람***********************************************************
		uil.withdrawl(user);
		
		
		
	}
	
	
}
