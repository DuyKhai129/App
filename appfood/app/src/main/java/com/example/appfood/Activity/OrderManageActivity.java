package com.example.appfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appfood.Adapter.CustomerAdapter;
import com.example.appfood.Model.Customer;
import com.example.appfood.R;
import com.example.appfood.Ultil.Check;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderManageActivity extends AppCompatActivity {
    Toolbar toolBarOrderManage;
    ListView lvOrderManage;
    ArrayList<Customer> customerArrayList;
    CustomerAdapter customerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manage);
        if (Check.haveNetworkConnection(getApplicationContext())){
            toolBarOrderManage = findViewById(R.id.toolBarOrderManage);
            lvOrderManage = findViewById(R.id.lvOrderManage);
            customerArrayList = new ArrayList<>();
            customerAdapter =  new CustomerAdapter(OrderManageActivity.this,customerArrayList);
            lvOrderManage.setAdapter(customerAdapter);
            actionToolbar();
            getData();

        }else{
            Check.ShowToast(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
        }
    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String linkGetCustomer = "http://foodsale.000webhostapp.com/localhost/getCustomer.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(linkGetCustomer, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    int id = 0;
                    String name = "";
                    int Phone = 0;
                    String Address = "";
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            name = jsonObject.getString("name");
                            Phone = jsonObject.getInt("phone");
                            Address = jsonObject.getString("address");
                            customerArrayList.add(new Customer(id,name,Phone,Address));
                            customerAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    private void actionToolbar() {
        toolBarOrderManage.setNavigationIcon(R.drawable.goback);
        toolBarOrderManage.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}