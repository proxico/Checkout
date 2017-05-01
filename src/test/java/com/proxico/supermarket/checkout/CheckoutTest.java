package com.proxico.supermarket.checkout;

import com.proxico.supermarket.checkout.service.TotalPriceCalculator;
import org.junit.Test;

import java.nio.CharBuffer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CheckoutTest {
    @Test(expected = NullPointerException.class)
    public void instantiationNullPointerException() throws Exception {
        // act
        new CheckoutImpl(null);
    }


    @Test(expected = NullPointerException.class)
    public void scanNullPointerExceptionOnNullItemId() throws Exception {
        // arrange
        TotalPriceCalculator totalPriceCalculator = mock(TotalPriceCalculator.class);
        Checkout checkout = new CheckoutImpl(totalPriceCalculator);
        // act
        checkout.scan(null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void scanNullPointerExceptionOnEmptyItemId() throws Exception {
        // arrange
        TotalPriceCalculator totalPriceCalculator = mock(TotalPriceCalculator.class);
        Checkout checkout = new CheckoutImpl(totalPriceCalculator);
        // act
        checkout.scan("", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void scanNullPointerExceptionOnZeroAmount() throws Exception {
        // arrange
        TotalPriceCalculator totalPriceCalculator = mock(TotalPriceCalculator.class);
        Checkout checkout = new CheckoutImpl(totalPriceCalculator);
        // act
        checkout.scan("A", 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void scanNullPointerExceptionOnNegativeAmount() throws Exception {
        // arrange
        TotalPriceCalculator totalPriceCalculator = mock(TotalPriceCalculator.class);
        Checkout checkout = new CheckoutImpl(totalPriceCalculator);
        // act
        checkout.scan("A", -1);
    }

    @Test
    public void scanTotalPriceCalculatorCalled() throws Exception {
        // arrange
        TotalPriceCalculator totalPriceCalculator = mock(TotalPriceCalculator.class);
        Checkout checkout = new CheckoutImpl(totalPriceCalculator);

        when(totalPriceCalculator.calcTotalPrice("A", 1)).thenReturn(0L);

        // act
        checkout.scan("A", 1);

        // assert
        verify(totalPriceCalculator).calcTotalPrice("A", 1);
    }


    @Test(expected = NullPointerException.class)
    public void readNullPointerExceptionOnNullBuffer() throws Exception {
        // arrange
        TotalPriceCalculator totalPriceCalculator = mock(TotalPriceCalculator.class);
        Checkout checkout = new CheckoutImpl(totalPriceCalculator);
        // act
        checkout.read(null);
    }

    @Test
    public void readWithEmptyResult() throws Exception {
        // arrange
        TotalPriceCalculator totalPriceCalculator = mock(TotalPriceCalculator.class);
        Checkout checkout = new CheckoutImpl(totalPriceCalculator);

        CharBuffer buffer = mock(CharBuffer.class);

        // act
        int length = checkout.read(buffer);

        // assert
        assertEquals(-1, length);
    }

    @Test
    public void readGetsPriceCalculatedWhenScannedBefore() throws Exception {
        // arrange
        TotalPriceCalculator totalPriceCalculator = mock(TotalPriceCalculator.class);
        CharBuffer buffer = mock(CharBuffer.class);
        Checkout checkout = new CheckoutImpl(totalPriceCalculator);

        long expectedPrice = 50L;
        String expectedStringPrice = String.valueOf(expectedPrice) + "\n";

        when(totalPriceCalculator.calcTotalPrice("A", 1)).thenReturn(expectedPrice);

        // act
        checkout.scan("A", 1);
        int length = checkout.read(buffer);

        // assert
        assertEquals(expectedStringPrice.length(), length);
        verify(buffer).put(expectedStringPrice);
    }

    @Test
    public void readEmptyResultWhenAllIsFetched() throws Exception {
        // arrange
        TotalPriceCalculator totalPriceCalculator = mock(TotalPriceCalculator.class);
        CharBuffer buffer = mock(CharBuffer.class);
        Checkout checkout = new CheckoutImpl(totalPriceCalculator);

        when(totalPriceCalculator.calcTotalPrice("A", 1)).thenReturn(50L);

        // act
        checkout.scan("A", 1); // push an item
        checkout.read(buffer); // pull the item's total rice
        int length = checkout.read(buffer);

        // assert
        assertEquals(-1, length);
    }
}