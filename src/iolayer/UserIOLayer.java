package iolayer;

import java.io.IOException;
import java.util.Scanner;

import controller.AdminPasswordValidationController;
import controller.UserValidationController;
import domain.User;
import exception.ShopException;
import service.SessionService;

import java.util.*;

public class UserIOLayer {
	private Scanner scanner;
	private MainLayer mainLayer;
	private UserValidationController userValidationController;
	private AdminPasswordValidationController adminCtrl;
	private SessionService sessionService;
	
	public UserIOLayer(Scanner scanner, MainLayer mainLayer,
                       UserValidationController userValidationController,
                       AdminPasswordValidationController adminCtrl,
                       SessionService sessionService) {
        this.scanner = scanner;
        this.mainLayer = mainLayer;
        this.userValidationController = userValidationController;
		this.adminCtrl = adminCtrl;
        this.sessionService = sessionService;
    }

    public void createUser() {
    	System.out.println("이메일을 입력하세요: ");
    	String email = scanner.nextLine();
    	System.out.println("현재 거주하고 계신 주소를 입력해주세요: ");
    	String address = scanner.nextLine();
    	System.out.println("전화번호를 입력해주세요: ");
    	String phone = scanner.nextLine(); // xxx-xxxx-xxxx
    	
    	System.out.println("-------------------------------");
    	System.out.println("유저이름을 입력하세요\t\t\t|");
    	String name = scanner.nextLine();
    	System.out.println("비밀번호를 입력하세요\t\t\t|");
    	String password = scanner.nextLine();
//    	System.out.println("비밀번호를 다시 입력하세요\t\t|");
//    	String password2 = scanner.nextLine();
////    	ctrl.checkPassword(password, password2);
//    	System.out.println("-------------------------------");
//    	System.out.println("개인 정보에 대한 동의서를 확인하시겠습니까? [30 페이지 양의 동의서 출력 예정] (Y/N): ");
//    	String agree = scanner.nextLine();
//    	if(userValidationController.validateChoice(agree)){
//    		System.out.println("수집하는 개인정보 항목 :");
//    		System.out.println("개인식별정보 : 성명, 주소, 전화번호, 이메일, 기타 위촉을 위해 본인이 작성한 관련 정보 등");
//    		System.out.println("개인정보의 수집 및 이용목적");
//    		System.out.println("공하신 정보는 위촉절차의 집행 및 관리, 경력‧자격 등 확인(조회 및 검증), 위촉 여부의 결정, 민원처리에 사용 됩니다.\n"
//    				+ "① 본인 확인 및 범죄경력 조회에 이용: 성명, 생년월일\n"
//    				+ "② 지원자와의 의사소통 및 정보 전달 등에 이용: 성명, 주소, 전화번호, 휴대전화번호, 이메일\n");
//    		System.out.println("수집된 개인정보는 지원서 제출 후 위촉기간 만료 시 또는 지원서 삭제 요청 시까지 위 이용 목적을 위하여 보유‧이용됩니다. 또한 삭제 요청 시 지원자의 개인정보를 재생이 불가능한 방법으로 즉시 파기합니다.");
//    	}
//    	System.out.println("귀하는 이에 대한 동의를 거부할 수 있으며, 다만, 동의가 없을 경우 위촉 전형 진행이 불가능할 수 있음을 알려드립니다.\n");
//    	System.out.println("개인정보 수집 및 이용에 동의함/동의안함 (Y/N): ");
//    	String agree2 = scanner.nextLine();
//    	userValidationController.validateChoice(agree2);
//    	if(userValidationController.validateChoice(agree2) == false) {
//    		System.out.println("안녕히가십시요");
//    		System.exit(0);
//    	}
		try {
			userValidationController.createUser(name, email, password, address, phone);
			mainLayer.main();
		} catch (ShopException e) {
			System.out.println("e = " + e);
		}
    	System.out.println("성공적으로 회원가입이 되었습니다.");
    }

    public void updateManager() {
    	System.out.println("user를 manager로 권한부여합니다");
    	//System.out.println("현재 UserID: ");
    	//String id = input.nextLine();
    	// [feat] 총괄적이게 administrator 이 되려면 저장된 파일 비밀번호와 일치할경우에만 가능
//    	System.out.println("Admin password: ");
//    	ArrayList<String> password = new ArrayList<String>();
//    	password.add(scanner.nextLine());
//    	adminCtrl.checkManagerByPassword(password);
		try {
			User user = userValidationController.updateManager(sessionService.getLoggedInUser());
			System.out.println("성공적으로 매니저로 승격되었습니다.");
			sessionService.updateSessionUser(user);
		} catch (ShopException e) {
			System.out.println("e = " + e);
		}
    }
    public void withdrawUser()  {
//    	System.out.println("퇴출 고객 지정중");
//    	System.out.println("Admin password: ");
//    	ArrayList<String> password = new ArrayList<String>();
//    	password.add(scanner.nextLine());
//    	adminCtrl.checkManagerByPassword(password);
    	System.out.print("탈퇴시킬 고객네임: ");
    	String username = scanner.nextLine();
		try {
			userValidationController.withdrawUserByAdmin(sessionService.getLoggedInUser(), username);
			System.out.println("성공적으로 퇴출완료");
			mainLayer.main();
		} catch (Exception e) {
			System.out.println("e = " + e);
		}
    }
    
