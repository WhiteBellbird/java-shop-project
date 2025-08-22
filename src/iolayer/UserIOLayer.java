package iolayer;

import java.util.Scanner;

import controller.UserValidationController;
import service.UserService;

public class UserIOLayer {
    private Scanner input;
	UserValidationController ctrl;
	
	public UserIOLayer(UserValidationController userValidationController) {
		this.ctrl = userValidationController;
	}
    public UserIOLayer(Scanner input) {
        this.input = input;
    }
    public String readLine() {
    	return input.nextLine();
    }
    public void createUser() {
    	System.out.println("이메일을 입력하세요: ");
    	String email = readLine();
    	System.out.println("현재 거주하고 계신 주소를 입력해주세요: ");
    	String address = readLine();
    	System.out.println("전화번호를 입력해주세요: ");
    	String phone = readLine(); // xxx-xxxx-xxxx
    	
    	System.out.println("-------------------------------");
    	System.out.println("유저이름을 입력하세요\t\t|");
    	String name = input.nextLine();
    	System.out.println("비밀번호를 입력하세요\t\\t|");
    	String password = input.nextLine();
    	System.out.println("비밀번호를 다시 입력하세요\t\t|");
    	String password2 = input.nextLine();
    	ctrl.checkPassword(password, password2);
    	System.out.println("-------------------------------");
    	System.out.println("개인 정보에 대한 동의서를 확인하시겠습니까? [30 페이지 양의 동의서 출력 예정] (Y/N): ");
    	String agree = input.nextLine();
    	if(ctrl.validateChoice(agree)){
    		System.out.println("수집하는 개인정보 항목 :");
    		System.out.println("개인식별정보 : 성명, 주소, 전화번호, 이메일, 기타 위촉을 위해 본인이 작성한 관련 정보 등");
    		System.out.println("개인정보의 수집 및 이용목적");
    		System.out.println("공하신 정보는 위촉절차의 집행 및 관리, 경력‧자격 등 확인(조회 및 검증), 위촉 여부의 결정, 민원처리에 사용 됩니다.\n"
    				+ "① 본인 확인 및 범죄경력 조회에 이용: 성명, 생년월일\n"
    				+ "② 지원자와의 의사소통 및 정보 전달 등에 이용: 성명, 주소, 전화번호, 휴대전화번호, 이메일\n");
    		System.out.println("수집된 개인정보는 지원서 제출 후 위촉기간 만료 시 또는 지원서 삭제 요청 시까지 위 이용 목적을 위하여 보유‧이용됩니다. 또한 삭제 요청 시 지원자의 개인정보를 재생이 불가능한 방법으로 즉시 파기합니다.");
    	}
    	System.out.println("귀하는 이에 대한 동의를 거부할 수 있으며, 다만, 동의가 없을 경우 위촉 전형 진행이 불가능할 수 있음을 알려드립니다.\n");
    	System.out.println("개인정보 수집 및 이용에 동의함/동의안함 (Y/N): ");
    	String agree2 = input.nextLine();
    	ctrl.validateChoice(agree2);
    	if(ctrl.validateChoice(agree2) == false) {
    		System.out.println("꺼지쇼");
    		System.exit(0);
    	}
    	ctrl.createUser(name, email, password2, address, phone);
    	System.out.println("성공적으로 회원가입이 되었습니다.");
    }
    public void updateManager() {
    	
    }
    
    
    
    
    
    
    
    
    
}
