package com.example.appfood.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.Activity.CartActivity;
import com.example.appfood.Activity.HomeActivity;
import com.example.appfood.Model.Cart;
import com.example.appfood.Model.Product;
import com.example.appfood.R;
import com.example.appfood.Ultil.Check;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductNewAdapter extends RecyclerView.Adapter<ProductNewAdapter.productViewHolder>{
    Context context;
    ArrayList<Product> arrayListProductNew;

    public ProductNewAdapter(Context context, ArrayList<Product> arrayListProductNew) {
        this.context = context;
        this.arrayListProductNew = arrayListProductNew;
    }

    @NonNull
    @Override
    public productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foodnew,null);
        productViewHolder p = new productViewHolder(v);
        return p;
    }

    @Override
    public void onBindViewHolder(@NonNull productViewHolder holder, int position) {
        Product product = arrayListProductNew.get(position);
        holder.tvProductName.setText(product.getpName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvPrice.setText(decimalFormat.format(product.getPrice()) + " Đ");
        Picasso.with(context).load(product.getImage())
                .placeholder(R.drawable.noimage).error(R.drawable.error)
                .into(holder.imgProductNew);
    }

    @Override
    public int getItemCount() {
        return arrayListProductNew.size();
    }

    public class productViewHolder extends RecyclerView.ViewHolder{
         ImageView imgProductNew;
         TextView tvProductName,tvPrice;
        TextView tvName,tvPrices,tvDescription;
        Button btPlus,btValues,btMinus,btAddCard;
        ImageView imgDetails;
        public productViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProductNew = itemView.findViewById(R.id.imgProductNew);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(context);

                    dialog.setContentView(R.layout.details);


                    tvName = dialog.findViewById(R.id.tvName);
                    tvPrices = dialog.findViewById(R.id.tvPrice);
                    tvDescription = dialog.findViewById(R.id.tvDescription);
                    imgDetails = dialog.findViewById(R.id.imgDetails);
                    btMinus = dialog.findViewById(R.id.btMinus);
                    btPlus = dialog.findViewById(R.id.btPlus);
                    btValues = dialog.findViewById(R.id.btValues);
                    btAddCard = dialog.findViewById(R.id.btAddCard);

                    HomeActivity.id = arrayListProductNew.get(getPosition()).getId();
                    HomeActivity.idCategory = arrayListProductNew.get(getPosition()).getIdCategory();
                    HomeActivity.productName = arrayListProductNew.get(getPosition()).getpName();
                    HomeActivity.productPrice = arrayListProductNew.get(getPosition()).getPrice();
                    HomeActivity.productImage = arrayListProductNew.get(getPosition()).getImage();
                    HomeActivity.description = arrayListProductNew.get(getPosition()).getDescription();

                    tvName.setText(HomeActivity.productName);
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    tvPrices.setText(decimalFormat.format(HomeActivity.productPrice) + " Đ");
                    tvDescription.setText(HomeActivity.description);
                    Picasso.with(context).load(HomeActivity.productImage)
                            .placeholder(R.drawable.noimage).error(R.drawable.error)
                            .into(imgDetails);
                    int x = 1;
                    btValues.setText(x + "");
                    int quantity = Integer.parseInt(btValues.getText().toString());
                    if (quantity >= 100){
                        btPlus.setVisibility(View.INVISIBLE);
                        btMinus.setVisibility(View.VISIBLE);
                    }else if (quantity <= 2){
                        btMinus.setVisibility(View.INVISIBLE);
                    }else if (quantity >= 2){
                        btMinus.setVisibility(View.VISIBLE);
                        btPlus.setVisibility(View.VISIBLE);
                    }
                    btPlus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int quantityNew = Integer.parseInt(btValues.getText().toString()) + 1;
                            if (quantityNew > 99){
                                btPlus.setVisibility(View.INVISIBLE);
                                btMinus.setVisibility(View.VISIBLE);
                                btValues.setText(String.valueOf(quantityNew));
                            }else {
                                btMinus.setVisibility(View.VISIBLE);
                                btPlus.setVisibility(View.VISIBLE);
                                btValues.setText(String.valueOf(quantityNew));
                            }
                        }
                    });
                    btMinus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int quantityNew = Integer.parseInt(btValues.getText().toString()) - 1;
                            if (quantityNew < 2){
                                btMinus.setVisibility(View.INVISIBLE);
                                btPlus.setVisibility(View.VISIBLE);
                                btValues.setText(String.valueOf(quantityNew));
                            }else {
                                btMinus.setVisibility(View.VISIBLE);
                                btPlus.setVisibility(View.VISIBLE);
                                btValues.setText(String.valueOf(quantityNew));
                            }
                        }
                    });
                    btAddCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (HomeActivity.arrayListCart.size() > 0){
                                int addQuantity = Integer.parseInt(btValues.getText().toString());
                                boolean exist = false;
                                for (int i = 0;i<HomeActivity.arrayListCart.size();i++){
                                    if (HomeActivity.arrayListCart.get(i).getId() == HomeActivity.id){
                                        HomeActivity.arrayListCart.get(i).setQuantity(HomeActivity.arrayListCart.get(i).getQuantity() + addQuantity);
                                        if (HomeActivity.arrayListCart.get(i).getQuantity() >= 100){
                                            HomeActivity.arrayListCart.get(i).setQuantity(100);
                                        }
                                        HomeActivity.arrayListCart.get(i).setPrice(HomeActivity.productPrice + HomeActivity.arrayListCart.get(i).getQuantity());
                                        exist = true;
                                    }
                                }
                                if (exist == false){
                                    int quantity = Integer.parseInt(btValues.getText().toString());
                                    long totalPrice = quantity * HomeActivity.productPrice;
                                    HomeActivity.arrayListCart.add(new Cart(HomeActivity.id,HomeActivity.productName,totalPrice,HomeActivity.productImage,quantity));
                                }
                            }else {
                                int quantity = Integer.parseInt(btValues.getText().toString());
                                long totalPrice = quantity * HomeActivity.productPrice;
                                HomeActivity.arrayListCart.add(new Cart(HomeActivity.id,HomeActivity.productName,totalPrice,HomeActivity.productImage,quantity));
                            }
                            Check.ShowToast(context," Sản phẩm đã được thêm vào giỏ hàng");
                        }
                    });
                    dialog.show();
                }
            });

        }
    }
}
