package com.proxico.supermarket.checkout.repository;

import com.proxico.supermarket.checkout.model.ItemPrice;

public interface ItemPriceRepository {
    ItemPrice getItem(String itemId);
}
