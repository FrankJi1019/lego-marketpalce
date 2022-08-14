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
import android.widget.Button;
import android.widget.Toast;

import com.example.se306project1.R;
import com.example.se306project1.adapters.ProductAdapter;
import com.example.se306project1.database.FireStoreCallback;
import com.example.se306project1.database.LikesDatabase;
import com.example.se306project1.database.ProductDatabase;
import com.example.se306project1.models.IProduct;
import com.example.se306project1.utilities.UserState;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static ProductActivityState state = ProductActivityState.UNDEFINED;

    private SortState sortState = SortState.NO_SORT;
    private List<IProduct> defaultOrder = new ArrayList<>();
    private List<IProduct> products = new ArrayList<>();

    ViewHolder viewHolder;
    Drawer drawer;
    ProductSearcher productSearcher;

    class ViewHolder {
        private final RecyclerView productRecyclerView = findViewById(R.id.product_recycler_view);
        private final Button likeSortButton = findViewById(R.id.sort_by_likes_button);
        private final Button likeSortAscButton = findViewById(R.id.sort_by_likes_ascend_button);
        private final Button likeSortDscButton = findViewById(R.id.sort_by_likes_desend_button);
        private final Button priceSortButton = findViewById(R.id.sort_by_price_button);
        private final Button priceSortAscButton = findViewById(R.id.sort_by_price_ascend_button);
        private final Button priceSortDscButton = findViewById(R.id.sort_by_price_descend_button);
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
        this.drawer = new Drawer(this);
        this.productSearcher = new ProductSearcher(this);

        this.drawer.initialise();
        this.productSearcher.initialise();

        updateProductList();

        updateSortingButtonStyle();

    }

    @Override
    public void onResume() {
        super.onResume();
        updateProductList();
    }

    public void updateProductList() {
        if (state == ProductActivityState.THEME) {
            getSupportActionBar().setTitle(getIntent().getStringExtra("theme"));
            fetchAndRenderForCategory(getSupportActionBar().getTitle().toString().toLowerCase());
        } else if (state == ProductActivityState.LIKE) {
            getSupportActionBar().setTitle("Your Likes");
            fetchAndRenderForLikes(UserState.getInstance().getCurrentUser().getUsername());
        } else if (state == ProductActivityState.SEARCH) {
            getSupportActionBar().setTitle(
                    String.format("Items related to \"%s\"", getIntent().getStringExtra("keyword"))
            );
            String keyword = getSupportActionBar().getTitle().toString()
                    .replace("Items related to ", "")
                    .replaceAll("\"", "");
            fetchAndRenderForSearing(keyword);
        }
    }

    public void setProductAdapter(List<IProduct> list) {
        ProductAdapter productAdapter;
        if (state == ProductActivityState.SEARCH) {
            String keyword = getSupportActionBar().getTitle().toString()
                    .replace("Items related to ", "")
                    .replaceAll("\"", "");
            productAdapter = new ProductAdapter(this, list, keyword);
        } else {
            productAdapter = new ProductAdapter(this, list);
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        this.viewHolder.productRecyclerView.setLayoutManager(layoutManager);
        this.viewHolder.productRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.viewHolder.productRecyclerView.setAdapter(productAdapter);
    }

    public void fetchAndRenderForCategory(String categoryTitle) {
        ProductDatabase db = ProductDatabase.getInstance();
        db.getAllProductsByCategoryTitle(new FireStoreCallback() {
            @Override
            public <T> void Callback(T value) {
                List<IProduct> res = (List<IProduct>) value;
                setProductAdapter(res);
                products.addAll(res);
                defaultOrder.addAll(res);
            }
        }, categoryTitle);
    }

    public void fetchAndRenderForSearing(String keyword) {
        ProductDatabase db = ProductDatabase.getInstance();
        db.getAllProducts(new FireStoreCallback() {
            @Override
            public <T> void Callback(T value) {
                List<IProduct> res = (List<IProduct>) value;
                setProductAdapter(res);
                products.addAll(res);
                defaultOrder.addAll(res);
            }
        });
    }

    public void fetchAndRenderForLikes(String userName) {
        ProductDatabase db = ProductDatabase.getInstance();
        db.getAllProducts(new FireStoreCallback() {
            @Override
            public <T> void Callback(T value) {
                LikesDatabase ldb = LikesDatabase.getInstance();
                ldb.getUsersAllLikes(new FireStoreCallback() {
                    @Override
                    public <T> void Callback(T value) {
                        List<IProduct> tt = (List<IProduct>) value;
                        setProductAdapter(tt);
                        products.addAll(tt);
                        defaultOrder.addAll(tt);
                    }
                }, userName, (List<IProduct>) value);
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

    public void onGoBack(View view) {
        finish();
    }

    public void onSortClick(View view) {
        updateSortState(((Button) view).getText().toString().toLowerCase().equals("likes"));
        updateSortingButtonStyle();
    }

    private void updateSortState(boolean isLikeClicked) {
        if (isLikeClicked) {
            switch (sortState) {
                case LIKE_DESCEND:
                    sortState = SortState.LIKE_ASCEND;
                    break;
                case LIKE_ASCEND:
                    sortState = SortState.NO_SORT;
                    break;
                default:
                    sortState = SortState.LIKE_DESCEND;
            }
        } else {
            switch (sortState) {
                case PRICE_ASCEND:
                    sortState = SortState.PRICE_DESCEND;
                    break;
                case PRICE_DESCEND:
                    sortState = SortState.NO_SORT;
                    break;
                default:
                    sortState = SortState.PRICE_ASCEND;
            }
        }
    }

    private void updateSortingButtonStyle() {
        this.viewHolder.likeSortButton.setVisibility(View.INVISIBLE);
        this.viewHolder.likeSortAscButton.setVisibility(View.INVISIBLE);
        this.viewHolder.likeSortDscButton.setVisibility(View.INVISIBLE);
        this.viewHolder.priceSortButton.setVisibility(View.INVISIBLE);
        this.viewHolder.priceSortAscButton.setVisibility(View.INVISIBLE);
        this.viewHolder.priceSortDscButton.setVisibility(View.INVISIBLE);
        switch (sortState) {
            case NO_SORT:
                this.viewHolder.likeSortButton.setVisibility(View.VISIBLE);
                this.viewHolder.priceSortButton.setVisibility(View.VISIBLE);
                break;
            case LIKE_ASCEND:
                this.viewHolder.likeSortAscButton.setVisibility(View.VISIBLE);
                this.viewHolder.priceSortButton.setVisibility(View.VISIBLE);
                break;
            case LIKE_DESCEND:
                this.viewHolder.likeSortDscButton.setVisibility(View.VISIBLE);
                this.viewHolder.priceSortButton.setVisibility(View.VISIBLE);
                break;
            case PRICE_ASCEND:
                this.viewHolder.likeSortButton.setVisibility(View.VISIBLE);
                this.viewHolder.priceSortAscButton.setVisibility(View.VISIBLE);
                break;
            case PRICE_DESCEND:
                this.viewHolder.likeSortButton.setVisibility(View.VISIBLE);
                this.viewHolder.priceSortDscButton.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void sortProductList() {
        switch (sortState) {
            case NO_SORT:
                this.products.clear();
                this.products.addAll(defaultOrder);
                break;
            case LIKE_ASCEND:
                LikesDatabase.getInstance().sortAscendByLikes(this.products);
                break;
            case LIKE_DESCEND:
                LikesDatabase.getInstance().sortDescendByLikes(this.products);
                break;
            case PRICE_ASCEND:
                LikesDatabase.getInstance().sortAscendByPrice(this.products);
                break;
            case PRICE_DESCEND:
                LikesDatabase.getInstance().sortDescendByPrice(this.products);
                break;
        }
    }

}

enum ProductActivityState {
    UNDEFINED, THEME, LIKE, SEARCH
}

enum SortState {
    NO_SORT, LIKE_ASCEND, LIKE_DESCEND, PRICE_ASCEND, PRICE_DESCEND
}
