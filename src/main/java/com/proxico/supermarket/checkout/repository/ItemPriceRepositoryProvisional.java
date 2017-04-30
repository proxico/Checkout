package com.proxico.supermarket.checkout.repository;

import com.proxico.supermarket.checkout.model.ItemPrice;

import java.util.HashMap;
import java.util.Map;

// This implementation should be replaced with real repository
// that reads pricing data from some external storage or service.
// Local caching fetched data is to be considered.
// It's not covered with unit test as being a provisional one.
public final class ItemPriceRepositoryProvisional implements ItemPriceRepository {
    private final Map<String, ItemPrice> priceMap = new HashMap<>();

    public ItemPriceRepositoryProvisional() {
        priceMap.put("A", new ItemPrice(40, 3, 70));
        priceMap.put("B", new ItemPrice(10, 2, 15));
        priceMap.put("C", new ItemPrice(30));
        priceMap.put("D", new ItemPrice(25));
    }

    @Override
    public ItemPrice getItem(String itemId) {
        return priceMap.get(itemId);
    }
}