package com.example.se306project1.utilities;

import com.example.se306project1.database.CartDatabase;
import com.example.se306project1.models.CartProduct;

import java.util.ArrayList;
import java.util.List;

public class CartState {

    private static CartState cartState = new CartState();

    private List<CartProduct> cartProducts;
    private List<String> checkedProducts;

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
        if (this.checkedProducts.contains(productName)) return;
        for (int i = 0; i < this.cartProducts.size(); i++) {
            if (this.cartProducts.get(i).getName().equals(productName)) {
                this.checkedProducts.add(this.cartProducts.get(i).getName());
            }
        }
    }

    public void uncheckItem(String productName) {
        this.checkedProducts.remove(productName);
    }

    public double getPrice() {
        double total = 0;
        for (String productName: this.checkedProducts) {
            CartProduct cartProduct = this.cartProducts.stream().filter(c -> c.getName().equals(productName)).findFirst().get();
            total += cartProduct.getPrice() * cartProduct.getAmount();
        }
        return total;
    }

    public void checkAll() {
        this.checkedProducts.clear();
        for (CartProduct c: this.cartProducts) {
            this.checkedProducts.add(c.getName());
        }
    }

    public void uncheckAll() {
        this.checkedProducts.clear();
    }

    public void printAllChecked() {
        System.out.println(this.checkedProducts);
    }

    public boolean isAllChecked() {
        return this.checkedProducts.size() == this.cartProducts.size();
    }

    public boolean isItemChecked(String productName) {
        return this.checkedProducts.contains(productName);
    }

    public static CartState getCartState() {
        return cartState;
    }

}
