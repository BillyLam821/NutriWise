package com.example.nutriwise.api;

import com.example.nutriwise.model.NutritionDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EdamamApiService {
    @GET("nutrition-data")
    Call<NutritionDataResponse> getNutritionData(
            @Query("app_id") String appId,
            @Query("app_key") String appKey,
            @Query("ingr") String ingredient
    );
}
