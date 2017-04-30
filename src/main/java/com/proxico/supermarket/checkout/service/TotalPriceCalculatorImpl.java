package com.proxico.supermarket.checkout.service;

import com.proxico.supermarket.checkout.model.ItemPrice;
import com.proxico.supermarket.checkout.repository.ItemPriceRepository;

import java.util.NoSuchElementException;
import java.util.Objects;

public final class TotalPriceCalculatorImpl implements TotalPriceCalculator {
    private final ItemPriceRepository itemPriceRepository;

    public TotalPriceCalculatorImpl(ItemPriceRepository itemPriceRepository) {
        if (itemPriceRepository == null)
            throw new NullPointerException("itemPriceRepository");

        this.itemPriceRepository = itemPriceRepository;
    }

    @Override
    public long calcTotalPrice(String itemId, int amount) {
        if (itemId == null)
            throw new NullPointerException("itemId");
        if (Objects.equals(itemId, ""))
            throw new IllegalArgumentException("ItemId should be non-empty string");
        if (amount <= 0)
            throw new IllegalArgumentException("Amount should be a positive value");

        ItemPrice itemPrice = itemPriceRepository.getItem(itemId);
        if (itemPrice == null)
            throw new NoSuchElementException(itemId);

        return CalcTotalPrice(itemPrice, amount);
    }

    private long CalcTotalPrice(ItemPrice itemPrice, int amount) {
        int specialAmount = itemPrice.specialAmount;

        if (specialAmount == 0)
            return (long) amount * itemPrice.price;

        long specialPacksCount = amount / specialAmount;
        long usualPriceAmount = amount % specialAmount;

        return specialPacksCount * itemPrice.specialPrice + usualPriceAmount * itemPrice.price;
    }
}