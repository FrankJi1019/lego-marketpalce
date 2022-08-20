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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.se306project1.R;
import com.example.se306project1.adapters.ProductAdapter;
import com.example.se306project1.database.FireStoreCallback;
import com.example.se306project1.database.LikesDatabase;
import com.example.se306project1.database.ProductDatabase;
import com.example.se306project1.models.IProduct;
import com.example.se306project1.models.Product;
import com.example.se306project1.utilities.ActivityState;
import com.example.se306project1.utilities.AnimationFactory;
import com.example.se306project1.utilities.ContextState;
import com.example.se306project1.utilities.UserState;
import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ProductActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static ProductActivityState activityState = ProductActivityState.UNDEFINED;

    private SortState sortState = SortState.NO_SORT;
    private final List<IProduct> defaultOrder = new ArrayList<>();
    private final List<IProduct> products = new ArrayList<>();

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
        private final TextView noResultTextView = findViewById(R.id.no_search_result_message);
        private final ProgressBar productProgressbar = findViewById(R.id.product_progressbar);
    }

    public static void startWithTheme(AppCompatActivity activity, String theme) {
        activityState = ProductActivityState.THEME;
        Intent thisIntent = new Intent(activity.getBaseContext(), ProductActivity.class);
        thisIntent.putExtra("theme", theme);
        activity.startActivity(thisIntent);
    }

    public static void startWithLikes(AppCompatActivity activity) {
        activityState = ProductActivityState.LIKE;
        Intent thisIntent = new Intent(activity.getBaseContext(), ProductActivity.class);
        activity.startActivity(thisIntent);
    }

    public static void startWithSearch(AppCompatActivity activity, String keyword) {
        activityState = ProductActivityState.SEARCH;
        Intent thisIntent = new Intent(activity.getBaseContext(), ProductActivity.class);
        thisIntent.putExtra("keyword", keyword);
        activity.startActivity(thisIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ActivityState.getInstance().setCurrentActivity(this);
        ContextState.getInstance().setCurrentContext(getApplicationContext());

        this.products.clear();
        this.defaultOrder.clear();

        this.viewHolder = new ViewHolder();
        this.drawer = new Drawer();
        this.productSearcher = new ProductSearcher();

        this.drawer.initialise();
        this.productSearcher.initialise();

        updateProductList();
        updateSortingButtonStyle();

        this.viewHolder.noResultTextView.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onResume() {
        super.onResume();
        this.products.clear();
        this.defaultOrder.clear();
        updateProductList();
    }

    public void updateProductList() {
        if (activityState == ProductActivityState.THEME) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(getIntent().getStringExtra("theme"));
            fetchCategoryProducts(getSupportActionBar().getTitle().toString().toLowerCase());
        } else if (activityState == ProductActivityState.LIKE) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Your Likes");
            fetchLikedProducts(UserState.getInstance().getCurrentUser().getUsername());
        } else if (activityState == ProductActivityState.SEARCH) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(
                    String.format("Items related to \"%s\"", getIntent().getStringExtra("keyword"))
            );
            String keyword = getIntent().getStringExtra("keyword");
            fetchSearchingResults(keyword);
        }
    }

    public void setProductAdapter() {
        ProductAdapter productAdapter = new ProductAdapter(this.products);
        if (activityState == ProductActivityState.SEARCH) {
            String keyword = getIntent().getStringExtra("keyword");
            this.viewHolder.noResultTextView.setVisibility(View.VISIBLE);
            for (IProduct p: this.products) {
                if (p.getName().contains(keyword)) {
                    this.viewHolder.noResultTextView.setVisibility(View.INVISIBLE);
                }
            }
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        this.viewHolder.productRecyclerView.setLayoutManager(layoutManager);
        this.viewHolder.productRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.viewHolder.productRecyclerView.setAdapter(productAdapter);
        this.viewHolder.productProgressbar.setVisibility(View.GONE);
        this.viewHolder.productRecyclerView.setVisibility(View.VISIBLE);
        if (activityState == ProductActivityState.THEME) {
            this.viewHolder.productRecyclerView.startAnimation(
                    new AnimationFactory().getSlideFromBottomAnimation()
            );
        } else {
            this.viewHolder.productRecyclerView.startAnimation(
                    new AnimationFactory().getSlideFromLeftAnimation()
            );
        }
    }

    public void fetchCategoryProducts(String categoryTitle) {
        this.products.clear();
        ProductDatabase db = ProductDatabase.getInstance();
        db.getAllProductsByCategoryTitle(new FireStoreCallback() {
            @Override
            public <T> void Callback(T value) {
                products.clear();
                defaultOrder.clear();
                List<IProduct> res = (List<IProduct>) value;
                products.addAll(res);
                setProductAdapter();
                defaultOrder.addAll(res);
            }
        }, categoryTitle);
    }

    public void fetchSearchingResults(String keyword) {
        this.products.clear();
        ProductDatabase db = ProductDatabase.getInstance();
        db.getAllProducts(new FireStoreCallback() {
            @Override
            public <T> void Callback(T value) {
                products.clear();
                defaultOrder.clear();
                List<IProduct> res = (List<IProduct>) value;
                res.removeIf(p -> !p.getName().toLowerCase().contains(keyword.toLowerCase()));
                products.addAll(res);
                setProductAdapter();
                defaultOrder.addAll(res);
            }
        });
    }

    public void fetchLikedProducts(String userName) {
        this.products.clear();
        ProductDatabase productDatabase = ProductDatabase.getInstance();
        productDatabase.getAllProducts(new FireStoreCallback() {
            @Override
            public <T> void Callback(T value) {
                LikesDatabase likesDatabase = LikesDatabase.getInstance();
                likesDatabase.getUsersAllLikes(new FireStoreCallback() {
                    @Override
                    public <T> void Callback(T value) {
                        products.clear();
                        defaultOrder.clear();
                        List<IProduct> likedProducts = (List<IProduct>) value;
                        setProductAdapter();
                        products.addAll(likedProducts);
                        defaultOrder.addAll(likedProducts);
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
        sortProductList();
        setProductAdapter();
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
                this.sortProducts("likesNum", true);
                break;
            case LIKE_DESCEND:
                this.sortProducts("likesNum", false);
                break;
            case PRICE_ASCEND:
                this.sortProducts("price", true);
                break;
            case PRICE_DESCEND:
                this.sortProducts("price", false);
                break;
        }
    }

    private void sortProducts(String fieldName, boolean ascend) {
        this.products.sort(new Comparator<IProduct>() {
            @Override
            public int compare(IProduct productA, IProduct productB) {
                Class<Product> productClass = Product.class;
                try {
                    Field field = productClass.getDeclaredField(fieldName);
                    System.out.println("get the field");
                    field.setAccessible(true);
                    System.out.println("reset accessible");
                    return new BigDecimal(field.get(productA).toString())
                            .subtract(new BigDecimal(field.get(productB).toString()))
                            .intValue() * (ascend ? 1 : -1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

}

enum ProductActivityState {
    UNDEFINED, THEME, LIKE, SEARCH
}

enum SortState {
    NO_SORT, LIKE_ASCEND, LIKE_DESCEND, PRICE_ASCEND, PRICE_DESCEND
}
