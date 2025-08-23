package service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domain.User;
import exception.*;
import helper.PasswordEncoder;
import repository.UserRepository;

public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(String username, String email, String password, String address, String phone) {
        try {
            //isRegisteredUserEmail(email); // 유저 이메일이 우리의 파일에 이미 저장되어 있는지
            //validateCreateUser(username, email, password); // 입력한 입력값들이 실제로 타당한 값인지? 빈값이 없다든가
            // UUID.randomUUID()는 랜덤한 String 문자열을 생성하는 static 메서드입니다.
            // 유저 객체를 생성
            validateDuplicatedUsernameAndEmail(username, email);
            String encoded = passwordEncoder.encode(password);
            User newUser = new User(UUID.randomUUID().toString(), username, email, encoded,
                    "SILVER", address, 0, null, phone, false, LocalDateTime.now(), null, null);
            // 유저 객체를 생성하고 파일에서 생성된 유저 값을 불러옴
            User saved = repository.saveUser(newUser);
            // 아 참고로, 결과 값을 출력할 때, 비밀번호 같은 민감한 데이터를 출력하지 않도록 합시다.
            //System.out.println(saved.toString());
            repository.commit();
            System.out.printf("\n[DEBUG] User created: %s\n", saved);
            return saved;
        } catch (ShopException e) {
            repository.rollback();
            throw e;
        }
    }

    @Override
    public User updateManager(User user) {
        try {
//            User findUser = repository.findUserByUserId(userId);
            // 유저를 찾을 수 없다면 예외처리
//            if (findUser == null) {
//                throw new UserNotfoundException(String.format("%s 아이디의 유저를 찾을 수 없습니다.", userId));
//            }
            // 유저를 관리자로 승격
            if (user.isAdmin()) {
                System.out.printf("\n[ERROR] %s is already admin\n", user.getUsername());
                throw new UserDuplicatedException(String.format("%s is already admin", user.getUsername()));
            }
            user.giveManagerAuthentication();
            // 리포지토리에서 유저를 업데이트
            //User updated = repository.authorizeUser(findUser);
            User saved = repository.saveUser(user);
            // 결과 값 출력할 때 비밀번호 같은 민감한 데이터를 출력하지 않도록 합시다.
            repository.commit();
            System.out.printf("\n[DEBUG] Manager updated: %s\n", saved);
            return saved;
        } catch (ShopException e) {
            repository.rollback();
            throw new ShopException(e.getMessage());
        }
    }


    // DisplayUsers 를 유저 서비스에서 출력하는건 SRP 위반이야.
    // 비즈니스 로직만 처리하는게 유저 서비스의 궁극적인 역할이야
    // 유저 리포지토리에서 조회한 결과를 그대로 그냥 return 해줘서 이 서비스의 역할을 지키는 것이 맞다고 생각해.
    // 다시 한 번, 말하지만 유저 서비스는 '출력'을 담당하는 것이 아닌 '객체의 변화'에 스탠스를 가지는 클래스라는 점
    // 서비스는 리포지토리에서 조회한걸 그대로 리턴해주고, 출력은 IO Layer 에서 해줬으면 해.


    //관리자용
    @Override
    public User displayUserByAdmin(User user, String username) {
        if (!user.isAdmin()) {
            System.out.printf("\n[ERROR] %s is not authenticated.\n", user);
            throw new UserAuthenticationException(String.format("%s is not authorized to access this resource.",
                    user.getUsername()));
        }
//        User user = repository.findUserByUsername(username);
        return Optional.of(repository.findUserByUsername(username)).orElseThrow(() -> new UserNotfoundException(
                String.format("%s is not found.", username)
        ));
    }

    @Override
    public List<User> withdrawUserByAdmin(User user, String willDeleteUsername) {
        try {
//            if (repository.findUserByUsername(username) == null) {
//                throw new ShopException("유저가 존재하지 않습니다/ 찾을 수 없습니다");
//            }
            if (!user.isAdmin()) {
                System.out.printf("\n[ERROR] %s is not authenticated.\n", user);
                throw new UserAuthenticationException(String.format("%s is not authenticated.", user.getUsername()));
            }
//            repository.delete(repository.findUserByUsername(username));
            Optional.of(repository.findUserByUsername(user.getUsername())).ifPresent(repository::delete);
            repository.commit();
            return repository.getUsersList();
        } catch (ShopException e) {
            repository.rollback();
//            System.out.println(e.getClass() + ": 퇴출과정중 에러 발생");
            throw e;
        }
    }


    //고객/유저용
    @Override
    @Deprecated
    public User findUser(String username, String password) {

        if (repository.findUserByUsername(username) == null || repository.findUserByUsername(username).getPassword() != password) {

            throw new ShopException("옳바르지 않은 유저네임이거나 패스워드가 틀렸습니다.");
        }
        return repository.findUserByUsername(username);
    }

    @Override
    public List<User> displayAllUsersByAdmin(User user) {
//        if (repository.findUserByUsername(adminUsername) == null || repository.findUserByUsername(adminUsername).getPassword() != adminPassword) {
//            throw new ShopException("옳바르지 않은 유저네임이거나 패스워드가 틀렸습니다.");
//        }
//        return repository.getUsersList();
        if (!user.isAdmin()) {
            System.out.printf("\n[ERROR] %s is not authenticated.\n", user);
            throw new UserAuthenticationException(String.format("%s is not authorized to access this resource.",
                    user.getUsername()));
        }
        return repository.getUsersList();
    }

    @Override
    public User changePassword(User user, String currentPwd, String newPwd) {
        try {
//            if (repository.findUserByUsername(username) == null) {
//                throw new ShopException("옳바르지 않은 유저네임이거나 패스워드가 틀렸습니다.");
//            }
//            User user = repository.findUserByUsername(username);
//            repository.delete(user);
//            String encodedCurrentPwd = passwordEncoder.encode(currentPwd);
            String decoded = passwordEncoder.decode(user.getPassword());
            if (!decoded.trim().equals(currentPwd)) {
                throw new UserAuthenticationException(String.format("%s is not matched current Password",
                        user.getUsername()));
            }
//            user.updatePassword(changedPassword);
            String encodedNewPwd = passwordEncoder.encode(newPwd);
            user.updatePassword(encodedNewPwd);
            User saved = repository.saveUser(user);
            repository.commit();
            System.out.printf("\n[DEBUG] Password changed: %s\n", saved);
            return saved;
        } catch (ShopException e) {
            repository.rollback();
//            System.out.println(e.getClass() + "패스워드 변경중 문제 발생");
            throw e;
        }
    }

    @Override
    public User updateUser(User user, String email, String address, String phone) {
        try {
            user.updateUser(email, address, phone);
            User savedUser = repository.saveUser(user);
//            if (repository.findUserByUserId(previousUser.getUserId()) == null) {
//                throw new ShopException("옳바르지 않은 유저이거나 찾을수 없습니다.");
//            }
//            repository.replaceUser(previousUser, changedUser);


            repository.commit();
            System.out.printf("\n[DEBUG] %s is saved\n", savedUser);
            return savedUser;
        } catch (ShopException e) {
            repository.rollback();
//            System.out.println(e.getClass() + "고객정보 변경중 문제 발생");
            throw e;
        }
    }

    @Override
    public Boolean withdrawal(User user,String password) {
        try {
            String decoded = passwordEncoder.decode(user.getPassword());
            if (!decoded.trim().equals(password)) {
                throw new UserAuthenticationException(String.format("%s password is not matched.", user.getUsername()));
            }
            repository.delete(user);
            System.out.printf("\n[DEBUG] %s is deleted\n", user);
            repository.commit();
            return Boolean.TRUE;
        } catch (ShopException e) {
            repository.rollback();
//            System.out.println(e.getMessage() + "회원 탈퇴중 문제 발생");
            return Boolean.FALSE;
        }
    }

