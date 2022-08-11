package com.example.se306project1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se306project1.R;
import com.example.se306project1.models.CartProduct;

import java.util.ArrayList;
import java.util.List;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.CartProductViewHolder> {

    public class CartProductViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView amountTextView;
        private TextView priceTextView;
        private ImageView imageView;
        public CartProductViewHolder(final View view) {
            super(view);
            this.nameTextView = view.findViewById(R.id.cart_product_name_textview);
            this.amountTextView = view.findViewById(R.id.cart_product_amount_textview);
            this.priceTextView = view.findViewById(R.id.cart_product_price_textview);
            this.imageView = view.findViewById(R.id.cart_product_imageview);
        }
    }

    private List<CartProduct> products;

    public CartProductAdapter(List<CartProduct> products) {
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
        holder.nameTextView.setText(cartProduct.getName());
        holder.priceTextView.setText("$" + cartProduct.getPrice());
        holder.amountTextView.setText(Integer.toString(cartProduct.getAmount()));
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

}
