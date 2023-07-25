package com.example.lab2testapi;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab2testapi.databinding.ActivityMainBinding;
import com.example.lab2testapi.model.Product;
import com.example.lab2testapi.retrofit.ApiClient;
import com.example.lab2testapi.retrofit.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //api
        ApiService apiService = ApiClient.getApiService();

//        call.enqueue(new Callback<List<Product>>() {
//            @Override
//            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                if (response.isSuccessful()) {
//                    List<Product> products = response.body();
//                    Log.e("List", products.size()+"" );
//                    Log.e("Product", products.get(0).name+"");
//                } else {
//                    // Xử lý khi nhận được mã lỗi
//                    Log.e("error", "hihe");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Product>> call, Throwable t) {
//                // Xử lý khi gặp lỗi
//                Log.e("Error", "Error occurred: " + t.getMessage());
//            }
//        });
        binding.btnSave.setOnClickListener(view -> {
            String strName = binding.edName.getText().toString().trim();
            float strPrice = Float.parseFloat(binding.edPrice.getText().toString().trim());
            String strDes = binding.edDes.getText().toString().trim();
            Product newProduct = new Product(strName, strPrice, strDes);
            // Gửi yêu cầu API POST để tạo sản phẩm mới
            Call<Product> postCall = apiService.createProduct(newProduct);
            postCall.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if (response.isSuccessful()) {
                        Product createdProduct = response.body();
                        // Xử lý kết quả trả về khi tạo sản phẩm thành công
                        Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        // Xử lý khi gọi API thành công nhưng nhận được kết quả không hợp lệ
                        Log.e("er", response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    // Xử lý khi gọi API thất bại (lỗi kết nối, lỗi server, ...)
                }
            });
        });


        binding.btnGet.setOnClickListener(view -> {
            Call<List<Product>> call = apiService.getProducts();
            call.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if (response.isSuccessful()) {
                        List<Product> products = response.body();
                        Log.e("List", products.size()+"" );
                        Log.e("Product", products.get(0).name+"");
                        Toast.makeText(MainActivity.this, products+"", Toast.LENGTH_SHORT).show();
                    } else {
                        // Xử lý khi nhận được mã lỗi
                        Log.e("error", "hihe");
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    // Xử lý khi gặp lỗi
                    Log.e("Error", "Error occurred: " + t.getMessage());
                }
            });

        });


    }


}