package com.example.se306project1.utilities;

import com.example.se306project1.database.FireStoreCallback;
import com.example.se306project1.database.LikesDatabase;
import com.example.se306project1.models.IProduct;
import com.example.se306project1.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserState {
    private static UserState userState = null;
    private User currentUser = null;
    private static List<IProduct> likedProducts = new ArrayList<>();

    private UserState() {
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void logoutCurrentUser() {
        userState = null;
    }

    public void setCurrentUser(User currentUser) {
        LikesDatabase likesDatabase = LikesDatabase.getInstance();
        likesDatabase.getAllProducts(new FireStoreCallback() {
            @Override
            public <T> void Callback(T value) {
                List<IProduct> allProducts = (List<IProduct>) value;
                likesDatabase.getUsersAllLikes(new FireStoreCallback() {
                    @Override
                    public <T> void Callback(T value) {
                        List<IProduct> temp = (List<IProduct>) value;
                        for (IProduct p : temp) likedProducts.add(p);
//                        hasLiked()
                    }
                }, currentUser.getUsername(), allProducts);
            }
        });
        this.currentUser = currentUser;
    }

    public boolean hasLiked(String productName) {
        for (int i = 0; i < likedProducts.size(); i++) {
            if (likedProducts.get(i).getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    public void like(IProduct product) {
        if (hasLiked(product.getName())) return;
        likedProducts.add(product);
    }

    public void unlike(IProduct product) {
        if (!hasLiked(product.getName())) return;
        for (int i = 0; i < likedProducts.size(); i++) {
            if (likedProducts.get(i).getName().equals(product.getName())) {
                likedProducts.remove(i);
            }
        }
    }

    public static UserState getInstance() {
        if (userState == null) {
            userState = new UserState();
        }
        return userState;
    }
}
