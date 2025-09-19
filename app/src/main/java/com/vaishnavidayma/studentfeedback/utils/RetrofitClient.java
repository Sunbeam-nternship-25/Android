package com.vaishnavidayma.studentfeedback.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient retrofitClient;

    private Api api;

    private RetrofitClient(){
        api= new Retrofit.Builder()
                .baseUrl(api.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api.class);
    }

    public static RetrofitClient getInstance(){
        if(retrofitClient == null)
            retrofitClient = new RetrofitClient();
        return  retrofitClient;
    }


    public Api getApi() {
        return api;
    }
}
