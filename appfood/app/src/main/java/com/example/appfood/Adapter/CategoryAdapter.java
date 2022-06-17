package com.example.appfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appfood.Model.Category;
import com.example.appfood.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    ArrayList<Category> arrayListCategory;
    Context context;

    public CategoryAdapter(ArrayList<Category> arrayListCategory, Context context) {
        this.arrayListCategory = arrayListCategory;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListCategory.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListCategory.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class Holder{
        TextView tvCategory;
        ImageView imgCategory;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if (view == null){
            holder = new Holder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_category,null);
            holder.tvCategory = view.findViewById(R.id.tvCategory);
            holder.imgCategory = view.findViewById(R.id.imgCategory);
            view.setTag(holder);
        }else {
            holder = (Holder) view.getTag();
        }
        Category category = (Category) getItem(i);
        holder.tvCategory.setText(category.getName());
        Picasso.with(context).load(category.getImage()).placeholder(R.drawable.noimage)
                .error(R.drawable.error).into(holder.imgCategory);
        return view;
    }
}
