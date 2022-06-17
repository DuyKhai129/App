package com.example.appfood.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appfood.Adapter.CategoryAdapter;
import com.example.appfood.Adapter.ProductCategoryAdapter;
import com.example.appfood.Model.Category;
import com.example.appfood.R;
import com.example.appfood.Ultil.Check;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductAddActivity extends AppCompatActivity {
    Button buttonAdd,buttonCancel,btSelectPhoto,btTakePhoto;
    Spinner spinnerCategory;
    EditText edtName,edtPrice,edtDescription;
    ImageView imageAdd;
    ArrayList<Category> arrayCategory;
    ProductCategoryAdapter categoryAdapter;
    final int takePhoto =  123;
    final int choosePhoto = 321;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        if (Check.haveNetworkConnection(getApplicationContext())){
            anhXa();
            eventClickButton();
            getDataCategory();
        }else{
            Check.ShowToast(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
        }
    }
    private  void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,takePhoto);
    }
    private void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,choosePhoto);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == choosePhoto){
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imageAdd.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else if (requestCode == takePhoto){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imageAdd.setImageBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void eventClickButton() {
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhoto();
            }
        });
        btTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idCategory = "";
                String Name = edtName.getText().toString().trim();
                String price = edtPrice.getText().toString().trim();
                byte[] image = getByteArrayFromImageView(imageAdd);
                String description = edtDescription.getText().toString().trim();
                if (spinnerCategory.getSelectedItemId() > 0){
                      idCategory = String.valueOf(spinnerCategory.getSelectedItemId());
                }
                if (Name.length() > 0 && price.length() > 0 && image.length > 0 && description.length() > 0 && idCategory.length() > 0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String linkInsertProduct = "http://192.168.0.102/localhost/insertProduct.php";
                    String finalIdCategory = idCategory;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, linkInsertProduct, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                                Check.ShowToast(getApplicationContext(),"Bạn đã thêm sản phẩm thành công...!");
                                Intent intent = new Intent(getApplicationContext(),ProductManageActivity.class);
                                startActivity(intent);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String,String>();
                            hashMap.put("pname",Name);
                            hashMap.put("price",price);
                            hashMap.put("image", String.valueOf(image));
                            hashMap.put("description",description);
                            hashMap.put("idcategory", finalIdCategory);
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
    private byte[] getByteArrayFromImageView(ImageView img){
        BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
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
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void anhXa() {
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonCancel = findViewById(R.id.buttonCancel);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        imageAdd = findViewById(R.id.imageAdd);
        btSelectPhoto = findViewById(R.id.btSelectPhoto);
        btTakePhoto = findViewById(R.id.btTakePhoto);
        arrayCategory = new ArrayList<>();
        arrayCategory.add(0,new Category(0,"Loại sản phẩm","https://icons.iconarchive.com/icons/elegantthemes/beautiful-flat-one-color/128/cursor-icon.png"));
        categoryAdapter = new ProductCategoryAdapter(arrayCategory,ProductAddActivity.this);
        spinnerCategory.setAdapter(categoryAdapter);
    }
}