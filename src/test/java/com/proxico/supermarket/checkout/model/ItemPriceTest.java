package com.proxico.supermarket.checkout.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemPriceTest {
    @Test(expected = IllegalArgumentException.class)
    public void createIllegalArgumentExceptionOnNegativePrice() {
        // act
        new ItemPrice(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createIllegalArgumentExceptionOnNegativeSpecialAmount() {
        // act
        new ItemPrice(50, -1, 50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createIllegalArgumentExceptionOnNegativeSpecialPrice() {
        // act
        new ItemPrice(50, 3, -1);
    }

    @Test()
    public void createFieldsInitialized() {
        // act
        ItemPrice itemPrice = new ItemPrice(50);

        // assert
        assertEquals(50, itemPrice.price);
        assertEquals(0, itemPrice.specialAmount);
        assertEquals(0, itemPrice.specialPrice);
    }

    @Test()
    public void createFieldsInitializedWithSpecialPrice() {
        // act
        ItemPrice itemPrice = new ItemPrice(50, 3, 100);

        // assert
        assertEquals(50, itemPrice.price);
        assertEquals(3, itemPrice.specialAmount);
        assertEquals(100, itemPrice.specialPrice);
    }
}