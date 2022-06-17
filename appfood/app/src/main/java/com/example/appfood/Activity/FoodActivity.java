package com.example.appfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appfood.Adapter.FoodAdapter;
import com.example.appfood.Model.Cart;
import com.example.appfood.Model.Product;
import com.example.appfood.R;
import com.example.appfood.Ultil.Check;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FoodActivity extends AppCompatActivity {
    Toolbar toolBarFood;
    ListView lvFood;
    ImageView imgCart;
    FoodAdapter foodAdapter;
    ArrayList<Product> arrayListFood;
    int idProduct = 0;
    TextView tvName,tvPrice,tvDescription;
    Button btPlus,btValues,btMinus,btAddCard;
    ImageView imgDetails;
    int Id = 0;
    String Name = "";
    int Price = 0;
    String Image = "";
    String 	Description = "";
    int idCategory = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        if (Check.haveNetworkConnection(getApplicationContext())){
            anhXa();
            actionToolBar();
            getIdCategory();
            getDataFood();
            viewDetails();
            cart();
        }else{
            Check.ShowToast(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
        }
    }

    private void cart() {
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void viewDetails() {
        lvFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Dialog dialog = new Dialog(FoodActivity.this);

                dialog.setContentView(R.layout.details);
                tvName = dialog.findViewById(R.id.tvName);
                tvPrice = dialog.findViewById(R.id.tvPrice);
                tvDescription = dialog.findViewById(R.id.tvDescription);
                imgDetails = dialog.findViewById(R.id.imgDetails);
                btMinus = dialog.findViewById(R.id.btMinus);
                btPlus = dialog.findViewById(R.id.btPlus);
                btValues = dialog.findViewById(R.id.btValues);
                btAddCard = dialog.findViewById(R.id.btAddCard);

                Id = arrayListFood.get(i).getId();
                idCategory = arrayListFood.get(i).getIdCategory();
                Name = arrayListFood.get(i).getpName();
                Price = arrayListFood.get(i).getPrice();
                Image = arrayListFood.get(i).getImage();
                Description = arrayListFood.get(i).getDescription();

                tvName.setText(Name);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                tvPrice.setText(decimalFormat.format(Price) + " Đ");
                tvDescription.setText(Description);
                Picasso.with(getApplicationContext()).load(Image)
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
                                if (HomeActivity.arrayListCart.get(i).getId() == Id){
                                    HomeActivity.arrayListCart.get(i).setQuantity(HomeActivity.arrayListCart.get(i).getQuantity() + addQuantity);
                                    if (HomeActivity.arrayListCart.get(i).getQuantity() >= 100){
                                        HomeActivity.arrayListCart.get(i).setQuantity(100);
                                    }
                                    HomeActivity.arrayListCart.get(i).setPrice(Price + HomeActivity.arrayListCart.get(i).getQuantity());
                                    exist = true;
                                }
                            }
                            if (exist == false){
                                int quantity = Integer.parseInt(btValues.getText().toString());
                                long totalPrice = quantity * Price;
                                HomeActivity.arrayListCart.add(new Cart(Id,Name,totalPrice,Image,quantity));
                            }
                        }else {
                            int quantity = Integer.parseInt(btValues.getText().toString());
                            long totalPrice = quantity * Price;
                            HomeActivity.arrayListCart.add(new Cart(Id,Name,totalPrice,Image,quantity));
                        }
                        Check.ShowToast(FoodActivity.this," Sản phẩm đã được thêm vào giỏ hàng");
                    }
                });
                dialog.show();
            }
        });
    }

    private void getIdCategory() {
        idProduct = getIntent().getIntExtra("idcategory",0);
    }

    private void getDataFood() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String linkGetFood = "http://foodsale.000webhostapp.com/localhost/getProduct.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, linkGetFood, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Id = jsonObject.getInt("id");
                            Name = jsonObject.getString("pname");
                            Price = jsonObject.getInt("price");
                            Image = jsonObject.getString("image");
                            Description = jsonObject.getString("description");
                            idCategory = jsonObject.getInt("idcategory");
                            arrayListFood.add(new Product(Id,Name,Price,Image,Description,idCategory));
                            foodAdapter.notifyDataSetChanged();
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
                HashMap<String,String> hashMap = new HashMap<String,String>();
                hashMap.put("idcategory",String.valueOf(idProduct));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionToolBar() {
        toolBarFood.setNavigationIcon(R.drawable.goback);
        toolBarFood.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void anhXa() {
        toolBarFood = findViewById(R.id.toolBarFood);
        lvFood = findViewById(R.id.lvFood);
        imgCart = findViewById(R.id.imgCart);
        arrayListFood  = new ArrayList<>();
        foodAdapter = new FoodAdapter(getApplicationContext(),arrayListFood);
        lvFood.setAdapter(foodAdapter);
    }
}