package com.example.se306project1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.example.se306project1.R;
import com.example.se306project1.adapters.CategoryAdapter;
import com.example.se306project1.adapters.TopPickAdapter;
import com.example.se306project1.models.Category;
import com.example.se306project1.models.Category1;
import com.example.se306project1.models.Category2;
import com.example.se306project1.models.Category3;
import com.example.se306project1.models.ICategory;
import com.example.se306project1.models.IProduct;
import com.example.se306project1.models.Product;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private List<ICategory> categories;
    private ArrayList<IProduct> products;

    ViewHolder viewHolder;
    AppBarViewHolder appBarViewHolder;

    class ViewHolder {
        private final RecyclerView categoryRecyclerView = findViewById(R.id.category_recycler_view);
        private final RecyclerView topPickRecyclerView = findViewById(R.id.top_pick_product_recycler_view);
    }

    class AppBarViewHolder {
        private final Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        private final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        private final NavigationView navigationView = (NavigationView) findViewById(R.id.app_drawer_navigation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        viewHolder = new ViewHolder();
        appBarViewHolder = new AppBarViewHolder();
        this.categories = new ArrayList<>();
        this.products = new ArrayList<>();
        this.fillTopPicks();
        this.fillCategories();
        this.setAdapter();
        this.setAppBar();
    }

    public void setAdapter() {
        TopPickAdapter topPickAdapter = new TopPickAdapter(this.products);
        RecyclerView.LayoutManager topPickLayoutManager = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        this.viewHolder.topPickRecyclerView.setLayoutManager(topPickLayoutManager);
        this.viewHolder.topPickRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.viewHolder.topPickRecyclerView.setAdapter(topPickAdapter);

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


    public void fillCategories() {
        Category category1 = new Category1();
        Category category2 = new Category2();
        Category category3 = new Category3();

        category1.setId(1);
        category2.setId(2);
        category3.setId(3);

        category1.setTitle("Colosseum1");
        category2.setTitle("Colosseum2");
        category3.setTitle("Colosseum3");

        category1.setImage("image_placeholder.png");
        category1.setImage("image_placeholder.png");
        category1.setImage("image_placeholder.png");
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);

    }

    public void fillTopPicks() {
        Product product = new Product();
        product.setId(1);
        product.setCategoryId(1);
        product.setName("Colosseum");
        product.setDescription("Build and discover the Taj Mahal! The huge ivory-white marble mausoleum, renowned as one of the world’s architectural wonders, was commissioned in 1631 by the Emperor Shah Jahan in memory of his wife, the Empress Mumtaz Mahal. This relaunched 2008 LEGO® Creator Expert interpretation features the structure's 4 facades with sweeping arches, balconies and arched windows. The central dome, subsidiary domed chambers and surrounding minarets are topped with decorative finials, and the raised platform is lined with recessed arches.");
        product.setPrice(199.90);
        product.setStock(0);
        List<String> images = new ArrayList<>();
        images.add("image_placeholder.png");
        product.setImages(images);
        for (int i = 0; i < 5; i++) {
            this.products.add(product);
        }
    }

    // click event handler for the menu icon in the app bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                appBarViewHolder.drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setAppBar() {
        setSupportActionBar(appBarViewHolder.toolbar);
        ActionBar tb = getSupportActionBar();
        tb.setHomeAsUpIndicator(R.drawable.menu);
        tb.setTitle("MyMusic");
        tb.setDisplayHomeAsUpEnabled(true);

        // click event handler for the items in the navigation
        appBarViewHolder.navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        appBarViewHolder.drawerLayout.closeDrawers();
                        appBarViewHolder.drawerLayout.setSelected(true);
                        return true;
                    }
                });

        getSupportActionBar().setTitle(R.string.app_title);
    }

}
