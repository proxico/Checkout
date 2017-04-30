package com.proxico.supermarket.checkout.service;

import com.proxico.supermarket.checkout.model.ItemPrice;
import com.proxico.supermarket.checkout.repository.ItemPriceRepository;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TotalPriceCalculatorTest {
    @Test(expected = NullPointerException.class)
    public void instantiationNullPointerException() throws Exception {
        // act
        new TotalPriceCalculatorImpl(null);
    }


    @Test(expected = NullPointerException.class)
    public void calcTotalPriceNullPointerExceptionOnNullItemId() throws Exception {
        // arrange
        ItemPriceRepository itemPriceRepository = mock(ItemPriceRepository.class);
        TotalPriceCalculator calculator = new TotalPriceCalculatorImpl(itemPriceRepository);
        // act
        calculator.calcTotalPrice(null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calcTotalPriceNullPointerExceptionOnEmptyItemId() throws Exception {
        // arrange
        ItemPriceRepository itemPriceRepository = mock(ItemPriceRepository.class);
        TotalPriceCalculator calculator = new TotalPriceCalculatorImpl(itemPriceRepository);
        // act
        calculator.calcTotalPrice("", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calcTotalPriceNullPointerExceptionOnZeroAmount() throws Exception {
        // arrange
        ItemPriceRepository itemPriceRepository = mock(ItemPriceRepository.class);
        TotalPriceCalculator calculator = new TotalPriceCalculatorImpl(itemPriceRepository);
        // act
        calculator.calcTotalPrice("A", 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calcTotalPriceNullPointerExceptionOnNegativeAmount() throws Exception {
        // arrange
        ItemPriceRepository itemPriceRepository = mock(ItemPriceRepository.class);
        TotalPriceCalculator calculator = new TotalPriceCalculatorImpl(itemPriceRepository);
        // act
        calculator.calcTotalPrice("A", -1);
    }

    @Test(expected = NoSuchElementException.class)
    public void calcTotalPriceNoSuchElementExceptionOnNonExistingItemId() throws Exception {
        // arrange
        ItemPriceRepository itemPriceRepository = mock(ItemPriceRepository.class);
        TotalPriceCalculator calculator = new TotalPriceCalculatorImpl(itemPriceRepository);
        // act
        calculator.calcTotalPrice("X", 1);
    }


    @Test()
    public void calcTotalPriceWithoutSpecialPrice() throws Exception {
        // arrange
        ItemPriceRepository itemPriceRepository = mock(ItemPriceRepository.class);
        TotalPriceCalculator calculator = new TotalPriceCalculatorImpl(itemPriceRepository);

        String itemId = "A";
        int price = 50;
        int amount = 1;

        ItemPrice itemPrice = new ItemPrice(price);
        when(itemPriceRepository.getItem(itemId)).thenReturn(itemPrice);

        // act
        long totalPrice = calculator.calcTotalPrice(itemId, amount);

        // assert
        assertEquals(price * amount, totalPrice);
    }

    @Test()
    public void calcTotalPriceWithSpecialPrice() throws Exception {
        // arrange
        ItemPriceRepository itemPriceRepository = mock(ItemPriceRepository.class);
        TotalPriceCalculator calculator = new TotalPriceCalculatorImpl(itemPriceRepository);

        String itemId = "A";
        int price = 50;
        int amount = 4;
        int specialAmount = 3;
        int specialPrice = 100;
        int expectedPrice = specialPrice + price * (amount - specialAmount); // 150

        ItemPrice itemPrice = new ItemPrice(price, specialAmount, specialPrice);
        when(itemPriceRepository.getItem(itemId)).thenReturn(itemPrice);

        // act
        long totalPrice = calculator.calcTotalPrice(itemId, amount);

        // assert
        assertEquals(expectedPrice, totalPrice);
    }

    @Test()
    public void calcTotalPriceWithSpecialPriceBigValue() throws Exception {
        // arrange
        ItemPriceRepository itemPriceRepository = mock(ItemPriceRepository.class);
        TotalPriceCalculator calculator = new TotalPriceCalculatorImpl(itemPriceRepository);

        String itemId = "A";
        int price = 5000 * 100; // $5k
        int amount = 4 * 1000 * 1000;
        int specialAmount = 3;
        int specialPrice = 10000 * 100; // $10k
        long expectedPrice = // 1333333500000
                (long) specialPrice * (amount / specialAmount) + price * (amount % specialAmount);

        ItemPrice itemPrice = new ItemPrice(price, specialAmount, specialPrice);
        when(itemPriceRepository.getItem(itemId)).thenReturn(itemPrice);

        // act
        long totalPrice = calculator.calcTotalPrice(itemId, amount);

        // assert
        assertEquals(expectedPrice, totalPrice);
    }
}