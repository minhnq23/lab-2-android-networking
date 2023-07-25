package com.example.lab2testapi.retrofit;

import com.example.lab2testapi.model.Product;
import com.example.lab2testapi.utilities.ApiUrl;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET(ApiUrl.ApiGet)
    Call<List<Product>> getProducts();
    @POST(ApiUrl.ApiPost)
    Call<Product> createProduct(@Body Product productRequest);

}
