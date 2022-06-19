package com.yb.board.encoding;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Base64")
public class Base64 implements EncodingPolicy {
    @Override
    public String encoding(String password) {
        //Base64.getEncoder().encode(password);
        String encodedPwd = java.util.Base64.getEncoder().encodeToString(password.getBytes());
        return encodedPwd;
    }

    @Override
    public String decoding(String password) {
        byte[] decodeBytes = java.util.Base64.getDecoder().decode(password);
        String decodedPwd = new String(decodeBytes);
        return decodedPwd;
    }
}
