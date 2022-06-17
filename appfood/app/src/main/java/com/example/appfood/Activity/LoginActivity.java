package com.example.appfood.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appfood.R;
import com.example.appfood.Ultil.Check;

public class LoginActivity extends AppCompatActivity {
    EditText extUser,extPass;
    Button btLogin,btCancel;
    TextView tvRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Check.haveNetworkConnection(getApplicationContext())){
        extUser  = findViewById(R.id.editTextUser);
        extPass = findViewById(R.id.editTextPass);
        btLogin = findViewById(R.id.buttonLogin);
        tvRegister = findViewById(R.id.tvRegister);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check.ShowToast(getApplicationContext(),"Chức năng này đăng cập nhập");
            }
        });

        btCancel = findViewById(R.id.buttonCancel);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = extUser.getText().toString();
                String pass = extPass.getText().toString();
                if (user.equalsIgnoreCase("Admin") && pass.equalsIgnoreCase("ad12345")){
                    Intent intent = new Intent(getApplicationContext(),ManageActivity.class);
                    startActivity(intent);
                }else {
                    Check.ShowToast(getApplicationContext(),"Tài khoản hoặc mật khẩu ko chính sắc");
                }
            }
        });
        }else{
            Check.ShowToast(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
        }
    }
}