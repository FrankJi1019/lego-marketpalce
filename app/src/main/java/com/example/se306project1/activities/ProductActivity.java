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

import com.example.se306project1.R;
import com.example.se306project1.adapters.ProductAdapter;
import com.example.se306project1.dataproviders.DataProvider;
import com.example.se306project1.dataproviders.ProductData;
import com.example.se306project1.models.IProduct;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static ProductActivityState state = ProductActivityState.UNDEFINED;

    private ArrayList<IProduct> products;

    ViewHolder viewHolder;
    Drawer drawer;
    ProductSearcher productSearcher;

    class ViewHolder {
        private final RecyclerView productRecyclerView = findViewById(R.id.product_recycler_view);
    }

    public static void start(AppCompatActivity activity) {
        Intent thisIntent = new Intent(activity.getBaseContext(), ProductActivity.class);
        activity.startActivity(thisIntent);
    }

    public static void startWithTheme(AppCompatActivity activity, String theme) {
        state = ProductActivityState.THEME;
        Intent thisIntent = new Intent(activity.getBaseContext(), ProductActivity.class);
        thisIntent.putExtra("theme", theme);
        activity.startActivity(thisIntent);
    }

    public static void startWithLikes(AppCompatActivity activity) {
        state = ProductActivityState.LIKE;
        Intent thisIntent = new Intent(activity.getBaseContext(), ProductActivity.class);
        activity.startActivity(thisIntent);
    }

    public static void startWithSearch(AppCompatActivity activity, String keyword) {
        state = ProductActivityState.SEARCH;
        Intent thisIntent = new Intent(activity.getBaseContext(), ProductActivity.class);
        thisIntent.putExtra("keyword", keyword);
        activity.startActivity(thisIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        this.viewHolder = new ViewHolder();
        this.products = new ArrayList<>();
        this.drawer = new Drawer(this);
        this.productSearcher = new ProductSearcher(this);

        this.fillProducts();

        this.setProductAdapter();
        this.drawer.initialise();
        this.productSearcher.initialise();

        if (state == ProductActivityState.THEME) {
            getSupportActionBar().setTitle(getIntent().getStringExtra("theme"));
        } else if (state == ProductActivityState.LIKE) {
            getSupportActionBar().setTitle("Your Likes");
        } else if (state == ProductActivityState.SEARCH) {
            getSupportActionBar().setTitle(
                    String.format("Items related to \"%s\"", getIntent().getStringExtra("keyword"))
            );
        }
    }

    public void setProductAdapter() {
        ProductAdapter productAdapter = new ProductAdapter(this.products);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        this.viewHolder.productRecyclerView.setLayoutManager(layoutManager);
        this.viewHolder.productRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.viewHolder.productRecyclerView.setAdapter(productAdapter);
    }

    public void fillProducts() {
        this.products = DataProvider.getIProductList(10);
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

    public void onProductItemClick(View view) {
        DetailActivity.start(this);
    }

    public void onGoBack(View view) {
        finish();
    }

    public void onSortClick(View view) {
        ((MaterialButton) view).setIconTintResource(R.color.orange_100);
        ((MaterialButton) view).setIconResource(R.drawable.outline_favorite_border_24);
    }

}

enum ProductActivityState {
    UNDEFINED, THEME, LIKE, SEARCH
}
