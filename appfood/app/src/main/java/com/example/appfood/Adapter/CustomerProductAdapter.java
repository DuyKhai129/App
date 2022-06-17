package com.example.appfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appfood.Activity.CustomerProductActivity;
import com.example.appfood.Model.Orders;
import com.example.appfood.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CustomerProductAdapter extends BaseAdapter {
    Context context;
    ArrayList<Orders> arrayListOder;

    public CustomerProductAdapter(Context context, ArrayList<Orders> arrayListOder) {
        this.context = context;
        this.arrayListOder = arrayListOder;
    }

    @Override
    public int getCount() {
        return arrayListOder.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListOder.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class Holder{
        TextView tvName,tvQuantity,tvPrice;
        ImageView image;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if (view == null){
            holder = new Holder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.listview_customer_product,null);
            holder.tvName = view.findViewById(R.id.tvName);
            holder.tvPrice = view.findViewById(R.id.tvPrice);
            holder.tvQuantity = view.findViewById(R.id.tvQuantity);
            holder.image = view.findViewById(R.id.image);
            view.setTag(holder);
        }else {
            holder = (Holder) view.getTag();
        }
        Orders oders = (Orders) getItem(i);
        holder.tvName.setText(oders.getNameDetails());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvPrice.setText(decimalFormat.format(oders.getTotalPrice()) + " Đ");
        holder.tvQuantity.setText(String.valueOf(oders.getQuantity()));
        Picasso.with(context).load(oders.getImage())
                .placeholder(R.drawable.noimage).error(R.drawable.error)
                .into(holder.image);
        CustomerProductActivity.totalPrice();
        return view;
    }
}
