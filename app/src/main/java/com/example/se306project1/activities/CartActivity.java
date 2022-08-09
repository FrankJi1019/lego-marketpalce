package com.example.se306project1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.se306project1.R;
import com.example.se306project1.adapters.CartProductAdapter;
import com.example.se306project1.models.CartProduct;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ArrayList<CartProduct> cartProducts;

    ViewHolder viewHolder;

    class ViewHolder {
        private final RecyclerView cartProductRecyclerView = findViewById(R.id.cart_product_recyclerview);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        viewHolder = new ViewHolder();
        this.cartProducts = new ArrayList<>();
        this.fillProducts();
        this.setAdapter();
    }

    public void setAdapter() {
        CartProductAdapter cartProductAdapter = new CartProductAdapter(this.cartProducts);
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
        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(1);
        cartProduct.setCategoryId(1);
        cartProduct.setName("Colosseum");
        cartProduct.setDescription("Build and discover the Taj Mahal! The huge ivory-white marble mausoleum, renowned as one of the world’s architectural wonders, was commissioned in 1631 by the Emperor Shah Jahan in memory of his wife, the Empress Mumtaz Mahal. This relaunched 2008 LEGO® Creator Expert interpretation features the structure's 4 facades with sweeping arches, balconies and arched windows. The central dome, subsidiary domed chambers and surrounding minarets are topped with decorative finials, and the raised platform is lined with recessed arches.");
        cartProduct.setPrice(199.90);
        cartProduct.setStock(0);
        List<String> images = new ArrayList<>();
        images.add("image_placeholder.png");
        cartProduct.setImages(images);
        cartProduct.setAmount(1);
        for (int i = 0; i < 10; i++) {
            this.cartProducts.add(cartProduct);
        }
    }
}