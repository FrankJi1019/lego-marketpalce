package com.example.se306project1.adapters;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se306project1.R;
import com.example.se306project1.models.IProduct;
import com.example.se306project1.models.Product;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView productNameTextview;
        private MaterialButton likeButton, unlikeButton;
        public ProductViewHolder(final View view) {
            super(view);
            this.productNameTextview = view.findViewById(R.id.product_name_textview);
            this.likeButton = view.findViewById(R.id.like_button);
            this.unlikeButton = view.findViewById(R.id.unlike_button);
        }
    }

    private ArrayList<IProduct> products;

    public ProductAdapter(ArrayList<IProduct> products) {
        this.products = products;
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
        });
        holder.unlikeButton.setOnClickListener(view -> {
            view.setVisibility(View.INVISIBLE);
            holder.likeButton.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

}
