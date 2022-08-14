package com.example.se306project1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.se306project1.R;
import com.example.se306project1.adapters.CartProductAdapter;
import com.example.se306project1.database.CartDatabase;
import com.example.se306project1.database.FireStoreCallback;
import com.example.se306project1.database.ProductDatabase;
import com.example.se306project1.models.CartProduct;
import com.example.se306project1.models.IProduct;
import com.example.se306project1.utilities.CartState;
import com.example.se306project1.utilities.UserState;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<CartProduct> cartProducts;

    ViewHolder viewHolder;

    Drawer drawer;
    ProductSearcher productSearcher;

    class ViewHolder {
        private final RecyclerView cartProductRecyclerView = findViewById(R.id.cart_product_recyclerview);
        private final TextView totalPriceTextview = findViewById(R.id.total_price_textview);
        private final CheckBox selectAllCheckBox = findViewById(R.id.select_all_checkbox);
    }

    public static void start(AppCompatActivity activity) {
        Intent intent = new Intent(activity.getBaseContext(), CartActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        viewHolder = new ViewHolder();
        this.cartProducts = new ArrayList<>();
        this.drawer = new Drawer(this);
        this.productSearcher = new ProductSearcher(this);

//        this.fillProducts();
//        this.setAdapter(this.cartProducts);
        fetchAndRender();

        this.drawer.initialise();
        this.productSearcher.initialise();

    }

    public void fetchAndRender(){
        ProductDatabase db = ProductDatabase.getInstance();
        db.getAllProducts(new FireStoreCallback() {
            @Override
            public <T> void Callback(T value) {
                List<IProduct> products = (List<IProduct>) value;
                CartDatabase cdb = CartDatabase.getInstance();
                cdb.getUsersCartProducts(new FireStoreCallback() {
                    @Override
                    public <T> void Callback(T value) {
                        List<CartProduct> res = (List<CartProduct>) value;
                        cartProducts.clear();
                        cartProducts.addAll(res);
                        setAdapter(res);
                        CartState.getCartState().setCartList(res);
                    }
                }, UserState.getInstance().getCurrentUser().getUsername(),products);
            }
        });
    }

    public void setAdapter(List<CartProduct> cartProducts) {
        CartProductAdapter cartProductAdapter = new CartProductAdapter(cartProducts, this.viewHolder.totalPriceTextview, this.viewHolder.selectAllCheckBox);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        this.viewHolder.cartProductRecyclerView.setLayoutManager(layoutManager);
        this.viewHolder.cartProductRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.viewHolder.cartProductRecyclerView.setAdapter(cartProductAdapter);
    }

    public void fillProducts() {
        this.cartProducts = CartState.getCartState().getCartProducts();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return this.drawer.setUp(item, super.onOptionsItemSelected(item));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return this.productSearcher.onCreateOptionsMenu(menu, super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return this.drawer.onNavigationItemSelected(item, true);
    }

    public void selectAll(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()) {
            CartState.getCartState().checkAll();
        } else {
            CartState.getCartState().uncheckAll();
        }
        setAdapter(this.cartProducts);
        this.viewHolder.totalPriceTextview.setText("$" + CartState.getCartState().getPrice());
    }

    public void onGoBack(View view) {
        finish();
    }

}