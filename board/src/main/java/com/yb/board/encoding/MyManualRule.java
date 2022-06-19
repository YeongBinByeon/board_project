package com.yb.board.encoding;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("MyManualRule")
public class MyManualRule implements EncodingPolicy{
    @Override
    public String encoding(String password) {
        return password+"bybbyb";
    }

    @Override
    public String decoding(String password) {
        return password.replace("bybbyb","");
    }
}
