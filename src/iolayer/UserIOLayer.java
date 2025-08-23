package iolayer;

import java.util.Scanner;

import controller.UserController;
import domain.User;
import exception.ShopException;
import helper.IOHelper;
import service.SessionService;

public class UserIOLayer {
	private Scanner scanner;
	private MainLayer mainLayer;
	private UserController userController;
    private SessionService sessionService;
	
	public UserIOLayer(Scanner scanner, MainLayer mainLayer,
                       UserController userController,
					   SessionService sessionService) {
        this.scanner = scanner;
        this.mainLayer = mainLayer;
        this.userController = userController;
        this.sessionService = sessionService;
    }

	public void myPage() {
		while (true) {
			System.out.println("┌────────────────────────────────────┐");
			System.out.println("│         👤 마이페이지               │");
			System.out.println("├────────────────────────────────────┤");
			System.out.println("│  1. 내 정보 조회                   │");
			System.out.println("│  2. 비밀번호 변경                  │");
			System.out.println("│  3. 개인정보 수정                  │");
			System.out.println("│  4. 주문 내역 조회                 │");
			System.out.println("│  5. 회원 탈퇴                      │");
			System.out.println("│  6. 돌아가기                       │");
			System.out.println("└────────────────────────────────────┘");


		}
	}

    public void createUser() {
		IOHelper.printFirstLine();
    	System.out.println("이메일을 입력하세요: ");
    	String email = scanner.nextLine();
    	System.out.println("현재 거주하고 계신 주소를 입력해주세요: ");
    	String address = scanner.nextLine();
    	System.out.println("전화번호를 입력해주세요: ");
    	String phone = scanner.nextLine(); // xxx-xxxx-xxxx
    	System.out.println("유저이름을 입력하세요\t\t\t|");
		String name = scanner.nextLine();
		String password;
		String checkPassword;
		while (true) {
			System.out.println("비밀번호를 입력하세요\t\t\t|");
			password = scanner.nextLine();
			System.out.println("비밀번호 다시를 입력하세요\t\t\t|");
			checkPassword = scanner.nextLine();
			if (!checkPassword.equals(password)) {
				System.out.println("패스워드가 일치하지 않습니다. 다시 입력해주세요.");
			} else {
				break;
			}
		}
		try {
			userController.createUser(name, email, password, address, phone);
			System.out.println("성공적으로 회원가입이 되었습니다. 로그인 해주세요.");
		} catch (ShopException e) {
			System.out.println("오류가 발생했습니다: " + e.getMessage());
		} finally {
			IOHelper.printEndLine();
		}
    }

    private void updateManager() {
		IOHelper.printFirstLine();
    	System.out.println("user를 manager로 권한부여합니다");
		try {
			User user = userController.updateManager(sessionService.getLoggedInUser());
			System.out.println("성공적으로 매니저로 승격되었습니다.");
			sessionService.updateSessionUser(user);
		} catch (ShopException e) {
			System.out.println("오류가 발생했습니다: " + e.getMessage());
		} finally {
			IOHelper.printEndLine();
		}
    }
    private void withdrawUser()  {
		IOHelper.printFirstLine();
    	System.out.print("탈퇴시킬 고객네임: ");
    	String username = scanner.nextLine();
		try {
			userController.withdrawUserByAdmin(sessionService.getLoggedInUser(), username);
			System.out.println("성공적으로 퇴출완료");
		} catch (Exception e) {
			System.out.println("오류가 발생했습니다: " + e.getMessage());
		} finally {
			IOHelper.printEndLine();
		}
    }

    private void displayLoggedUser() {
		IOHelper.printFirstLine();
		System.out.println("로그인된 현재 유저 조회를 시작합니다.");
		System.out.println(sessionService.getLoggedInUser());
		IOHelper.printEndLine();
    }

    public void displayAllUser() {
		IOHelper.printFirstLine();
		System.out.println("모든 유저를 조회합니다.");
    	userController.findAllUsers(sessionService.getLoggedInUser());
		IOHelper.printEndLine();
    }

    private void changePassword() {
		IOHelper.printFirstLine();
    	System.out.println("패스워드를 변경합니다.");
    	System.out.println("현재 패스워드를 입력하시오");
    	String currentPwd = scanner.nextLine();
    	System.out.println("변경할 비밀번호를 입력하시오");
    	String newPwd = scanner.nextLine();
		try {
			User updatedUser = userController.changePassword(sessionService.getLoggedInUser(), currentPwd, newPwd);
			System.out.println("유저 정보 : " + updatedUser);
			System.out.println("변경되었습니다. 다시 로그인해주시기 바랍니다.");
			sessionService.logout();
		} catch (ShopException e) {
			System.out.println("오류가 발생했습니다: " + e.getMessage());
		} finally {
			IOHelper.printEndLine();
		}
    }
    //상세정보 변경
    public void updateUser() {
		IOHelper.printFirstLine();
    	System.out.println("유저를 상세정보를 변경합니다.");
    	System.out.println("변경할 이메일을 입력하세요: ");
    	String email = scanner.nextLine();
    	System.out.println("변경할 현재 거주하고 계신 주소를 입력해주세요: ");
    	String address = scanner.nextLine();
    	System.out.println("변경할 전화번호를 입력해주세요: ");
    	String phone = scanner.nextLine(); // xxx-xxxx-xxxx
		try {
			User updatedUser = userController.updateUser(sessionService.getLoggedInUser(), email, address, phone);
			sessionService.updateSessionUser(updatedUser);
			System.out.println("업데이트 된 유저 정보 : " + updatedUser);
			System.out.println("변경되었습니다.");
			sessionService.updateSessionUser(updatedUser);
		} catch (ShopException e) {
			System.out.println("오류가 발생했습니다: " + e.getMessage());
		} finally {
			IOHelper.printEndLine();
		}
    }
    public void withdrawal() {
		IOHelper.printFirstLine();
    	System.out.println("회원탈퇴 시작합니다");
		try {
			userController.withdrawal(sessionService.getLoggedInUser());
			sessionService.logout();
			System.out.println("성공적으로 회원탈퇴 완료");
		} catch (ShopException e){
			System.out.println("오류가 발생했습니다: " + e.getMessage());
		} finally {
			IOHelper.printEndLine();
		}
    }
    
    class ManagerLayer {


	}
}
