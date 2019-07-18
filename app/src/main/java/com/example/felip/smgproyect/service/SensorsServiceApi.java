package com.example.felip.smgproyect.service;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SensorsServiceApi {
    @GET("/{condition}/last")
    Call<Integer> getCondition(@Path("condition")String condition);

    @GET("/{condition}")
    Call<List<ConditionsResponse>> getConditions(@Path("condition") String condition);

}

