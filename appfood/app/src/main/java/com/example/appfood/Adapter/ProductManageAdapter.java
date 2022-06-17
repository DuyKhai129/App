package com.example.appfood.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appfood.Activity.DrinksActivity;
import com.example.appfood.Activity.ProductUpdateActivity;
import com.example.appfood.Model.Product;
import com.example.appfood.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductManageAdapter extends BaseAdapter {
    Activity context;
    ArrayList<Product> arrayListProductManage;

    public ProductManageAdapter(Activity context, ArrayList<Product> arrayListProductManage) {
        this.context = context;
        this.arrayListProductManage = arrayListProductManage;
    }

    @Override
    public int getCount() {
        return arrayListProductManage.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListProductManage.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class Holder{
        ImageView image;
        TextView tvName,tvPrice,tvDescription;
        Button btEdit;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if (view == null){
            holder = new Holder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lisview_product_manage,null);
            holder.image = view.findViewById(R.id.image);
            holder.tvName = view.findViewById(R.id.tvName);
            holder.tvPrice = view.findViewById(R.id.tvPrice);
            holder.tvDescription = view.findViewById(R.id.tvDescription);
            holder.btEdit = view.findViewById(R.id.btEdit);
            view.setTag(holder);
        }else {
            holder = (Holder) view.getTag();
        }
        Product product = (Product) getItem(i);
        holder.tvName.setText(product.getpName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvPrice.setText(decimalFormat.format(product.getPrice()) + " Đ");
        holder.tvDescription.setMaxLines(3);
        holder.tvDescription.setEllipsize(TextUtils.TruncateAt.END);
        holder.tvDescription.setText(product.getDescription());
        Picasso.with(context).load(product.getImage())
                .placeholder(R.drawable.noimage).error(R.drawable.error)
                .into(holder.image);
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductUpdateActivity.class);
                intent.putExtra("idProduct",arrayListProductManage.get(i).getId());
                intent.putExtra("id",arrayListProductManage.get(i));
                context.startActivity(intent);
            }
        });

        return view;
    }
}
