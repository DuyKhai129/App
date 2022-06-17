package com.example.appfood.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appfood.Adapter.ProductCategoryAdapter;
import com.example.appfood.Model.Category;
import com.example.appfood.Model.Product;
import com.example.appfood.R;
import com.example.appfood.Ultil.Check;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductUpdateActivity extends AppCompatActivity {
    ImageView imgUpdate;
    Button btSelectPhoto,btTakePhoto,buttonSave,buttonCancel;
    EditText edtName,edtPrice,edtDescription;
    Spinner spinnerCategory;
    ArrayList<Category> arrayCategory;
    ProductCategoryAdapter categoryAdapter;
    int idProduct = 0;
    int Id = 0;
    String Name = "";
    int Price = 0;
    String Image = "";
    String 	Description = "";
    int idCategory = 0;
    final int takePhoto =  123;
    final int choosePhoto = 321;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_update);
        if (Check.haveNetworkConnection(getApplicationContext())){
            anhXa();
            getIdpProduct();
            getInformation();
            getDataCategory();
            eventButtonClick();
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
                    imgUpdate.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else if (requestCode == takePhoto){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgUpdate.setImageBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void eventButtonClick() {
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

    private void getInformation() {
        Product product = (Product) getIntent().getSerializableExtra("id");
        Id = product.getId();
        Name = product.getpName();
        Price = product.getPrice();
        Image  = product.getImage();
        Description = product.getDescription();
        idCategory = product.getIdCategory();
        edtName.setText(Name);
        edtDescription.setText(Description);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        edtPrice.setText( decimalFormat.format(Price));
        spinnerCategory.setId(idCategory);
        Picasso.with(getApplicationContext()).load(Image)
                .placeholder(R.drawable.noimage).error(R.drawable.error)
                .into(imgUpdate);
    }

    private void getIdpProduct() {
        idProduct = getIntent().getIntExtra("idProduct",0);
    }

    private void anhXa() {
        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel = findViewById(R.id.buttonCancel);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        imgUpdate = findViewById(R.id.imgUpdate);
        btSelectPhoto = findViewById(R.id.btSelectPhoto);
        btTakePhoto = findViewById(R.id.btTakePhoto);
        arrayCategory = new ArrayList<>();
        arrayCategory.add(0,new Category(0,"Loại sản phẩm","https://icons.iconarchive.com/icons/elegantthemes/beautiful-flat-one-color/128/cursor-icon.png"));
        categoryAdapter = new ProductCategoryAdapter(arrayCategory,ProductUpdateActivity.this);
        spinnerCategory.setAdapter(categoryAdapter);
    }
}