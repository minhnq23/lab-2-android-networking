package com.example.lab2testapi;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.lab2testapi.adapter.ProductAdapter;
import com.example.lab2testapi.databinding.ActivityMainBinding;
import com.example.lab2testapi.model.Product;
import com.example.lab2testapi.retrofit.ApiClient;
import com.example.lab2testapi.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProductAdapter adapter;
    private List<Product> list = new ArrayList<>();
    //api
    ApiService apiService = ApiClient.getApiService();
    //call
    Call<List<Product>> call = apiService.getProducts();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    Log.e("List", list.size()+" " +list.get(0).get_id());

                    adapter = new ProductAdapter(MainActivity.this,list);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    binding.recyclerView.setLayoutManager(layoutManager);
                    binding.recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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

//
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
                        refreshData();
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


    }

    private void refreshData() {
        // Gọi lại API để lấy dữ liệu mới
        ApiService apiService = ApiClient.getApiService();
        Call<List<Product>> call = apiService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                // Dừng animation pull-to-refresh
                if (response.isSuccessful()) {
                    list = response.body();
                    adapter.setProductList(list); // Cập nhật dữ liệu trong adapter
                    // adapter.notifyDataSetChanged(); // Nếu cần thiết
                } else {
                    // Xử lý khi nhận được mã lỗi
                    Log.e("error", "hihe");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false); // Dừng animation pull-to-refresh
                // Xử lý khi gặp lỗi
                Log.e("Error", "Error occurred: " + t.getMessage());
            }
        });
    }


}