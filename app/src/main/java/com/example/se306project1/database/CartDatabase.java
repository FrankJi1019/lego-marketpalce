package com.example.se306project1.database;

import androidx.annotation.NonNull;

import com.example.se306project1.models.CartProduct;
import com.example.se306project1.models.IProduct;
import com.example.se306project1.utilities.UserState;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        cart.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    cart.update("cartproducts", FieldValue.arrayUnion(productName));
                } else {
                    Map<String, List<String>> map = new HashMap<>();
                    List<String> carts = new ArrayList<>();
                    carts.add(productName);
                    map.put("cartproducts", carts);
                    cart.set(map);
                }
            }
        });
    }

    public void removeProductFromCart(String userName, String productName) {
        DocumentReference cart = db.collection("cart").document(userName);
        cart.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (!documentSnapshot.exists()) {
                    return;
                } else {
                    cart.update("cartproducts", FieldValue.arrayRemove(productName));
                    db.collection("cartNum").document(userName+"-"+productName).delete();
                }
            }
        });
    }

    public void getUsersCartProducts(FireStoreCallback fireStoreCallback, String username, List<IProduct> products) {
        List<CartProduct> ans = new ArrayList<>();
        db.collection("cart").document(username).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> carts = (List<String>) documentSnapshot.get("cartproducts");
                if(carts == null){

                }else{
                    for(int i=0;i<products.size();i++){
                        if(carts.contains(products.get(i).getName())){
                            CartProduct cartProduct = products.get(i).toCartProduct();
//                            db.collection("cartNum").document(UserState.getInstance().getCurrentUser().getUsername()+"-"+cartProduct.getName())
//                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                        @Override
//                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                            int res = (int)documentSnapshot.get("amount");
//                                            cartProduct.setAmount(res);
//                                            ans.add(cartProduct);
//                                        }
//                                    });
                            ans.add(cartProduct);
                        }
                    }
                }
                fireStoreCallback.Callback(ans);
            }
        });
    }

    public void addCartAmount(String username,String productName){
        DocumentReference cartNum = db.collection("cartNum").document(username + "-" + productName);
        cartNum.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(!documentSnapshot.exists()){
                    Map<String,Integer> map = new HashMap<>();
                    map.put("amount",2);
                    cartNum.set(map);
                }else{
                    cartNum.update("amount",FieldValue.increment(1));
                }
            }
        });
    }

    public void SubstractCartAmount(String username,String productName){
        DocumentReference cartNum = db.collection("cartNum").document(username + "-" + productName);
        cartNum.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                 cartNum.update("amount",FieldValue.increment(-1));
            }
        });
    }

    public void getProductAmount(FireStoreCallback fireStoreCallback,List<CartProduct> cartProducts){
         List<Integer> cartProducts1= new ArrayList<>();
         for(int i=0;i<cartProducts.size();i++){
             db.collection("cartNum").document(UserState.getInstance().getCurrentUser().getUsername()+"-"+cartProducts.get(i).getName())
                     .get()
                     .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                         @Override
                         public void onSuccess(DocumentSnapshot documentSnapshot) {
                             int amount = (int)documentSnapshot.get("amount");
                             cartProducts1.add(amount);
                         }
                     });
             fireStoreCallback.Callback(cartProducts1);
         }
    }


}

//class CartRecordList {
//    public List<CartRecord> cartRecordList;
//
//    public CartRecordList() {
//
//    }
//
//    public List<CartRecord> getCartRecordList() {
//        return cartRecordList;
//    }
//
//    @Override
//    public String toString() {
//        return "CartRecordList{" +
//                "cartRecordList=" + cartRecordList +
//                '}';
//    }
//}
//
//class CartRecord {
//    private String productName;
//    private int amount;
//
//    public CartRecord() {
//
//    }
//
//    public CartRecord(String productName, int amount) {
//        this.productName = productName;
//        this.amount = amount;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }
//
//    public String getProductName() {
//        return productName;
//    }
//
//    public void setAmount(int amount) {
//        this.amount = amount;
//    }
//
//    public int getAmount() {
//        return amount;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        CartRecord that = (CartRecord) o;
//        return Objects.equals(productName, that.productName);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(productName);
//    }
//
//    @Override
//    public String toString() {
//        return "CartRecord{" +
//                "productName='" + productName + '\'' +
//                ", amount=" + amount +
//                '}';
//    }
//}