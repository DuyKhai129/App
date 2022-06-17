package com.example.appfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appfood.Adapter.CustomerProductAdapter;
import com.example.appfood.Model.Orders;
import com.example.appfood.R;
import com.example.appfood.Ultil.Check;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerProductActivity extends AppCompatActivity {
    Toolbar toolBar;
    ListView listView;
    static TextView tvTotalPrice;
    public static ArrayList<Orders> ordersArrayList;
    CustomerProductAdapter customerProductAdapter;
    int idOrder = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_product);
        if (Check.haveNetworkConnection(getApplicationContext())){
            anhXa();
            actionToolbar();
            getData();
            getIdCustomer();

        }else{
            Check.ShowToast(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
        }
    }

    public static void totalPrice() {
        long totalPrice = 0;
        for (int i = 0;i<ordersArrayList.size();i++){
            totalPrice += ordersArrayList.get(i).getTotalPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTotalPrice.setText(decimalFormat.format(totalPrice) + " Đ");
    }

    private void getIdCustomer() {
        idOrder = getIntent().getIntExtra("idCustomer",0);
    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String linkGetCustomerProduct = "http://foodsale.000webhostapp.com/localhost/customerProduct.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, linkGetCustomerProduct, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                int ordercode = 0;
                int productcode = 0;
                String namedetails = "";
                Integer totalPrice = 0;
                int quantity = 0;
                String Image = "";
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            ordercode = jsonObject.getInt("ordercode");
                            productcode = jsonObject.getInt("productcode");
                            namedetails = jsonObject.getString("namedetails");
                            Image = jsonObject.getString("image");
                            totalPrice = jsonObject.getInt("totalprice");
                            quantity = jsonObject.getInt("quantity");
                            ordersArrayList.add(new Orders(id, ordercode, productcode, namedetails,Image, totalPrice, quantity));
                            customerProductAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("idCustomer",String.valueOf(idOrder));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionToolbar() {
        toolBar.setNavigationIcon(R.drawable.goback);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void anhXa() {
        toolBar = findViewById(R.id.toolBar);
        listView = findViewById(R.id.listView);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        ordersArrayList = new ArrayList<>();
        customerProductAdapter =  new CustomerProductAdapter(CustomerProductActivity.this,ordersArrayList);
        listView.setAdapter(customerProductAdapter);
    }

}