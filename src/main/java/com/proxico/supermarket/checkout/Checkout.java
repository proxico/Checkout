package com.proxico.supermarket.checkout;

import java.io.IOException;
import java.nio.CharBuffer;

public interface Checkout extends Readable {
    void scan(String itemId, int amount);

    @Override
    int read(CharBuffer buffer) throws IOException;
}
