package com.example.appfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.example.appfood.R;
import com.example.appfood.Ultil.Check;

public class ContactActivity extends AppCompatActivity {
    Toolbar toolbarContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        if (Check.haveNetworkConnection(getApplicationContext())){
            toolbarContact = findViewById(R.id.toolbarContact);
            actionToolbar();
        }else{
            Check.ShowToast(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
        }
    }

    private void actionToolbar() {
        toolbarContact.setNavigationIcon(R.drawable.goback);
        toolbarContact.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}