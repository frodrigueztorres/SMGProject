package com.example.felip.smgproyect.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SensorsServiceApi {
    @GET("api/{condition}/last?kit_name=kit_sgh")
    Call<ConditionsResponse> getCondition(@Path("condition")String condition);

    @GET("api/{condition}?kit_name=kit_sgh")
    Call<List<ConditionsResponse>> getConditions(@Path("condition") String condition);

}

