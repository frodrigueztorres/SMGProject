package com.example.felip.smgproyect.service;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SensorsServiceApi {
    @GET("/floor-humidity")
    Call<Integer> getFloorHumidity();

    @GET("/light")
    Call<Integer> getLight();

    @GET("/ambient-humidity")
    Call<Integer> getAmbientHumidity();

    @GET("/temperature")
    Call<Integer> getTemperature();

    @GET("/{condition}?all=true")
    Call<List<ConditionsResponse>> getConditions(@Path("condition") String condition);

}

