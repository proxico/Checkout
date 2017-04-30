package com.proxico.supermarket.checkout.service;

public interface TotalPriceCalculator {
    long calcTotalPrice(String itemId, int amount);
}
