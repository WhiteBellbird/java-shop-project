package helper;

public interface PasswordEncoder {

    String decode(String password);
    String encode(String password);
}
