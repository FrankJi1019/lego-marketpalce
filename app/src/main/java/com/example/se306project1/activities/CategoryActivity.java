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
import android.widget.ProgressBar;

import com.example.se306project1.R;
import com.example.se306project1.adapters.CategoryAdapter;
import com.example.se306project1.adapters.TopPickAdapter;
import com.example.se306project1.database.FireStoreCallback;
import com.example.se306project1.database.LikesDatabase;
import com.example.se306project1.models.TechnicTheme;
import com.example.se306project1.models.StarWarTheme;
import com.example.se306project1.models.CityTheme;
import com.example.se306project1.models.ICategory;
import com.example.se306project1.models.IProduct;
import com.example.se306project1.utilities.ActivityState;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<ICategory> categories;

    Drawer drawer;
    ProductSearcher productSearcher;
    ViewHolder viewHolder;

    class ViewHolder {
        private final RecyclerView categoryRecyclerView = findViewById(R.id.category_recycler_view);
        private final RecyclerView topPickRecyclerView = findViewById(R.id.top_pick_product_recycler_view);
        ProgressBar topPickProgressbar = findViewById(R.id.top_pick_progressbar);;
    }

    public static void start(AppCompatActivity activity) {
        Intent intent = new Intent(activity.getBaseContext(), CategoryActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ActivityState.getInstance().setCurrentActivity(this);

        this.viewHolder = new ViewHolder();
        this.categories = new ArrayList<>();
        this.drawer = new Drawer(this);
        this.productSearcher = new ProductSearcher(this);

        this.fillTopPicks(4);
        this.fillCategories();

        this.setCategoryAdapter();
        this.drawer.initialise();
        this.productSearcher.initialise();
    }

    private void setCategoryAdapter() {
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, this.categories);
        RecyclerView.LayoutManager categoryLayoutManager = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        this.viewHolder.categoryRecyclerView.setLayoutManager(categoryLayoutManager);
        this.viewHolder.categoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.viewHolder.categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void setTopProductAdapter(List<IProduct> list) {
        RecyclerView.LayoutManager topPickLayoutManager = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        this.viewHolder.topPickRecyclerView.setLayoutManager(topPickLayoutManager);
        this.viewHolder.topPickRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.viewHolder.topPickRecyclerView.setAdapter(new TopPickAdapter(this, list));
        this.viewHolder.topPickProgressbar.setVisibility(View.GONE);
        this.viewHolder.topPickRecyclerView.setVisibility(View.VISIBLE);
    }

    private void fillCategories() {
        this.categories.add(new TechnicTheme());
        this.categories.add(new StarWarTheme());
        this.categories.add(new CityTheme());
    }

    private void fillTopPicks(int size) {
        LikesDatabase likesDatabase = LikesDatabase.getInstance();
        likesDatabase.getAllProducts(new FireStoreCallback() {
            @Override
            public <T> void Callback(T value) {
                List<IProduct> products = (List<IProduct>) value;
                likesDatabase.sortDescendByLikes(products);
                List<IProduct> res = new ArrayList<>();
                for(int i=0;i<size;i++){
                    res.add(products.get(i));
                }
                setTopProductAdapter(res);
            }
        });
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

}
