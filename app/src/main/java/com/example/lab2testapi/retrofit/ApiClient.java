package com.example.lab2testapi.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static  ApiService getApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.101:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiService.class);
    }
}
