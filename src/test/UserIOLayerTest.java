package test;

import java.io.IOException;
import java.util.Scanner;

import controller.UserController;
import domain.User;
import helper.PasswordEncoder;
import helper.PasswordEncoderImpl;
import iolayer.MainLayer;
import iolayer.OrderIOLayer;
import iolayer.ProductIOLayer;
import iolayer.UserIOLayer;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.SessionService;
import service.SessionServiceImpl;
import service.UserService;
import service.UserServiceImpl;

public class UserIOLayerTest {
		static Scanner input = new Scanner(System.in);
		static UserRepository userRepository = new UserRepositoryImpl();
		static PasswordEncoder passwordEncoder = new PasswordEncoderImpl();
		static UserService userService = new UserServiceImpl(userRepository,passwordEncoder);
		static SessionService sessionService = new SessionServiceImpl(userRepository, passwordEncoder);
		static UserIOLayer userIOLayer = new UserIOLayer(input,new UserController(userService),sessionService,null);
		static MainLayer mainLayer = new MainLayer(new Scanner(System.in), null, null,
				sessionService, null);
	public static void main(String[] args) throws IOException {

<<<<<<< HEAD
		uil.createUser();
		uil.updateManager(user);
		uil.findUser(user.getUsername(), user.getPassword());
		
		
		uil.changePassword(user);
		uil.updateUser(user);
		
		//탈퇴 기능 문제 -- 확인 바람***********************************************************
		uil.withdrawl(user);
		
		
		
=======
		createUser();
		login();
	}

	private static void createUser() {
		User user = userService.createUser("username", "hi@gmail.com", "password",
				"address", "101-1111-1111");
	}

	private static void login() {
		mainLayer.main();
>>>>>>> origin/lsek/dev
	}
	
}