    public void findUser() {
//		System.out.println("유저의 정보를 출력합니다: ");
//		System.out.println(sessionService.getLoggedInUser());
//    	userValidationController.findUser(user);
    }
    public void displayLoggedUser() {
		System.out.println("로그인된 현재 유저 조회를 시작합니다.");
		System.out.println("-".repeat(20));
		System.out.println(sessionService.getLoggedInUser());
		System.out.println("-".repeat(20));
		System.out.println();
//    	System.out.println("Admin password: ");
//    	ArrayList<String> password = new ArrayList<String>();
//    	password.add(scanner.nextLine());
//    	adminCtrl.checkManagerByPassword(password);
//		try {
//			userValidationController.displayUser(sessionService.getLoggedInUser());
//		} catch (ShopException e) {
//			System.out.println("e = " + e);
//		}
    }
    public void displayAllUser(User adminUser) {
//    	System.out.println("모든 유저를 확인합니다.");
//    	System.out.println("Admin password: ");
//    	ArrayList<String> password = new ArrayList<String>();
//    	password.add(scanner.nextLine());
//    	adminCtrl.checkManagerByPassword(password);
		System.out.println("모든 유저를 조회합니다.");
    	userValidationController.findAllUsers(adminUser);
    }
    public void changePassword(User user) {
    	System.out.println("패스워드를 변경합니다.");
    	System.out.println("현재 패스워드를 입력하시오");
    	String currentPwd = scanner.nextLine();
//    	userValidationController.checkPassword(user.getPassword(), password);
    	
    	System.out.println("변경할 비밀번호를 입력하시오");
    	String newPwd = scanner.nextLine();
//    	userValidationController.changePassword(user.getUsername(), password2);
		try {
			User updatedUser = userValidationController.changePassword(user, currentPwd, newPwd);
			System.out.println("유저 정보 : " + updatedUser);
			System.out.println("변경되었습니다.");
		} catch (ShopException e) {
			System.out.println("e = " + e);
		}
    }
    //상세정보 변경
    public void updateUser(User user) {
    	System.out.println("유저를 상세정보를 변경합니다.");
    	System.out.println("변경할 이메일을 입력하세요: ");
    	String email = scanner.nextLine();
    	System.out.println("변경할 현재 거주하고 계신 주소를 입력해주세요: ");
    	String address = scanner.nextLine();
    	System.out.println("변경할 전화번호를 입력해주세요: ");
    	String phone = scanner.nextLine(); // xxx-xxxx-xxxx
    	System.out.println("-------------------------------");
//    	System.out.println("유저이름을 입력하세요\t\t\t|");
//    	String name = input.nextLine();
//    	System.out.println("비밀번호를 입력하세요\t\t\t|");
//    	String password = input.nextLine();
//    	System.out.println("비밀번호를 다시 입력하세요\t\t|");
    	
//    	String password2 = input.nextLine();
//    	ctrl.checkPassword(password, password2);
    	
//    	User changedUser = ctrl.createUser(name, email, password2, address, phone);
//    	ctrl.updateUser(user, changedUser);
		try {
			User updatedUser = userValidationController.updateUser(user, email, address, phone);
			sessionService.updateSessionUser(user);
			System.out.println("유저 정보 : " + updatedUser);
			System.out.println("변경되었습니다.");

		} catch (ShopException e) {
			System.out.println("e = " + e);
		}
    	System.out.println("변경되었습니다.");
    }
    public void withdrawal() {
    	System.out.println("회원탈퇴 시작합니다");
//    	System.out.println("유저네임: ");
//    	String username = input.nextLine();
//    	System.out.println("패스워드: ");
//    	String password = input.nextLine();
//    	ctrl.withdrawal(username, password);
		try {
			userValidationController.withdrawal(sessionService.getLoggedInUser());
			sessionService.logout();
			mainLayer.main();
		} catch (ShopException e){
			System.out.println("e = " + e);
		}
    	System.out.println("성공적으로 회원탈퇴 완료");
    }
    
    
}
