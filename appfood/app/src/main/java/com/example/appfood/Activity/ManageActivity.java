package com.example.appfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.appfood.R;
import com.example.appfood.Ultil.Check;

public class ManageActivity extends AppCompatActivity {
    ImageView imgLogout;
    CardView qlProduct,qlOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        if (Check.haveNetworkConnection(getApplicationContext())){
            imgLogout = findViewById(R.id.imgLogout);
            imgLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            qlProduct = findViewById(R.id.qlProduct);
            qlProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageActivity.this,ProductManageActivity.class);
                    startActivity(intent);
                }
            });

            qlOrder = findViewById(R.id.qlOrder);
            qlOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageActivity.this,OrderManageActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            Check.ShowToast(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
        }
    }
}