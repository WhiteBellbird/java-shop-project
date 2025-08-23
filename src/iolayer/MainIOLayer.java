package iolayer;

import java.io.IOException;
import java.util.Scanner;

import controller.AdminPasswordValidationController;
import controller.UserValidationController;
import domain.User;
import helper.PasswordEncoder;
import helper.PasswordEncoderImpl;
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

public class MainIOLayer {
	
	static AdminPasswordRepository ar = new AdminPasswordRepositoryImpl();
	static AdminPasswordService as = new AdminPasswordServiceImpl(ar);
	static AdminPasswordValidationController ac = new AdminPasswordValidationController(as);
	static PasswordEncoder pe = new PasswordEncoderImpl();
	
	private Scanner input;
    private ProductIOLayer productIOLayer;
    private SessionService sessionService;
    private UserIOLayer userIOLayer;
    private UserValidationController userValidationController;
    private OrderIOLayer orderIOLayer;

    public MainIOLayer(Scanner scanner, OrderIOLayer orderIOLayer,
                     ProductIOLayer productIOLayer,
                     SessionService sessionService, UserIOLayer userIOLayer, UserValidationController controller) {
        this.input = scanner;
        this.productIOLayer = productIOLayer;
        this.sessionService = sessionService;
        this.userIOLayer = userIOLayer;
        this.orderIOLayer = orderIOLayer;
        this.userValidationController = controller;
    }
	
