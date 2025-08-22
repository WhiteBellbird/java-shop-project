package main;

import java.io.IOException;
import java.util.Scanner;

import controller.AdminPasswordValidationController;
import controller.UserValidationController;
import domain.User;
import helper.PasswordEncoder;
import helper.PasswordEncoderImpl;
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
	static Scanner input = new Scanner(System.in);
	
	static UserRepository ur;
	static UserService us;
	static AdminPasswordRepository ar;
	static AdminPasswordService as;
	static UserValidationController uc;
	static AdminPasswordValidationController ac;
	static PasswordEncoder pe;
	
	static UserIOLayer uil;
	static SessionService ss;
	
	public static void main(String[] args) throws IOException {
		// 여기서 모든 함수의 인스턴스 새 객체를 생성한 뒤,
		uil = new UserIOLayer(uc, ac);
		ss = new SessionServiceImpl(ur, pe);
		
		// run() 호출
		run();
	}
	
	private static void run() {
		do {
			// MainIOController 호출하면 됌
			System.out.println(
					"╔════════════════════════════════════════════╗\r\n"
					+ "║     🛍️  Java Shopping Mall                 ║\r\n"
					+ "╚════════════════════════════════════════════╝\r\n"
					+ "\r\n"
					+ "1. 회원가입\r\n"
					+ "2. 로그인\r\n"
					+ "3. 상품 둘러보기\r\n"
					+ "4. 프로그램 종료\r\n"
					+ "\r\n"
					+ "메뉴를 선택하세요: _");
			int choice = input.nextInt();
			
			switch(choice) {
				case 1:
					createUser();
					break;
				case 2:
					login();
					User user = ss.getLoggedInUser();
					if(!user.isAdmin()) {
						mainMenu();	
					}else{
						mainMenuAdmin();
					}
					break;
				case 3:
					viewProducts();
					break;
				case 4:
					System.exit(0);
			}
		}while(true);
	}
	
	private static void mainMenuAdmin() {
		System.out.println(
				"╔════════════════════════════════════════════╗\r\n"
				+ "║     🛍️  Java Shopping Mall                 ║\r\n"
				+ "║     [관리자 모드] 환영합니다!                  ║\r\n"
				+ "╚════════════════════════════════════════════╝\r\n"
				+ "\r\n"
				+ "1. 상품 둘러보기\r\n"
				+ "2. 상품 검색\r\n"
				+ "3. 장바구니 관리\r\n"
				+ "4. 주문하기\r\n"
				+ "5. 주문 내역\r\n"
				+ "6. 마이페이지\r\n"
				+ "7. 로그아웃\r\n"
				+ "8. [관리] 상품 관리\r\n"
				+ "9. [관리] 사용자 관리\r\n"
				+ "\r\n"
				+ "메뉴를 선택하세요: _");
	}

	private static void mainMenu() {
		System.out.println(
				"╔════════════════════════════════════════════╗\r\n"
				+ "║     🛍️  Java Shopping Mall                 ║\r\n"
				+ "║     환영합니다, [사용자명]님!                  ║\r\n"
				+ "╚════════════════════════════════════════════╝\r\n"
				+ "\r\n"
				+ "1. 상품 둘러보기\r\n"
				+ "2. 상품 검색\r\n"
				+ "3. 장바구니 관리\r\n"
				+ "4. 주문하기\r\n"
				+ "5. 주문 내역\r\n"
				+ "6. 마이페이지\r\n"
				+ "7. 로그아웃\r\n"
				+ "\r\n"
				+ "메뉴를 선택하세요: _");
		int choice = input.nextInt();
		
		switch(choice) {
		case 1:
			viewProductlogin();
			break;
		case 2:
			serachProduct();
			break;
		case 3:
			manageCart();
			break;
		case 4:
			order();
			break;
		case 5:
			orderHistory();
			break;
		case 6:
			myProfile();
			break;
		case 7:
			logout();
			break;
		}
	}

	private static void logout() {
		// TODO Auto-generated method stub
		
	}

	private static void myProfile() {
		// TODO Auto-generated method stub
		
	}

	private static void orderHistory() {
		// TODO Auto-generated method stub
		
	}

	private static void order() {
		// TODO Auto-generated method stub
		
	}

	private static void manageCart() {
		// TODO Auto-generated method stub
		
	}

	private static void serachProduct() {
		// TODO Auto-generated method stub
		
	}

	private static void viewProductlogin() {
		// TODO Auto-generated method stub
		
	}

	private static void createUser() {
		uil.createUser();
	}
	private static void login() {
		System.out.println("USERNAME: ");
		String username = input.nextLine();
		System.out.println("PASSWORD: ");
		String password = input.nextLine();
		ss.login(username, password);
	}
	private static void viewProducts() {
		
	}

	

	
	

}
