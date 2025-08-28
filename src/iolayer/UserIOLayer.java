package iolayer;

import java.util.Scanner;

import controller.UserController;
import domain.User;
import exception.ShopException;
import helper.IOHelper;
import service.SessionService;

public class UserIOLayer {
    private Scanner scanner;
    private UserController userController;
    private SessionService sessionService;

    public UserIOLayer(Scanner scanner,
                       UserController userController,
                       SessionService sessionService) {
        this.scanner = scanner;
        this.userController = userController;
        this.sessionService = sessionService;
    }

    public void myPage() {
        while (true) {
            System.out.println("┌────────────────────────────────────┐");
            System.out.println("│         👤 마이 페이지                │");
            System.out.println("├────────────────────────────────────┤");
            System.out.println("│1. 내 정보 조회                        │");
            System.out.println("│2. 비밀번호 변경                       │");
            System.out.println("│3. 개인정보 수정                       │");
            System.out.println("│4. 회원 탈퇴                          │");
            System.out.println("│5. 돌아가기                           │");
            System.out.println("└────────────────────────────────────┘");
            System.out.print("메뉴를 선택하세요: _");

            int choice = Integer.parseInt(scanner.next());
            scanner.nextLine();
            switch (choice) {
                case 1:
                    displayLoggedUser();
                    break;
                case 2:
                    changePassword();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    withdrawal();
                    return;
                case 5:
                    System.out.println("이전 메뉴로 돌아갑니다...");
                    return;
                default:
                    System.out.println("올바른 메뉴를 선택해주세요.");
            }
        }
    }

    public void createUser() {
        IOHelper.printFirstLine();
        System.out.print("이메일을 입력하세요: ");
        String email = scanner.next();
        scanner.nextLine();
        System.out.print("현재 거주하고 계신 주소를 입력해주세요: ");
        String address = scanner.nextLine();
        System.out.print("전화번호를 입력해주세요: ");
        String phone = scanner.nextLine();
        System.out.print("유저이름을 입력하세요: ");
        String name = scanner.nextLine();
        String password;
        String checkPassword;
        while (true) {
            System.out.print("비밀번호를 입력하세요: ");
            password = scanner.next();
            System.out.print("비밀번호를 다시 입력하세요: ");
            checkPassword = scanner.next();
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
            scanner.nextLine();
            IOHelper.printEndLine();
        }
    }

    private void displayLoggedUser() {
        IOHelper.printFirstLine();
        System.out.println("로그인된 현재 유저 조회를 시작합니다.");
        System.out.println(sessionService.getLoggedInUser());
        IOHelper.printEndLine();
    }

    private void changePassword() {
        IOHelper.printFirstLine();
        System.out.println("패스워드를 변경합니다.");
        System.out.print("현재 패스워드를 입력하시오: ");
        String currentPwd = scanner.next();
        System.out.print("변경할 비밀번호를 입력하시오: ");
        String newPwd = scanner.next();
        try {
            User updatedUser = userController.changePassword(sessionService.getLoggedInUser(), currentPwd, newPwd);
            System.out.println("유저 정보: " + updatedUser);
            System.out.println("변경되었습니다.");
        } catch (ShopException e) {
            System.out.println("오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();
        }
    }

    public void updateUser() {
        IOHelper.printFirstLine();
        System.out.println("유저 상세정보를 변경합니다.");
        System.out.print("변경할 이메일을 입력하세요: ");
        String email = scanner.nextLine();
        System.out.print("변경할 현재 거주하고 계신 주소를 입력해주세요: ");
        String address = scanner.nextLine();
        System.out.print("변경할 전화번호를 입력해주세요: ");
        String phone = scanner.nextLine();
        try {
            User updatedUser = userController.updateUser(sessionService.getLoggedInUser(), email, address, phone);
            sessionService.updateSessionUser(updatedUser);
            System.out.println("업데이트 된 유저 정보: " + updatedUser);
            System.out.println("변경되었습니다.");
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
            System.out.print("회원 탈퇴를 위해 비밀번호를 다시 입력해주세요: ");
            String password = scanner.next();
            userController.withdrawal(sessionService.getLoggedInUser(), password);
            sessionService.logout();
            System.out.println("성공적으로 회원탈퇴 완료");
        } catch (ShopException e) {
            System.out.println("오류가 발생했습니다: " + e.getMessage());
        } finally {
            IOHelper.printEndLine();
        }
    }
}
