package com.example.appfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appfood.Adapter.CategoryAdapter;
import com.example.appfood.Adapter.ProductNewAdapter;
import com.example.appfood.Adapter.ProductPopularAdapter;
import com.example.appfood.Model.Cart;
import com.example.appfood.Model.Category;
import com.example.appfood.Model.Product;
import com.example.appfood.R;
import com.example.appfood.Ultil.Check;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbarHome;
    ImageView imgCart;
    RecyclerView recyclerviewNew,recyclerPopular;
    NavigationView navigationView;
    ListView lvHome;
    ViewFlipper viewFlipper;
    ArrayList<Category> arrayCategory;
    CategoryAdapter categoryAdapter;
    ArrayList<Product> arrayProductNew;
    ProductNewAdapter productNewAdapter;
    ArrayList<Product> arrayProductPopular;
    ProductPopularAdapter productPopularAdapter;
    public static ArrayList<Cart> arrayListCart;
    public static int id = 0;
    public static String productName = "";
    public static Integer productPrice = 0;
    public static String productImage = "";
    public static String description = "";
    public static int idCategory = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (Check.haveNetworkConnection(getApplicationContext())){
            anhXa();
            ActionBar();
            ActionViewFlipper();
            getDataCategory();
            GetDataProductNew();
            GetDataProductPopular();
            MenuClick();
            cart();

        }else{
            Check.ShowToast(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
            finish();
        }

    }

    private void cart() {
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
    }


    private void MenuClick() {
        lvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if (Check.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(HomeActivity.this,HomeActivity.class);
                            startActivity(intent);
                        }else{
                            Check.ShowToast(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (Check.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(HomeActivity.this,FoodActivity.class);
                            intent.putExtra("idcategory",arrayCategory.get(i).getId());
                            startActivity(intent);
                        }else{
                            Check.ShowToast(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (Check.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(HomeActivity.this,DrinksActivity.class);
                            intent.putExtra("idcategory",arrayCategory.get(i).getId());
                            startActivity(intent);
                        }else{
                            Check.ShowToast(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (Check.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(HomeActivity.this,ContactActivity.class);
                            startActivity(intent);
                        }else{
                            Check.ShowToast(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (Check.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }else{
                            Check.ShowToast(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDataProductPopular() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String linkGetProductPopular = "http://foodsale.000webhostapp.com/localhost/getProductPopular.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(linkGetProductPopular, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){

                    for (int i = 0; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            productName = jsonObject.getString("pname");
                            productPrice = jsonObject.getInt("price");
                            productImage = jsonObject.getString("image");
                            description = jsonObject.getString("description");
                            idCategory = jsonObject.getInt("idcategory");
                            arrayProductPopular.add(new Product(id,productName,productPrice,productImage,description,idCategory));
                            productPopularAdapter.notifyDataSetChanged();
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

    private void GetDataProductNew() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String linkGetProductNew = "http://foodsale.000webhostapp.com/localhost/getProductNew.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(linkGetProductNew, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){

                    for (int i = 0; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            productName = jsonObject.getString("pname");
                            productPrice = jsonObject.getInt("price");
                            productImage = jsonObject.getString("image");
                            description = jsonObject.getString("description");
                            idCategory = jsonObject.getInt("idcategory");
                            arrayProductNew.add(new Product(id,productName,productPrice,productImage,description,idCategory));
                            productNewAdapter.notifyDataSetChanged();
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

    private void getDataCategory() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String linkGetCategory = "http://foodsale.000webhostapp.com/localhost/getCategory.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(linkGetCategory, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    int id = 0;
                    String name = "";
                    String image = "";
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            name = jsonObject.getString("name");
                            image = jsonObject.getString("image");
                            arrayCategory.add(new Category(id, name, image));
                            categoryAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    arrayCategory.add(3, new Category(0, "Liên hệ", "https://icons.iconarchive.com/icons/graphicloads/100-flat-2/256/phone-icon.png"));
                    arrayCategory.add(4, new Category(0, "Đăng nhập", "https://www.freeiconspng.com/uploads/client-login-icon-4.gif"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> advertisement = new ArrayList<>();
        advertisement.add("https://hotelcareers.vn/wp-content/uploads/2020/12/mon-an-ngon-o-ha-noi.jpg");
        advertisement.add("https://chiasemonngon.com/wp-content/uploads/2018/09/c%C3%A1c-m%C3%B3n-%C4%83-ngon.jpg");
        advertisement.add("https://bigvn.blog/dp/wp-content/uploads/2018/07/tong-hop-dia-chi-cac-mon-an-ngon-o-ha-noi-ban-nhat-dinh-khong-duoc-bo-qua-1-600x422.jpg");
        advertisement.add("https://skp.com.vn/wp-content/uploads/2021/03/tim-hieu-net-dac-trung-trong-van-hoa-am-thuc-nguoi-my-tim-hieu-net-dac-trung-trong-van-hoa-am-thuc-nguoi-my-3.jpg");
        for (int i=0;i<advertisement.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(advertisement.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slider_in_right);
        Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slider_out_right);
        viewFlipper.setInAnimation(animation_in);
        viewFlipper.setOutAnimation(animation_out);

    }

    private void ActionBar() {
        toolbarHome.setNavigationIcon(R.drawable.menu);
        toolbarHome.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void anhXa() {
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbarHome =  findViewById(R.id.toolbarHome);
        imgCart = findViewById(R.id.imgCart);
        viewFlipper = findViewById(R.id.viewFlipper);
        recyclerviewNew = findViewById(R.id.recyclerviewNew);
        recyclerPopular = findViewById(R.id.recyclerPopular);
        navigationView = findViewById(R.id.navigationView);
        lvHome = findViewById(R.id.lvHome);

        arrayCategory = new ArrayList<>();
        arrayCategory.add(0,new Category(0,"Trang chủ","https://icons.iconarchive.com/icons/graphicloads/100-flat/256/home-icon.png"));
        categoryAdapter = new CategoryAdapter(arrayCategory,getApplicationContext());
        lvHome.setAdapter(categoryAdapter);

        arrayProductNew = new ArrayList<>();
        productNewAdapter = new ProductNewAdapter(HomeActivity.this,arrayProductNew);
        recyclerviewNew.setHasFixedSize(true);
        recyclerviewNew.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
        recyclerviewNew.setAdapter(productNewAdapter);

        arrayProductPopular = new ArrayList<>();
        productPopularAdapter = new ProductPopularAdapter(HomeActivity.this,arrayProductPopular);
        recyclerPopular.setHasFixedSize(true);
        recyclerPopular.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerPopular.setAdapter(productPopularAdapter);
        if(arrayListCart !=null){

        }else {
            arrayListCart = new ArrayList<>();
        }

    }
}