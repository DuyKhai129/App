package com.example.appfood.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appfood.Activity.CustomerProductActivity;
import com.example.appfood.Model.Customer;
import com.example.appfood.R;

import java.util.ArrayList;

public class CustomerAdapter extends BaseAdapter {
    Activity context;
    ArrayList<Customer> arrayListCustomer;

    public CustomerAdapter(Activity context, ArrayList<Customer> arrayListCustomer) {
        this.context = context;
        this.arrayListCustomer = arrayListCustomer;
    }

    @Override
    public int getCount() {
        return arrayListCustomer.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListCustomer.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class Holder{
        TextView tvName,tvPhone,tvAddress;
        ImageView image,imgDetails;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if (view == null){
            holder = new Holder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.listview_customer,null);
            holder.image = view.findViewById(R.id.image);
            holder.tvName = view.findViewById(R.id.tvName);
            holder.tvPhone = view.findViewById(R.id.tvPhone);
            holder.tvAddress = view.findViewById(R.id.tvAddress);
            holder.imgDetails = view.findViewById(R.id.imgDetails);
            view.setTag(holder);
        }else {
            holder = (Holder) view.getTag();
        }
        Customer customer = (Customer) getItem(i);
        holder.tvName.setText(customer.getName());
        holder.tvPhone.setText(String.valueOf(customer.getPhone()));
        holder.tvAddress.setText(customer.getAddress());
        holder.imgDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CustomerProductActivity.class);
                intent.putExtra("idCustomer", arrayListCustomer.get(i).getId());
                context.startActivity(intent);
            }
        });
        return view;
    }
}
