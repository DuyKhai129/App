package com.example.appfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.Activity.CartActivity;
import com.example.appfood.Activity.HomeActivity;
import com.example.appfood.Model.Cart;
import com.example.appfood.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<Cart> arrayListCart;

    public CartAdapter(Context context, ArrayList<Cart> arrayListCart) {
        this.context = context;
        this.arrayListCart = arrayListCart;
    }


    @Override
    public int getCount() {
        return arrayListCart.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListCart.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class Holder{
        TextView tvName,tvPrice;
        ImageView imageCart;
        Button btMinus,btValues,btPlus;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if (view == null){
            holder = new Holder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.listview_cart, null);
            holder.tvName = view.findViewById(R.id.tvName);
            holder.tvPrice = view.findViewById(R.id.tvPrice);
            holder.imageCart = view.findViewById(R.id.imageCart);
            holder.btMinus = view.findViewById(R.id.btMinus);
            holder.btValues = view.findViewById(R.id.btValues);
            holder.btPlus = view.findViewById(R.id.btPlus);
            view.setTag(holder);
        }else {
            holder = (Holder) view.getTag();
        }
        Cart cart = (Cart) getItem(i);
        holder.tvName.setText(cart.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvPrice.setText(decimalFormat.format(cart.getPrice()) + " Đ");
        Picasso.with(context).load(cart.getImage())
                .placeholder(R.drawable.noimage).error(R.drawable.error)
                .into(holder.imageCart);
        holder.btValues.setText(cart.getQuantity() + "");

        int quantity = Integer.parseInt(holder.btValues.getText().toString());
        if (quantity >= 100){
            holder.btPlus.setVisibility(View.INVISIBLE);
            holder.btMinus.setVisibility(View.VISIBLE);
        }else if (quantity <= 1){
            holder.btMinus.setVisibility(View.INVISIBLE);
        }else if (quantity >= 1){
            holder.btMinus.setVisibility(View.VISIBLE);
            holder.btPlus.setVisibility(View.VISIBLE);
        }
        Holder finalHolder = holder;
        holder.btPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantityNew = Integer.parseInt(finalHolder.btValues.getText().toString()) + 1;
                int currentQuantity = HomeActivity.arrayListCart.get(i).getQuantity();
                long priceQuantity = HomeActivity.arrayListCart.get(i).getPrice();
                HomeActivity.arrayListCart.get(i).setQuantity(quantityNew);
                long priceNew = (priceQuantity * quantityNew) / currentQuantity;
                HomeActivity.arrayListCart.get(i).setPrice(priceNew);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalHolder.tvPrice.setText(decimalFormat.format(priceNew) + " Đ");
                CartActivity.eventGetData();
                if (quantityNew > 99){
                    finalHolder.btPlus.setVisibility(View.INVISIBLE);
                    finalHolder.btMinus.setVisibility(View.VISIBLE);
                    finalHolder.btValues.setText(String.valueOf(quantityNew));
                }else {
                    finalHolder.btMinus.setVisibility(View.VISIBLE);
                    finalHolder.btPlus.setVisibility(View.VISIBLE);
                    finalHolder.btValues.setText(String.valueOf(quantityNew));
                }
            }
        });
        holder.btMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantityNew = Integer.parseInt(finalHolder.btValues.getText().toString()) - 1;
                int currentQuantity = HomeActivity.arrayListCart.get(i).getQuantity();
                long priceQuantity = HomeActivity.arrayListCart.get(i).getPrice();
                HomeActivity.arrayListCart.get(i).setQuantity(quantityNew);
                long priceNew = (priceQuantity * quantityNew) / currentQuantity;
                HomeActivity.arrayListCart.get(i).setPrice(priceNew);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalHolder.tvPrice.setText(decimalFormat.format(priceNew) + " Đ");
                CartActivity.eventGetData();
                if (quantityNew < 2){
                    finalHolder.btMinus.setVisibility(View.INVISIBLE);
                    finalHolder.btPlus.setVisibility(View.VISIBLE);
                    finalHolder.btValues.setText(String.valueOf(quantityNew));
                }else {
                    finalHolder.btMinus.setVisibility(View.VISIBLE);
                    finalHolder.btPlus.setVisibility(View.VISIBLE);
                    finalHolder.btValues.setText(String.valueOf(quantityNew));
                }
            }
        });

        return view;
    }
}

