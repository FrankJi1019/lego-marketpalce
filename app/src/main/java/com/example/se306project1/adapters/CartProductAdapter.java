package com.example.se306project1.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se306project1.R;
import com.example.se306project1.database.CartDatabase;
import com.example.se306project1.models.CartProduct;
import com.example.se306project1.utilities.CartState;
import com.example.se306project1.utilities.UserState;

import java.util.List;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.CartProductViewHolder> {

    public class CartProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView, amountTextView, priceTextView;
        private final ImageView imageView;
        private final Button decreaseAmountButton, increaseAmountButton, deleteButton;
        private final CardView cartContainer;
        private final CheckBox checkBox;

        public CartProductViewHolder(final View view) {
            super(view);
            this.nameTextView = view.findViewById(R.id.cart_product_name_textview);
            this.amountTextView = view.findViewById(R.id.cart_product_amount_textview);
            this.priceTextView = view.findViewById(R.id.cart_product_price_textview);
            this.imageView = view.findViewById(R.id.cart_product_imageview);
            this.decreaseAmountButton = view.findViewById(R.id.decrease_amount_button);
            this.increaseAmountButton = view.findViewById(R.id.increase_amount_button);
            this.deleteButton = view.findViewById(R.id.delete_button);
            this.cartContainer = view.findViewById(R.id.cart_item_container);
            this.checkBox = view.findViewById(R.id.cart_product_checkbox);
        }
    }

    private final List<CartProduct> products;
    private final TextView totalPriceTextview;
    private final CheckBox selectAllCheckBox;

    public CartProductAdapter(List<CartProduct> products, TextView totalPriceTextview, CheckBox checkBox) {
        this.products = products;
        this.totalPriceTextview = totalPriceTextview;
        this.selectAllCheckBox = checkBox;
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
                updateAmount(view, holder.amountTextView, position, products.get(position).getAmount() - 1);
            }
        });
        holder.increaseAmountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAmount(view, holder.amountTextView, position, products.get(position).getAmount() + 1);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(position);
                notifyDataSetChanged();
            }
        });
        holder.imageView.setImageResource(cartProduct.getImages().get(0));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    CartState.getCartState().checkItem(cartProduct.getName());
                } else {
                    CartState.getCartState().uncheckItem(cartProduct.getName());
                }
                selectAllCheckBox.setChecked(CartState.getCartState().isAllChecked());
                updatePrice();
            }
        });
        holder.checkBox.setChecked(CartState.getCartState().isItemChecked(cartProduct.getName()));
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

    private void updatePrice() {
        totalPriceTextview.setText("$" + CartState.getCartState().getPrice());
    }

    private void updateAmount(View view, TextView amountTextView, int position, int amount) {
        CartProduct cartProduct = products.get(position);
        amount = Math.max(amount, 1);
        amount = Math.min(amount, cartProduct.getStock());
        amountTextView.setText(Integer.toString(amount));
        cartProduct.setAmount(amount);
        CartState.getCartState().updateAmount(cartProduct.getName(), amount);
        CartDatabase db = CartDatabase.getInstance();
        db.substractCartAmount(UserState.getInstance().getCurrentUser().getUsername(), cartProduct.getName());
        updatePrice();
    }

    private void deleteProduct(int position) {
        CartProduct cartProduct = products.get(position);
        CartState.getCartState().uncheckItem(cartProduct.getName());
        CartState.getCartState().removeCartProduct(cartProduct.getName());
        products.remove(position);
        updatePrice();
    }
}
