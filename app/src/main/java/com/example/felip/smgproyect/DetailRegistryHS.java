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

import static com.example.felip.smgproyect.data.model.ConditionConfiguration.Condition.AMBIENT_HUMIDITY;

public class DetailRegistryHS extends AppCompatActivity {

    @BindView(R.id.rv_ListIndicatorHS)
    RecyclerView indicatorsRecyclerView;

    private ConditionAdapter conditionAdapter;
    private ArrayList<ConditionsResponse> conditionsList;

    @BindView(R.id.pbr_DetailBarHs)
    ProgressBar progressBar;

    @BindView(R.id.lbl_DetailIndicatorHs)
    TextView labelProgress;

    @BindView(R.id.lbl_NumberIndicatorHs)
    TextView number;

    List<ConditionConfiguration> configurations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_registry_hs);
        ButterKnife.bind(this);

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

        Button btnBackIndicator = (Button) findViewById(R.id.btn_BackDetailIndicatorHS);

        configurations = new ArrayList<>();
        getConfigurations();

        SensorsServiceApi service = RetrofitInstance.getRetrofitInstance().create(SensorsServiceApi.class);
        getHistoric(service);
        getActualCondition(service);

        btnBackIndicator.setOnClickListener(v -> {
            Intent i = new Intent(DetailRegistryHS.this, IndicatorsMenu.class);
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
        Call<Integer> call = service.getCondition("ambient-humidity");
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                progressBar.setProgress(response.body());
                setMessage(labelProgress, response.body(), AMBIENT_HUMIDITY);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
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

    private void getHistoric(SensorsServiceApi service) {
        Call<List<ConditionsResponse>>  call = service.getConditions("floor-humidity");
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
