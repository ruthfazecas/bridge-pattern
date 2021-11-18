package org.example.service;

import org.example.model.SubMenuItem;

import java.util.ArrayList;
import java.util.List;

public class CartService {
    private final List<SubMenuItem> cart = new ArrayList<>();

    public void addItemToCart(SubMenuItem item) {
        cart.add(item);
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (var item : cart) {
            totalPrice += item.getPriceWithVAT();
        }
        return totalPrice;
    }
}
