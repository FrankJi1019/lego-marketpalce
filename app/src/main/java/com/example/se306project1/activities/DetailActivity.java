package com.example.se306project1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se306project1.R;
import com.example.se306project1.adapters.DetailAdapter;
import com.example.se306project1.database.FireStoreCallback;
import com.example.se306project1.database.ProductDatabase;
import com.example.se306project1.utilities.ActivityState;
import com.example.se306project1.utilities.CartState;
import com.example.se306project1.utilities.ContextState;
import com.example.se306project1.utilities.UserState;
import com.google.android.material.navigation.NavigationView;
import com.example.se306project1.models.IProduct;
import com.example.se306project1.models.Product;

import java.util.List;

public class DetailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    class ViewHolder {
        private final ViewPager viewPager = findViewById(R.id.viewPager);
        private final Button likeButton = findViewById(R.id.like_button);
        private final Button unlikeButton = findViewById(R.id.unlike_button);
        private final TextView name = findViewById(R.id.detail_name_textView);
        private final TextView stock = findViewById(R.id.stockNumber);
        private final TextView price = findViewById(R.id.price);
        private final TextView description = findViewById(R.id.description);
        private final LinearLayout dots = findViewById(R.id.dots);
    }

    private Drawable activeDot;
    private Drawable inactiveDot;
    private int dotsCount;
    private ImageView[] sliderDots;

    private IProduct product;

    private ViewHolder viewHolder;
    private Drawer drawer;
    private ProductSearcher productSearcher;

    public static void start(AppCompatActivity activity) {
        Intent intent = new Intent(activity.getBaseContext(), DetailActivity.class);
        activity.startActivity(intent);
    }

    public static void startWithName(AppCompatActivity activity, String name) {
        Intent intent = new Intent(activity.getBaseContext(), DetailActivity.class);
        intent.putExtra("name", name);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActivityState.getInstance().setCurrentActivity(this);
        ContextState.getInstance().setCurrentContext(getApplicationContext());

        this.product = new Product();
        this.viewHolder = new ViewHolder();
        this.drawer = new Drawer();
        this.productSearcher = new ProductSearcher();
        this.activeDot = ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot);
        this.inactiveDot = ContextCompat.getDrawable(getApplicationContext(), R.drawable.inactive_dot);

        this.drawer.initialise();
        this.productSearcher.initialise();

        ProductDatabase db = ProductDatabase.getInstance();
        db.getSpecificProduct(new FireStoreCallback() {
            @Override
            public <T> void Callback(T value) {
                product = (IProduct) value;
                renderProductInfo();
            }
        }, getIntent().getStringExtra("name"));

        this.setViewPager();

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

    public void onAddToCart(View view) {
        if (this.product.getStock() == 0) {
            Toast.makeText(this, "No stock", Toast.LENGTH_SHORT).show();
            return;
        }
        ProductDatabase productDatabase = ProductDatabase.getInstance();
        productDatabase.getSpecificProduct(new FireStoreCallback() {
            @Override
            public <T> void Callback(T value) {
                IProduct product = (Product) value;
                addToCartSuccess();
                CartState.getCartState().addToCart(product.toCartProduct());
            }
        }, product.getName());
    }

    private void addToCartSuccess() {
        Toast.makeText(this, "The lego is in your cart now", Toast.LENGTH_SHORT).show();
    }

    public void onToggleLike(View view) {
        view.setVisibility(View.INVISIBLE);
        if (view.getId() == R.id.unlike_button && UserState.getInstance().unlike(product.getName())) {
            this.viewHolder.likeButton.setVisibility(View.VISIBLE);
        } else if (UserState.getInstance().like(product.getName())) {
            this.viewHolder.unlikeButton.setVisibility(View.VISIBLE);
        }
    }

    public void renderProductInfo() {
        List<Integer> imageList = product.getImages();
        setViewPagerDots(imageList.size());
        DetailAdapter detailAdapter = new DetailAdapter(imageList);
        viewHolder.viewPager.setAdapter(detailAdapter);
        viewHolder.name.setText(product.getName());
        viewHolder.stock.setText(product.getStock() + "");
        viewHolder.price.setText("$" + product.getPrice());
        viewHolder.description.setText(product.getDescription());
        this.setLikeButtonState();
    }

    private void setViewPagerDots(int size) {
        dotsCount = size;
        sliderDots = new ImageView[size];
        for (int i = 0; i < dotsCount; i++) {
            sliderDots[i] = new ImageView(this);
            sliderDots[i].setImageDrawable(inactiveDot);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 0);
            viewHolder.dots.addView(sliderDots[i], params);
        }
        sliderDots[0].setImageDrawable(activeDot);
    }

    private void setLikeButtonState() {
        if (UserState.getInstance().hasLiked(this.product.getName())) {
            this.viewHolder.unlikeButton.setVisibility(View.VISIBLE);
            this.viewHolder.likeButton.setVisibility(View.INVISIBLE);
        } else {
            this.viewHolder.likeButton.setVisibility(View.VISIBLE);
            this.viewHolder.unlikeButton.setVisibility(View.INVISIBLE);
        }
    }

    private void setViewPager() {
        viewHolder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    sliderDots[i].setImageDrawable(inactiveDot);
                }
                sliderDots[position].setImageDrawable(activeDot);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
