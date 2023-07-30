package com.example.lab2testapi.retrofit;

import com.example.lab2testapi.model.Product;
import com.example.lab2testapi.utilities.ApiUrl;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET(ApiUrl.ApiGet)
    Call<List<Product>> getProducts();
    @POST(ApiUrl.ApiPost)
    Call<Product> createProduct(@Body Product productRequest);
    @DELETE(ApiUrl.ApiDelete)
    Call<Void> deleteProduct(@Path("id") String productId);
    @PUT(ApiUrl.ApiEdit)
    Call<Product> updateProduct(@Path("id") String productId, @Body Product updatedProduct);

}
