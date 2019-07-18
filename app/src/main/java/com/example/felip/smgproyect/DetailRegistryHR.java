package com.example.felip.smgproyect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.felip.smgproyect.adapter.ConditionAdapter;
import com.example.felip.smgproyect.service.ConditionsResponse;
import com.example.felip.smgproyect.service.SensorsServiceApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRegistryHR extends AppCompatActivity {

    @BindView(R.id.rv_ListIndicatorHS)
    RecyclerView indicatorsRecyclerView;

    private ConditionAdapter conditionAdapter;
    private ArrayList<ConditionsResponse> conditionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_registry_hr);

        indicatorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        conditionsList = new ArrayList<>();

        conditionAdapter = new ConditionAdapter(this, conditionsList);
        indicatorsRecyclerView.setAdapter(conditionAdapter);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        Button btnBackIndicator = findViewById(R.id.btn_BackDetailIndicatorHR);

        btnBackIndicator.setOnClickListener(v -> {
            Intent i = new Intent(DetailRegistryHR.this, IndicatorsMenu.class);
            startActivity(i);
        });
    }

    private void getTemperature(SensorsServiceApi service) {
        Call<List<ConditionsResponse>> call = service.getConditions("floor-humidity");
        call.enqueue(new Callback<List<ConditionsResponse>>() {
            @Override
            public void onResponse(Call<List<ConditionsResponse>> call, Response<List<ConditionsResponse>> response) {
                Toasty.success(getApplicationContext(), response.toString()).show();
                if (response.body() != null) {
                    conditionsList.addAll(response.body());
                    conditionAdapter.notifyDataSetChanged();
                } else {
                    Toasty.info(getApplicationContext(), getString(R.string.no_data_available)).show();

                }
            }

            @Override
            public void onFailure(Call<List<ConditionsResponse>> call, Throwable t) {
                Toasty.error(getApplicationContext(), t.getMessage()).show();

            }
        });
    }
}
