package com.example.felip.smgproyect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.felip.smgproyect.adapter.ConditionAdapter;
import com.example.felip.smgproyect.data.DatabaseInstance;
import com.example.felip.smgproyect.data.SMGDatabase;
import com.example.felip.smgproyect.data.model.ConditionConfiguration;
import com.example.felip.smgproyect.service.ConditionsResponse;
import com.example.felip.smgproyect.service.RetrofitInstance;
import com.example.felip.smgproyect.service.SensorsServiceApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.felip.smgproyect.data.model.ConditionConfiguration.Condition.TEMPERATURE;

public class DetailRegistryTemp extends AppCompatActivity {

    @BindView(R.id.rv_ListIndicatorTemp)
    RecyclerView indicatorsRecyclerView;

    @BindView(R.id.pbr_DetailBarTemp)
    ProgressBar progressBar;

    @BindView(R.id.lbl_DetailIndicatorTemp)
    TextView labelProgress;

    @BindView(R.id.lbl_NumberIndicatorTemp)
    TextView number;

    List<ConditionConfiguration> configurations;

    private ConditionAdapter conditionAdapter;
    private ArrayList<ConditionsResponse> conditionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_registry_temp);
        ButterKnife.bind(this);

        indicatorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        conditionsList = new ArrayList<>();

        conditionAdapter = new ConditionAdapter(this, conditionsList);
        indicatorsRecyclerView.setAdapter(conditionAdapter);

        configurations = new ArrayList<>();
        getConfigurations();

        SensorsServiceApi service = RetrofitInstance.getRetrofitInstance().create(SensorsServiceApi.class);
        getTemperatureDetails(service);
        getActualCondition(service);

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

        Button btnBackIndicator = (Button) findViewById(R.id.btn_BackDetailIndicatorTemp);

        btnBackIndicator.setOnClickListener(v -> {
            Intent i = new Intent(DetailRegistryTemp.this, IndicatorsMenu.class);
            startActivity(i);
        });

    }

    private void getConfigurations() {
        SMGDatabase database = DatabaseInstance.getDatabaseInstance(getApplicationContext());
        database.configurationDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(list -> configurations = list)
                .doOnComplete(() -> Toasty.info(getApplicationContext(), "No Configuration found").show())
                .subscribe();
    }

    private void getActualCondition(SensorsServiceApi service) {
        Call<ConditionsResponse> call = service.getCondition("temperature");
        call.enqueue(new Callback<ConditionsResponse>() {
            @Override
            public void onResponse(Call<ConditionsResponse> call, Response<ConditionsResponse> response) {
                progressBar.setProgress(response.body().getValue());
                setMessage(labelProgress, response.body().getValue(), TEMPERATURE);
            }

            @Override
            public void onFailure(Call<ConditionsResponse> call, Throwable t) {
                Toasty.error(
                        getApplicationContext(),
                        "Error: " + t.getLocalizedMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void setMessage(TextView textView, int value, ConditionConfiguration.Condition condition) {
        number.setText(Integer.toString(value));
        ConditionConfiguration config = getConditionConfiguration(condition);
        if (value < config.low) {
            textView.setText("Bajo");
        } else if (value < config.medium) {
            textView.setText("Medio");
        } else if (value < config.high) {
            textView.setText("Óptimo");
        } else if (value > config.high) {
            textView.setText("Crítico");
        }
    }

    private ConditionConfiguration getConditionConfiguration(ConditionConfiguration.Condition condition) {
        for(ConditionConfiguration configuration : configurations) {
            if(condition == configuration.condition) {
                return configuration;
            }
        }
        return null;
    }

    private void getTemperatureDetails(SensorsServiceApi service) {
        Call<List<ConditionsResponse>> call = service.getConditions("temperature");
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
