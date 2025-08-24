package helper;

import java.util.StringTokenizer;
import java.util.UUID;

public class PasswordEncoderImpl implements PasswordEncoder {

    @Override
    public String decode(String password) {
        StringTokenizer st = new StringTokenizer(password, ".");
        StringBuilder result = new StringBuilder();

        try {
            for (int i = 0; i < 4; i++) {
                if (!st.hasMoreTokens()) throw new IllegalArgumentException("Invalid encoded password format");
                String token = st.nextToken();
                if (token.length() < 1) throw new IllegalArgumentException("Token too short in encoded password");
                result.append(token.charAt(token.length() - 1));
            }

            if (!st.hasMoreTokens()) throw new IllegalArgumentException("Invalid encoded password format");
            String token = st.nextToken();
            result.append(token.length() >= 12 ? token.substring(12) : "");
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to decode password: " + e.getMessage());
        }

        return result.toString();
    }

    @Override
    public String encode(String password) {
        String encoder = UUID.randomUUID().toString();
        StringTokenizer st = new StringTokenizer(encoder, "-");
        StringBuilder finalEncode = new StringBuilder();

        int i = 0;
        while (st.hasMoreTokens() && i < 4) {
            String token = st.nextToken();
            finalEncode.append(token).append(password.charAt(i)).append(".");
            i++;
        }

        // 마지막 토큰 처리
        String token = st.hasMoreTokens() ? st.nextToken() : UUID.randomUUID().toString();
        String remaining = password.length() > 4 ? password.substring(4) : "";
        finalEncode.append(token).append(remaining);

        return finalEncode.toString();
    }
}
