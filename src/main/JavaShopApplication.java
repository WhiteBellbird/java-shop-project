package main;

import java.io.IOException;
import java.util.Scanner;

import controller.UserController;
import domain.User;
import helper.PasswordEncoder;
import helper.PasswordEncoderImpl;
import iolayer.OrderIOLayer;
import iolayer.ProductIOLayer;
import iolayer.UserIOLayer;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.SessionService;
import service.SessionServiceImpl;
import service.UserService;

public class JavaShopApplication {

    //	private AdminPasswordRepository adminPasswordRepository = new AdminPasswordRepositoryImpl();
//	private AdminPasswordService adminPasswordService = new AdminPasswordServiceImpl(adminPasswordRepository);
//	private AdminPasswordValidationController adminPasswordValidationController = new AdminPasswordValidationController(adminPasswordService);
//	private UserRepository userRepository = new UserRepositoryImpl();
//	private Scanner input = new Scanner(System.in);
//    private ProductIOLayer productIOLayer = new ProductIOLayer(input);
//    private PasswordEncoder encoder = new PasswordEncoderImpl();
//    private SessionService sessionService = new SessionServiceImpl(userRepository, encoder);
//    private UserIOLayer userIOLayer = new UserIOLayer();
    private OrderIOLayer orderIOLayer;

    public static void main(String[] args) {

    }
}

