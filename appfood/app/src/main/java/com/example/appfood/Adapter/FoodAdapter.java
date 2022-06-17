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

public class FoodAdapter extends BaseAdapter {

    Context context;
    ArrayList<Product> arrayListFood;

    public FoodAdapter(Context context, ArrayList<Product> arrayListFood) {
        this.context = context;
        this.arrayListFood = arrayListFood;
    }

    @Override
    public int getCount() {
        return arrayListFood.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListFood.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class Holder{
        TextView tvFoodName,tvFoodPrice,tvFoodDescription;
        ImageView imgFood;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if (view == null){
            holder = new Holder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_food,null);
            holder.tvFoodName = view.findViewById(R.id.tvFoodName);
            holder.tvFoodPrice = view.findViewById(R.id.tvFoodPrice);
            holder.tvFoodDescription = view.findViewById(R.id.tvFoodDescription);
            holder.imgFood = view.findViewById(R.id.imgFood);
            view.setTag(holder);
        }else {
            holder = (Holder) view.getTag();
        }
        Product product = (Product) getItem(i);
        holder.tvFoodName.setText(product.getpName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvFoodPrice.setText(decimalFormat.format(product.getPrice()) + " Đ");
        holder.tvFoodDescription.setMaxLines(2);
        holder.tvFoodDescription.setEllipsize(TextUtils.TruncateAt.END);
        holder.tvFoodDescription.setText(product.getDescription());
        Picasso.with(context).load(product.getImage())
                .placeholder(R.drawable.noimage).error(R.drawable.error)
                .into(holder.imgFood);
        return view;
    }
}
