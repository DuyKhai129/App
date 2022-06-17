package com.example.appfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appfood.Adapter.CartAdapter;
import com.example.appfood.R;
import com.example.appfood.Ultil.Check;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {
    ListView lvCart;
    TextView textViewNotification;
    Button btPay,btBuyNow;
    static TextView tvTotal;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        if (Check.haveNetworkConnection(getApplicationContext())){
            anhXa();
            checkData();
            eventGetData();
            deleteProduct();
            eventPay();
        }else{
            Check.ShowToast(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
        }
    }

    private void eventPay() {
        btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HomeActivity.arrayListCart.size() > 0){
                    Intent intent = new Intent(getApplicationContext(),CustomerActivity.class);
                    startActivity(intent);
                }
            }
        });
        btBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(intent);
            }
        });
    }

    private void deleteProduct() {
        lvCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setMessage("Bạn có  muốn xóa hay ko ...?");
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (HomeActivity.arrayListCart.size() <= 0){
                            textViewNotification.setVisibility(View.VISIBLE);
                            btPay.setVisibility(View.INVISIBLE);
                            btBuyNow.setVisibility(View.VISIBLE);
                        }else {
                            HomeActivity.arrayListCart.remove(position);
                            cartAdapter.notifyDataSetChanged();
                            eventGetData();

                            if (HomeActivity.arrayListCart.size() <= 0){
                                textViewNotification.setVisibility(View.VISIBLE);
                                btBuyNow.setVisibility(View.VISIBLE);
                                btPay.setVisibility(View.INVISIBLE);
                            }else {
                                textViewNotification.setVisibility(View.INVISIBLE);
                                btBuyNow.setVisibility(View.INVISIBLE);
                                btPay.setVisibility(View.VISIBLE);
                                cartAdapter.notifyDataSetChanged();
                                eventGetData();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cartAdapter.notifyDataSetChanged();
                        eventGetData();
                    }
                });
                builder.show();
                return false;
            }
        });
    }

    public static void eventGetData() {
        long totalPrice = 0;
        for (int i = 0;i<HomeActivity.arrayListCart.size();i++){
            totalPrice += HomeActivity.arrayListCart.get(i).getPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTotal.setText(decimalFormat.format(totalPrice) + " Đ");
    }

    private void checkData() {
        if (HomeActivity.arrayListCart.size() <= 0){
            cartAdapter.notifyDataSetChanged();
            textViewNotification.setVisibility(View.VISIBLE);
            lvCart.setVisibility(View.INVISIBLE);
            btBuyNow.setVisibility(View.VISIBLE);
        }else {
            cartAdapter.notifyDataSetChanged();
            textViewNotification.setVisibility(View.INVISIBLE);
            lvCart.setVisibility(View.VISIBLE);
            btBuyNow.setVisibility(View.INVISIBLE);

        }
    }

    private void anhXa() {
        lvCart = findViewById(R.id.lvCart);
        textViewNotification = findViewById(R.id.textViewNotification);
        btPay = findViewById(R.id.btPay);
        btBuyNow = findViewById(R.id.btBuyNow);
        tvTotal = findViewById(R.id.tvTotal);
        cartAdapter = new CartAdapter(getApplicationContext(),HomeActivity.arrayListCart);
        lvCart.setAdapter(cartAdapter);

    }
}