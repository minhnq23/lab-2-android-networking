package com.example.lab2testapi.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2testapi.R;
import com.example.lab2testapi.databinding.EditProductBinding;
import com.example.lab2testapi.databinding.ItemProductBinding;
import com.example.lab2testapi.model.Product;
import com.example.lab2testapi.retrofit.ApiClient;
import com.example.lab2testapi.retrofit.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.viewholder> {
    public Context context;
    public List<Product> products;
    private ApiService apiService;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }
    public void setProductList(List<Product> products) {
        this.products = products;
        notifyDataSetChanged(); // Cập nhật giao diện hiển thị
    }


    @NonNull
    @Override
    public ProductAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.viewholder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {


        private final ItemProductBinding binding;

        public viewholder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Product product) {
            binding.tvName.setText(product.getName());
            binding.imgDel.setOnClickListener(view -> {
                Log.e("ID", product.get_id()+"hih");
                deleteProduct(product.get_id());

            });
            binding.imgEdit.setOnClickListener(view -> {
                showMyDialog(product.get_id());
            });

        }
    }
    private void deleteProduct(String productId) {
        apiService = ApiClient.getApiService();
        Call<Void> call = apiService.deleteProduct(productId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Xử lý khi yêu cầu DELETE thành công
                    Toast.makeText(context, "Xóa thành công sản phẩm mã: "+ productId, Toast.LENGTH_SHORT).show();
                    refreshData();
                } else {
                    // Xử lý khi nhận được mã lỗi
                    Log.e("error", "Error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Xử lý khi gặp lỗi
                Log.e("Error", "Error occurred: " + t.getMessage());
            }
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
                    products = response.body();
                    setProductList(products); // Cập nhật dữ liệu trong adapter
                    // adapter.notifyDataSetChanged(); // Nếu cần thiết
                } else {
                    // Xử lý khi nhận được mã lỗi
                    Log.e("error", "hihe");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // Dừng animation pull-to-refresh
                // Xử lý khi gặp lỗi
                Log.e("Error", "Error occurred: " + t.getMessage());
            }
        });
    }

    private void showMyDialog(String productId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
       EditProductBinding binding = EditProductBinding.inflate(LayoutInflater.from(context));
        builder.setView(binding.getRoot());
        ApiService apiService = ApiClient.getApiService();

        builder.setPositiveButton("Save", (dialog, which) -> {
            String name = binding.edtName.getText().toString().trim();
            String priceStr = binding.edtPrice.getText().toString().trim();
            float price = Float.parseFloat(priceStr);
            String description = binding.edtDescription.getText().toString().trim();
            Product updatedProduct = new Product(name,price,description);

            // Xử lý dữ liệu khi nhấn Save
            // Ví dụ: Hiển thị thông tin nhập liệu lấy được từ dialog


            Call<Product> putCall = apiService.updateProduct(productId, updatedProduct);
            putCall.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if (response.isSuccessful()) {
                        Product updatedProduct = response.body();
                        // Xử lý kết quả trả về khi chỉnh sửa sản phẩm thành công
                        Toast.makeText(context, "Sản phẩm đã được chỉnh sửa", Toast.LENGTH_SHORT).show();
                        refreshData();
                    } else {
                        // Xử lý khi nhận được mã lỗi
                        Log.e("error", "Error code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    // Xử lý khi gặp lỗi
                    Log.e("Error", "Error occurred: " + t.getMessage());
                }
            });
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
