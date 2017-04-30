package com.proxico.supermarket.checkout.model;

public final class ItemPrice {
    public final int price;
    public final int specialAmount;
    public final int specialPrice;

    public ItemPrice(int price) {
        this(price, 0, 0);
    }

    public ItemPrice(int price, int specialAmount, int specialPrice) {
        if (price < 0)
            throw new IllegalArgumentException("Price should be non-negative value");
        if (specialAmount < 0)
            throw new IllegalArgumentException("SpecialAmount should be non-negative value");
        if (specialPrice < 0)
            throw new IllegalArgumentException("SpecialPrice should be non-negative value");

        this.price = price;
        this.specialAmount = specialAmount;
        this.specialPrice = specialPrice;
    }

    @Override
    public String toString() {
        return "ItemPrice{" +
                "price=" + price +
                ", specialAmount=" + specialAmount +
                ", specialPrice=" + specialPrice +
                '}';
    }
}