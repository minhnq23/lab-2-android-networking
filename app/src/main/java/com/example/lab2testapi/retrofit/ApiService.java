package com.example.lab2testapi.retrofit;

import com.example.lab2testapi.model.Product;
import com.example.lab2testapi.utilities.ApiUrl;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/api/products")
    Call<List<Product>> getProducts();
}
