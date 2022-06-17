package com.example.appfood.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appfood.R;
import com.example.appfood.Ultil.Check;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomerActivity extends AppCompatActivity {
    EditText edtCustomerName,edtCustomerPhone,edtCustomerAddress;
    Button btConfirm,btEscape;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        if (Check.haveNetworkConnection(getApplicationContext())){
            anhXa();
            eventButton();
            btEscape.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            Check.ShowToast(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
        }
    }

    private void eventButton() {


        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = edtCustomerName.getText().toString().trim();
                String Phone = edtCustomerPhone.getText().toString().trim();
                String Address = edtCustomerAddress.getText().toString().trim();
                if ( Phone.length() > 10 ){
                    Check.ShowToast(getApplicationContext(),"Số điện thoại bạn nhập ko đúng...!");
                }
                else if (Name.length() > 0 && Phone.length() > 0 && Address.length() > 0 && Phone.length() == 10){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String linkGetCustomer = "http://192.168.0.102/localhost/customer.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, linkGetCustomer, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String orderCode) {
                            if (Integer.parseInt(orderCode) > 0){
                                RequestQueue request = Volley.newRequestQueue(getApplicationContext());
                                String linkGetOrderDetails = "http://foodsale.000webhostapp.com/localhost/orderdetails.php";
                                StringRequest stringR = new StringRequest(Request.Method.POST, linkGetOrderDetails, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")){
                                            HomeActivity.arrayListCart.clear();
                                            Check.ShowToast(getApplicationContext(),"Bạn đã mua hàng thành công...!");
                                            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                            startActivity(intent);
                                        }else{
                                            Check.ShowToast(getApplicationContext(),"Bạn bị lỗi khi mua hàng...!");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0; i<HomeActivity.arrayListCart.size();i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("ordercode",orderCode);
                                                jsonObject.put("productcode",HomeActivity.arrayListCart.get(i).getId());
                                                jsonObject.put("image",HomeActivity.arrayListCart.get(i).getImage());
                                                jsonObject.put("namedetails",HomeActivity.arrayListCart.get(i).getName());
                                                jsonObject.put("totalprice",HomeActivity.arrayListCart.get(i).getPrice());
                                                jsonObject.put("quantity",HomeActivity.arrayListCart.get(i).getQuantity());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap = new HashMap<String,String>();
                                        hashMap.put("order",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                request.add(stringR);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String,String>();
                            hashMap.put("name",Name);
                            hashMap.put("phone",Phone);
                            hashMap.put("address",Address);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else {
                    Check.ShowToast(getApplicationContext(),"Hãy kiểm tra lại thông tin bạn vừa nhập...!");
                }
            }
        });
    }

    private void anhXa() {
        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtCustomerPhone = findViewById(R.id.edtCustomerPhone);
        edtCustomerAddress = findViewById(R.id.edtCustomerAddress);
        btConfirm = findViewById(R.id.btConfirm);
        btEscape = findViewById(R.id.btEscape);
    }
}