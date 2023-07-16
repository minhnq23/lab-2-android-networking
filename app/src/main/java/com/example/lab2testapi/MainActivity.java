package com.example.lab2testapi;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

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
        setContentView(R.layout.activity_main);
        ApiService apiService = ApiClient.getApiService();
        Call<List<Product>> call = apiService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> products = response.body();
                    Log.e("List", products.size()+"" );
                    Log.e("Product", products.get(0).name+"");
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



    }


}