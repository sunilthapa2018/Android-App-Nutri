package com.example.nutri.requests;

import com.example.nutri.requests.responses.FoodResponses;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodApi {
    @GET("parser")
    Call<FoodResponses> searchFood(
            @Query("ingr") String ingr,
            @Query("app_id") String app_id,
            @Query("app_key") String app_key
    );
}
