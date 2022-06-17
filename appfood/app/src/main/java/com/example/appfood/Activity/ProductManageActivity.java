package com.example.appfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appfood.Adapter.ProductManageAdapter;
import com.example.appfood.Model.Product;
import com.example.appfood.R;
import com.example.appfood.Ultil.Check;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductManageActivity extends AppCompatActivity {
    Toolbar toolBarProductManage;
    ListView lvProductManage;
    ImageView imgAdd;
    ProductManageAdapter productManageAdapter;
    ArrayList<Product> productArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manage);
        if (Check.haveNetworkConnection(getApplicationContext())){
            anhXa();
            actionToolbar();
            eventButtonAdd();
            getData();
            imgAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ProductManageActivity.this,ProductAddActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            Check.ShowToast(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
        }
    }

    private void anhXa() {
        toolBarProductManage = findViewById(R.id.toolBarProductManage);
        lvProductManage = findViewById(R.id.lvProductManage);
        imgAdd = findViewById(R.id.imgAdd);
        productArrayList = new ArrayList<>();
        productManageAdapter =  new ProductManageAdapter(ProductManageActivity.this,productArrayList);
        lvProductManage.setAdapter(productManageAdapter);

    }
    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String linkGetProduct = "http://foodsale.000webhostapp.com/localhost/productManage.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(linkGetProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    int Id = 0;
                    String Name = "";
                    int Price = 0;
                    String Image = "";
                    String 	Description = "";
                    int idCategory = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Id = jsonObject.getInt("id");
                            Name = jsonObject.getString("pname");
                            Price = jsonObject.getInt("price");
                            Image  = jsonObject.getString("image");
                            Description = jsonObject.getString("description");
                            idCategory = jsonObject.getInt("idcategory");
                            productArrayList.add(new Product(Id,Name,Price,Image,Description,idCategory));
                            productManageAdapter.notifyDataSetChanged();
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

    private void eventButtonAdd() {
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void actionToolbar() {
        toolBarProductManage.setNavigationIcon(R.drawable.goback);
        toolBarProductManage.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}