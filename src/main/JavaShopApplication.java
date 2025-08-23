package main;

import java.io.IOException;
import java.util.Scanner;

import controller.AdminPasswordValidationController;
import controller.UserValidationController;
import domain.User;
import helper.PasswordEncoder;
import helper.PasswordEncoderImpl;
import iolayer.MainIOLayer;
import iolayer.OrderIOLayer;
import iolayer.ProductIOLayer;
import iolayer.UserIOLayer;
import repository.AdminPasswordRepository;
import repository.AdminPasswordRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.AdminPasswordService;
import service.AdminPasswordServiceImpl;
import service.SessionService;
import service.SessionServiceImpl;
import service.UserService;
import service.UserServiceImpl;

public class JavaShopApplication{
	
	private AdminPasswordRepository adminPasswordRepository = new AdminPasswordRepositoryImpl();
	private AdminPasswordService adminPasswordService = new AdminPasswordServiceImpl(adminPasswordRepository);
	private AdminPasswordValidationController adminPasswordValidationController = new AdminPasswordValidationController(adminPasswordService);
	private UserRepository userRepository = new UserRepositoryImpl();
	private Scanner input = new Scanner(System.in);
    private ProductIOLayer productIOLayer = new ProductIOLayer(input);
    private PasswordEncoder encoder = new PasswordEncoderImpl(); 
    private SessionService sessionService = new SessionServiceImpl(userRepository, encoder);
    private UserValidationController userValidationController;
    private UserIOLayer userIOLayer = new UserIOLayer(userValidationController, adminPasswordValidationController);
    private OrderIOLayer orderIOLayer;
    
    public JavaShopApplication() {
		
	}
	
	public static void main(String[] args) {
		
	}
}
