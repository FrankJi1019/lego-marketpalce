package com.example.se306project1.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se306project1.R;
import com.example.se306project1.models.CartProduct;

import java.util.ArrayList;
import java.util.List;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.CartProductViewHolder> {

    public class CartProductViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView, amountTextView, priceTextView;
        private ImageView imageView;
        private Button decreaseAmountButton, increaseAmountButton;
        public CartProductViewHolder(final View view) {
            super(view);
            this.nameTextView = view.findViewById(R.id.cart_product_name_textview);
            this.amountTextView = view.findViewById(R.id.cart_product_amount_textview);
            this.priceTextView = view.findViewById(R.id.cart_product_price_textview);
            this.imageView = view.findViewById(R.id.cart_product_imageview);
            this.decreaseAmountButton = view.findViewById(R.id.decrease_amount_button);
            this.increaseAmountButton = view.findViewById(R.id.increase_amount_button);
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
    public void onBindViewHolder(@NonNull CartProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CartProduct cartProduct = this.products.get(position);
        holder.nameTextView.setText(cartProduct.getName());
        holder.priceTextView.setText("$" + cartProduct.getPrice());
        holder.amountTextView.setText(Integer.toString(cartProduct.getAmount()));
        holder.decreaseAmountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartProduct cartProduct = products.get(position);
                int newAmount = Math.max(cartProduct.getAmount() - 1, 1);
                holder.amountTextView.setText(Integer.toString(newAmount));
                cartProduct.setAmount(newAmount);
            }
        });
        holder.increaseAmountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartProduct cartProduct = products.get(position);
                int newAmount = Math.min(cartProduct.getAmount() + 1, cartProduct.getStock());
                holder.amountTextView.setText(Integer.toString(newAmount));
                cartProduct.setAmount(newAmount);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

}
