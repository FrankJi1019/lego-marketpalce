package com.example.se306project1.statemanagement;

import com.example.se306project1.models.CartProduct;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CartState {

    private static CartState cartState = new CartState();

    private List<CartProduct> cartProducts;

    private CartState() {
        this.cartProducts = new ArrayList<>();
    }

    public List<CartProduct> getCartProducts() {
        return this.cartProducts;
    }

    public void addToCart(CartProduct cartProduct) {
        for (CartProduct c: cartProducts) {
            if (c.getName().equals(cartProduct.getName())) return;
        }
        this.cartProducts.add(cartProduct);
    }

    public void updateAmount(String productName, int newAmount) {
        for (CartProduct c: this.cartProducts) {
            if (c.getName().equals(productName)) {
                c.setAmount(newAmount);
            }
        }
    }

    public void removeCartProduct(String productName) {
        for (CartProduct c: this.cartProducts) {
            if (c.getName().equals(productName)) {
                this.cartProducts.remove(c);
                return;
            }
        }
    }

    public static CartState getCartState() {
        return cartState;
    }

}
