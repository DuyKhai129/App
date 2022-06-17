package com.example.appfood.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appfood.Model.Product;
import com.example.appfood.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DrinkAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayListDrink;

    public DrinkAdapter(Context context, ArrayList<Product> arrayListDrink) {
        this.context = context;
        this.arrayListDrink = arrayListDrink;
    }

    @Override
    public int getCount() {
        return arrayListDrink.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListDrink.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class Holder{
        TextView tvDrinkName,tvDrinkPrice,tvDrinkDescription;
        ImageView imgDrink;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if (view == null){
            holder = new Holder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_drink,null);
            holder.tvDrinkName = view.findViewById(R.id.tvDrinkName);
            holder.tvDrinkPrice = view.findViewById(R.id.tvDrinkPrice);
            holder.tvDrinkDescription = view.findViewById(R.id.tvDrinkDescription);
            holder.imgDrink = view.findViewById(R.id.imgDrink);
            view.setTag(holder);
        }else {
            holder = (Holder) view.getTag();
        }
        Product product = (Product) getItem(i);
        holder.tvDrinkName.setText(product.getpName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvDrinkPrice.setText(decimalFormat.format(product.getPrice()) + " Đ");
        holder.tvDrinkDescription.setMaxLines(2);
        holder.tvDrinkDescription.setEllipsize(TextUtils.TruncateAt.END);
        holder.tvDrinkDescription.setText(product.getDescription());
        Picasso.with(context).load(product.getImage())
                .placeholder(R.drawable.noimage).error(R.drawable.error)
                .into(holder.imgDrink);
        return view;
    }
}
