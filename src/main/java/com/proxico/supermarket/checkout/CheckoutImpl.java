package com.proxico.supermarket.checkout;

import com.proxico.supermarket.checkout.service.TotalPriceCalculator;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public final class CheckoutImpl implements Checkout {
    private final Queue<Long> queue = new LinkedList<>();
    private final TotalPriceCalculator totalPriceCalculator;

    public CheckoutImpl(TotalPriceCalculator totalPriceCalculator) {
        if (totalPriceCalculator == null)
            throw new NullPointerException("totalPriceCalculator");

        this.totalPriceCalculator = totalPriceCalculator;
    }

    @Override
    public void scan(String itemId, int amount) {
        if (itemId == null)
            throw new NullPointerException("itemId");
        if (Objects.equals(itemId, ""))
            throw new IllegalArgumentException("ItemId should be non-empty string");
        if (amount <= 0)
            throw new IllegalArgumentException("Amount should be a positive value");

        queue.add(totalPriceCalculator.calcTotalPrice(itemId, amount));
    }

    @Override
    public int read(CharBuffer buffer) throws IOException {
        if (buffer == null)
            throw new NullPointerException("buffer");

        if (queue.isEmpty())
            return -1;

        long totalPrice = queue.poll();
        String src = totalPrice + "\n";

        buffer.put(src);
        return src.length();
    }
}
