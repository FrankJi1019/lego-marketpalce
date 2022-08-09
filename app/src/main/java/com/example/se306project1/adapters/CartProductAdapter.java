package com.example.se306project1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se306project1.R;
import com.example.se306project1.models.CartProduct;

import java.util.ArrayList;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.CartProductViewHolder> {

    public class CartProductViewHolder extends RecyclerView.ViewHolder {
        private TextView cartProductNameTextView;
        public CartProductViewHolder(final View view) {
            super(view);
            this.cartProductNameTextView = view.findViewById(R.id.cart_product_name_textview);
        }
    }

    private ArrayList<CartProduct> products;

    public CartProductAdapter(ArrayList<CartProduct> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public CartProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cartProductListItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_product_list_item, parent, false);
        return new CartProductViewHolder(cartProductListItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CartProductViewHolder holder, int position) {
        CartProduct cartProduct = this.products.get(position);
        holder.cartProductNameTextView.setText(cartProduct.getName());
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

}
