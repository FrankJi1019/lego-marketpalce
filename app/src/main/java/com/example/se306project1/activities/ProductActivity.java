package com.example.se306project1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.se306project1.R;
import com.example.se306project1.adapters.ProductAdapter;
import com.example.se306project1.models.IProduct;
import com.example.se306project1.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private ArrayList<IProduct> products;

    ViewHolder viewHolder;

    class ViewHolder {
        private final RecyclerView productRecyclerView = findViewById(R.id.product_recycler_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        viewHolder = new ViewHolder();
        this.products = new ArrayList<>();
        this.fillProducts();
        this.setAdapter();
    }

    public void setAdapter() {
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
        for (int i = 0; i < 10; i++) {
            this.products.add(product);
        }
    }

}