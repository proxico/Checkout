package com.proxico.supermarket.checkout;

import static org.junit.Assert.assertEquals;

import com.proxico.supermarket.checkout.repository.ItemPriceRepository;
import com.proxico.supermarket.checkout.repository.ItemPriceRepositoryProvisional;
import com.proxico.supermarket.checkout.service.TotalPriceCalculator;
import com.proxico.supermarket.checkout.service.TotalPriceCalculatorImpl;
import org.junit.Test;

import java.util.Scanner;

public class CheckoutIntegrationTest {
    @Test
    public void test() {
        // arrange
        ItemPriceRepository itemPriceRepository = new ItemPriceRepositoryProvisional();
        TotalPriceCalculator totalPriceCalculator = new TotalPriceCalculatorImpl(itemPriceRepository);
        Checkout checkout = new CheckoutImpl(totalPriceCalculator);

        Scanner scanner = new Scanner(checkout);

        // act
        checkout.scan("A", 1);
        checkout.scan("A", 3);
        checkout.scan("A", 4);
        checkout.scan("B", 3);
        checkout.scan("C", 3);
        checkout.scan("D", 3);

        // assert
        assertEquals(40, scanner.nextLong());
        assertEquals(70, scanner.nextLong());
        assertEquals(110, scanner.nextLong());
        assertEquals(25, scanner.nextLong());
        assertEquals(90, scanner.nextLong());
        assertEquals(75, scanner.nextLong());
    }
}
