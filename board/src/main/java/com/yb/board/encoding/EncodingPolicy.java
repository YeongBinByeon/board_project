package com.yb.board.encoding;

public interface EncodingPolicy {
    String encoding(String password);

    String decoding(String password);
}
