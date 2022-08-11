package com.example.se306project1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.se306project1.R;
import com.example.se306project1.adapters.CategoryAdapter;
import com.example.se306project1.adapters.TopPickAdapter;
import com.example.se306project1.dataproviders.DataProvider;
import com.example.se306project1.models.ICategory;
import com.example.se306project1.models.IProduct;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private List<ICategory> categories;
    private ArrayList<IProduct> topProducts;

    ViewHolder viewHolder;

    Drawer drawer;
    ProductSearcher productSearcher;

    class ViewHolder {
        private final RecyclerView categoryRecyclerView = findViewById(R.id.category_recycler_view);
        private final RecyclerView topPickRecyclerView = findViewById(R.id.top_pick_product_recycler_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        this.viewHolder = new ViewHolder();
        this.categories = new ArrayList<>();
        this.topProducts = new ArrayList<>();
        this.drawer = new Drawer(this);
        this.productSearcher = new ProductSearcher(this);

        this.fillTopPicks();
        this.fillCategories();

        this.setCategoryAdapter();
        this.setTopProductAdapter();
        this.drawer.initialise();
        this.productSearcher.initialise();
    }

    public void setCategoryAdapter() {
        CategoryAdapter categoryAdapter = new CategoryAdapter(this.categories);
        RecyclerView.LayoutManager categoryLayoutManager = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        this.viewHolder.categoryRecyclerView.setLayoutManager(categoryLayoutManager);
        this.viewHolder.categoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.viewHolder.categoryRecyclerView.setAdapter(categoryAdapter);
    }

    public void setTopProductAdapter() {
        RecyclerView.LayoutManager topPickLayoutManager = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        this.viewHolder.topPickRecyclerView.setLayoutManager(topPickLayoutManager);
        this.viewHolder.topPickRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.viewHolder.topPickRecyclerView.setAdapter(new TopPickAdapter(this.topProducts));
    }

    public void fillCategories() {
        this.categories = DataProvider.getICategoryList();
    }

    public void fillTopPicks() {
        this.topProducts = DataProvider.getIProductList(5);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return this.drawer.setUp(item, super.onOptionsItemSelected(item));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return this.productSearcher.onCreateOptionsMenu(menu, super.onCreateOptionsMenu(menu));
    }
}
