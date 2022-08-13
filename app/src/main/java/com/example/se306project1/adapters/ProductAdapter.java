package com.example.se306project1.adapters;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se306project1.R;
import com.example.se306project1.activities.DetailActivity;
import com.example.se306project1.database.FireStoreCallback;
import com.example.se306project1.database.LikesDatabase;
import com.example.se306project1.database.ProductDatabase;
import com.example.se306project1.models.IProduct;
import com.example.se306project1.models.Product;
import com.example.se306project1.utilities.UserState;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView productNameTextview,price_textview;
        private TextView inStockTextview, lowStockTextview, noStockTextview;
        private MaterialButton likeButton, unlikeButton;
        private ImageView product_image;
        private CardView container;
        public ProductViewHolder(final View view) {
            super(view);
            this.productNameTextview = view.findViewById(R.id.product_name_textview);
            this.likeButton = view.findViewById(R.id.like_button);
            this.unlikeButton = view.findViewById(R.id.unlike_button);
            this.product_image = view.findViewById(R.id.product_imageview);
            this.price_textview = view.findViewById(R.id.price_textview);
            this.container = view.findViewById(R.id.product_item_container);
            this.inStockTextview = view.findViewById(R.id.in_stock_textview);
            this.lowStockTextview = view.findViewById(R.id.low_stock_textview);
            this.noStockTextview = view.findViewById(R.id.no_stock_textview);
        }
    }

    private List<IProduct> products;
    private AppCompatActivity activity;

    public ProductAdapter(AppCompatActivity activity, List<IProduct> products) {
        this.activity = activity;
        this.products = products;
    }

    public ProductAdapter(AppCompatActivity activity, List<IProduct> products, String keyword) {
        this.activity = activity;
        ProductDatabase db = ProductDatabase.getInstance();
        this.products = db.getProductsBySearch(products,keyword);
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productListItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);
        return new ProductViewHolder(productListItem);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        IProduct product = this.products.get(position);
        holder.productNameTextview.setText(product.getName());
        holder.likeButton.setOnClickListener(view -> {
            view.setVisibility(View.INVISIBLE);
            holder.unlikeButton.setVisibility(View.VISIBLE);
            LikesDatabase db = LikesDatabase.getInstance();
            db.addProductToLikesList(UserState.getInstance().getCurrentUser().getUsername(),product.getName());
        });
        holder.unlikeButton.setOnClickListener(view -> {
            view.setVisibility(View.INVISIBLE);
            holder.likeButton.setVisibility(View.VISIBLE);
            LikesDatabase db = LikesDatabase.getInstance();
            db.removeProductFromLikesList(UserState.getInstance().getCurrentUser().getUsername(),product.getName());
        });
        holder.product_image.setImageResource(product.getImages().get(0));
        holder.price_textview.setText("$"+product.getPrice());
        holder.container.setOnClickListener(view -> {
            DetailActivity.startWithName(this.activity, this.products.get(position).getName());
        });
        if (product.getStock() == 0) {
            holder.noStockTextview.setVisibility(View.VISIBLE);
        } else if (product.getStock() <= Product.LOW_STOCK_BOUNDARY) {
            holder.lowStockTextview.setVisibility(View.VISIBLE);
        } else {
            holder.inStockTextview.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

}