//    @Override
//    public boolean CheckPassword(String firstInput, String SecondInput) {
//        if (!firstInput.equals(SecondInput)) {
//            throw new ShopException("패스워드가 일치하지 않습니다");
//        }
//        return firstInput.equals(SecondInput);
//    }

//    public boolean validateChoice(String choice) {
//        try {
//            if (choice.charAt(0) == 'y' || choice.charAt(0) == 'Y') {
//                return true;
//            } else if (choice.charAt(0) == 'n' || choice.charAt(0) == 'N') {
//                return false;
//            } else {
//                throw new ShopException("wrong choice input");
//            }
//        } catch (ShopException e) {
//            throw new ShopException("wrong choice input");
//        }
//    }

    private void validateDuplicatedUsernameAndEmail(String email, String username) {
        User userByUsername = repository.findUserByUsername(username);
        if (userByUsername == null) {
            System.out.println("[ERROR] " + username + " is not found.");
        }
//        Optional.of(repository.findUserByUsername(username)).orElseThrow(()->
//                new UserDuplicatedException(String.format("username %s not found", username)));
//        Optional.of(repository.findUserByUsername(username)).ifPresent((user) -> {
//            throw new UserDuplicatedException(String.format("%s is existed.", username));
//        });
//        Optional.of(repository.findUserByEmail(email)).ifPresent((user) -> {
//            throw new UserDuplicatedException(String.format("%s is existed.", email));
//        });
    }
}
