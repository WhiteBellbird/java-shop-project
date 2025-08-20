package helper;

import java.util.StringTokenizer;
import java.util.UUID;

public class PasswordEncoderImpl implements PasswordEncoder {

    @Override
    public String decode(String password) {
        StringTokenizer st = new StringTokenizer(password, ".");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String token = st.nextToken();
            result.append(token.charAt(token.length() - 1));
        }
        String token = st.nextToken();
        result.append(token.substring(12));
        return result.toString();
    }

    @Override
    public String encode(String password) {
        // 1 , 1 , 1 , 1
        String encoder = UUID.randomUUID().toString();
        StringTokenizer st = new StringTokenizer(encoder, "-");
        StringBuilder finalEncode = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String token = st.nextToken();
            String encoded = token + password.charAt(i) + ".";
            finalEncode.append(encoded);
        }
        String token = st.nextToken();
        String fourthWord = password.substring(4);
        finalEncode.append(token).append(fourthWord);
        return finalEncode.toString();
    }
}
