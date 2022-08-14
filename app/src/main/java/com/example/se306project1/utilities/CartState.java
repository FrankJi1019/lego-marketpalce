package com.example.se306project1.utilities;

import com.example.se306project1.database.CartDatabase;
import com.example.se306project1.models.CartProduct;

import java.util.ArrayList;
import java.util.List;

public class CartState {

    private static CartState cartState = new CartState();

    private List<CartProduct> cartProducts;
    private List<Integer> checkedProducts;

    private CartState() {
        this.cartProducts = new ArrayList<>();
        this.checkedProducts = new ArrayList<>();
    }

    public void setCartList(List<CartProduct> cartProducts) {
        this.cartProducts.addAll(cartProducts);
    }

    public List<CartProduct> getCartProducts() {
        return this.cartProducts;
    }

    public void addToCart(CartProduct cartProduct) {
        for (CartProduct c: cartProducts) {
            if (c.getName().equals(cartProduct.getName())) return;
        }
        this.cartProducts.add(cartProduct);
        CartDatabase db = CartDatabase.getInstance();
        db.addProductToCart(UserState.getInstance().getCurrentUser().getUsername(),cartProduct.getName());
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
                break;
            }
        }
        CartDatabase db = CartDatabase.getInstance();
        db.removeProductFromCart(UserState.getInstance().getCurrentUser().getUsername(),productName);
    }

    public void checkItem(String productName) {
        System.out.println("looking for: " + productName);
        for (int i = 0; i < this.cartProducts.size(); i++) {
            System.out.println(this.cartProducts.get(i).getName());
            if (this.cartProducts.get(i).getName().equals(productName)) {
                this.checkedProducts.add(i);
            }
        }
    }

    public void uncheckItem(String productName) {
        for (int i = 0; i < this.cartProducts.size(); i++) {
            if (this.cartProducts.get(i).getName().equals(productName)) {
                this.checkedProducts.remove(Integer.valueOf(i));
            }
        }
    }

    public double getPrice() {
        double total = 0;
        for (int i: this.checkedProducts) {
            total += this.cartProducts.get(i).getPrice() * this.cartProducts.get(i).getAmount();
        }
        return total;
    }

    public void checkAll() {
        this.checkedProducts.clear();
        for (int i = 0; i < this.cartProducts.size(); i++) {
            this.checkedProducts.add(i);
        }
    }

    public void uncheckAll() {
        this.checkedProducts.clear();
    }

    public boolean isAllChecked() {
        return this.checkedProducts.size() == this.cartProducts.size();
    }

    public boolean isAllUnchecked() {
        return this.checkedProducts.isEmpty();
    }

    public static CartState getCartState() {
        return cartState;
    }

}
