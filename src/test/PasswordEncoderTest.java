package test;

import helper.PasswordEncoder;
import helper.PasswordEncoderImpl;

public class PasswordEncoderTest {

    private static PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        passwordEncoder = new PasswordEncoderImpl();
        encodeTest();
    }

    private static void encodeTest() {
        String encoded = passwordEncoder.encode("helloWorld");
        System.out.println("encoded = " + encoded);
        String decode = passwordEncoder.decode(encoded);
        System.out.println("decode = " + decode);
    }
}
