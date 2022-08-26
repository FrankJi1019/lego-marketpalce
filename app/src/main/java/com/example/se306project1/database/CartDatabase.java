package com.example.se306project1.database;


import com.example.se306project1.models.CartProduct;
import com.example.se306project1.models.IProduct;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartDatabase extends ProductDatabase {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static CartDatabase cartDatabase;

    public static CartDatabase getInstance() {
        if (cartDatabase == null) {
            cartDatabase = new CartDatabase();
        }

        return cartDatabase;
    }

    public void addProductToCart(String userName, String productName) {
        DocumentReference cart = db.collection("cart").document(userName);
        cart.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                cart.update("cartproducts", FieldValue.arrayUnion(productName));
            } else {
                Map<String, List<String>> map = new HashMap<>();
                List<String> carts = new ArrayList<>();
                carts.add(productName);
                map.put("cartproducts", carts);
                cart.set(map);
            }
        });
    }

    public void removeProductFromCart(String userName, String productName) {
        DocumentReference cart = db.collection("cart").document(userName);
        cart.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                cart.update("cartproducts", FieldValue.arrayRemove(productName));
                db.collection("cartNum").document(userName+"-"+productName).delete();
            }
        });
    }

    public void getUsersCartProducts(FireStoreCallback fireStoreCallback, String username, List<IProduct> products) {
        List<CartProduct> ans = new ArrayList<>();
        db.collection("cart").document(username).get().addOnSuccessListener(documentSnapshot -> {
            List<String> carts = (List<String>) documentSnapshot.get("cartproducts");
            if(carts != null) {
                for(int i=0;i<products.size();i++){
                    if(carts.contains(products.get(i).getName())){
                        CartProduct cartProduct = products.get(i).toCartProduct();
                        ans.add(cartProduct);
                    }
                }
            }
            fireStoreCallback.Callback(ans);
        });
    }

    public void subtractCartAmount(String username, String productName) {
        DocumentReference cartNum = db.collection("cartNum").document(username + "-" + productName);
        cartNum.get().addOnSuccessListener(documentSnapshot -> cartNum.update("amount",FieldValue.increment(-1)));
    }


}