	private void main() {
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
					User user = sessionService.getLoggedInUser();
					System.out.println(user);
					mainMenu(user);	
					break;
				case 3:
					viewProducts();
					break;
				case 4:
					System.exit(0);
			}
		}while(true);
	}

	private void mainMenu(User checkUser) {
		System.out.printf(
				"╔════════════════════════════════════════════╗\r\n"
				+ "║     🛍️  Java Shopping Mall                 ║\r\n"
				+ "║     환영합니다, [%s]님!                  ║\r\n"
				+ "╚════════════════════════════════════════════╝\r\n"
				+ "\r\n"
				+ "1. 상품 둘러보기\r\n"
				+ "2. 상품 검색\r\n"
				+ "3. 장바구니 관리\r\n"
				+ "4. 주문하기\r\n"
				+ "5. 주문 내역\r\n"
				+ "6. 마이페이지\r\n"
				+ "7. 로그아웃\r\n"
				, checkUser.isAdmin() ? "관리자" : checkUser.getUsername());
		// 관리자일 경우에 이쪽 경롤로 간다
		if(checkUser.isAdmin()) {
			System.out.println(
					"8. [관리] 상품 관리\r\n"
					+ "9. [관리] 사용자 관리\r\n"
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
			case 8:
				manageProducts();
				break;
			case 9:
				manageUsers();
				break;
			}
			// 고객일 경우에 이쪽 경로로 간다
		}else {
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
	}

	private void manageUsers() {
		// TODO Auto-generated method stub
		System.out.println(
				"┌────────────────────────────────────┐\r\n"
				+ "│      👥 [관리자] 사용자 관리       │\r\n"
				+ "├────────────────────────────────────┤\r\n"
				+ "│  1. 전체 회원 조회                 │\r\n"
				+ "│  2. 회원 검색                      │\r\n"
				+ "│  3. 회원 상세 정보                 │\r\n"
				+ "│  4. 회원 강제 탈퇴                 │\r\n"
				+ "│  5. 돌아가기                       │\r\n"
				+ "└────────────────────────────────────┘\r\n"
				+ "");
		int choice = input.nextInt();
		switch(choice) {
		case 1:
			try {
				userIOLayer.displayAllUser(sessionService.getLoggedInUser());
			}catch (IOException e) {
				System.out.println("로그인한 유저를 찾을수 없거나 존재하지않습니다 다시한번 확인해주세요");
			}
			break;
		case 2:
			try {
				userIOLayer.displayUser(sessionService.getLoggedInUser());
			} catch (IOException e) {
				System.out.println("로그인한 유저를 찾을수 없거나 존재하지않습니다 다시한번 확인해주세요");
			}
			break;
		case 3:
			try {
				userIOLayer.displayUser(sessionService.getLoggedInUser());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 4:
			try {
				userIOLayer.withdrawUser();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 5:
			break;
		}
	}

	private void manageProducts() {
		System.out.println(
				"┌────────────────────────────────────┐\r\n"
				+ "│      📦 [관리자] 상품 관리         │\r\n"
				+ "├────────────────────────────────────┤\r\n"
				+ "│  1. 상품 등록                      │\r\n"
				+ "│  2. 상품 수정                      │\r\n"
				+ "│  3. 상품 삭제                      │\r\n"
				+ "│  4. 재고 관리                      │\r\n"
				+ "│  5. 상품 목록 조회                 │\r\n"
				+ "│  6. 돌아가기                       │\r\n"
				+ "└────────────────────────────────────┘");
		int choice = input.nextInt();
		switch(choice) {
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		}
	}

	private static void logout() {
		// TODO Auto-generated method stub
		
	}

	private void myProfile() {
		System.out.println(
				"┌────────────────────────────────────┐\r\n"
				+ "│         👤 마이페이지               │\r\n"
				+ "├────────────────────────────────────┤\r\n"
				+ "│  1. 내 정보 조회                   │\r\n"
				+ "│  2. 비밀번호 변경                  │\r\n"
				+ "│  3. 개인정보 수정                  │\r\n"
				+ "│  4. 주문 내역 조회                 │\r\n"
				+ "│  5. 회원 탈퇴                      │\r\n"
				+ "│  6. 돌아가기                       │\r\n"
				+ "└────────────────────────────────────┘");
		int choice = input.nextInt();
		switch(choice) {
		case 1:
			userIOLayer.findUser(sessionService.getLoggedInUser().getUsername(), sessionService.getLoggedInUser().getPassword());
			break;
		case 2:
			userIOLayer.changePassword(sessionService.getLoggedInUser());
			break;
		case 3:
			userIOLayer.updateUser(sessionService.getLoggedInUser());
			break;
		case 4:
			// 주문 내역 조회 
			break;
		case 5:
			userIOLayer.withdrawl(sessionService.getLoggedInUser());
			break;
		case 6:
			break;
		}
	}

	private static void orderHistory() {
		// TODO Auto-generated method stub
		
	}

	private static void order() {
		// TODO Auto-generated method stub
		
	}

	private static void manageCart() {
		System.out.println(
				"┌────────────────────────────────────┐\r\n"
				+ "│         🛒 장바구니 관리            │\r\n"
				+ "├────────────────────────────────────┤\r\n"
				+ "│  1. 장바구니 조회                   │\r\n"
				+ "│  2. 상품 추가                      │\r\n"
				+ "│  3. 수량 변경                      │\r\n"
				+ "│  4. 상품 삭제                      │\r\n"
				+ "│  5. 장바구니 비우기                 │\r\n"
				+ "│  6. 돌아가기                       │\r\n"
				+ "└────────────────────────────────────┘\r\n"
				+ "");
	}

	private static void serachProduct() {
		// TODO Auto-generated method stub
		
	}

	private static void viewProductlogin() {
		System.out.println(
				"┌────────────────────────────────────┐\r\n"
				+ "│         🛍️ 상품 둘러보기            │\r\n"
				+ "├────────────────────────────────────┤\r\n"
				+ "│  1. 전체 상품 보기                   │\r\n"
				+ "│  2. 카테고리별 보기                  │\r\n"
				+ "│  3. 가격대별 보기                    │\r\n"
				+ "│  4. 베스트셀러                      │\r\n"
				+ "│  5. 신상품                          │\r\n"
				+ "│  6. 상품 상세보기                    │\r\n"
				+ "│  7. 돌아가기                        │\r\n"
				+ "└────────────────────────────────────┘\r\n"
				+ "");
	}

	private void createUser() {
		userIOLayer.createUser();
	}
	private void login() {
		String username;
		String password;
		System.out.println("USERNAME: ");
		username = input.next();
		System.out.println("PASSWORD: ");
		password = input.next();
		
		
		if(userValidationController.displayUser(username).getPassword().equals(password)) {
			userValidationController.findUser(username, userValidationController.displayUser(username).getPassword());
			sessionService.login(username, password);
		}
		
	}
	private static void viewProducts() {
		
	}

	

	
	

}
