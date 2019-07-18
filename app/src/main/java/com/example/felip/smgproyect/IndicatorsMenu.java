package com.example.felip.smgproyect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.felip.smgproyect.service.RetrofitInstance;
import com.example.felip.smgproyect.service.SensorsServiceApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndicatorsMenu extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.prb_IndicatorHS)
    public ProgressBar floorHumidityProgress;

    @BindView(R.id.prb_IndicatorHR)
    public ProgressBar ambientHumidityProgress;

    @BindView(R.id.prb_IndicatorLight)
    public ProgressBar lightProgress;

    @BindView(R.id.prb_IndicatorTemp)
    public ProgressBar temperatureProgress;

    @BindView(R.id.lbl_IndicatorHS)
    public TextView lblFloorHumidityProgress;

    @BindView(R.id.lbl_IndicatorTemp)
    public TextView lblTemperatureProgress;

    @BindView(R.id.lbl_IndicatorLight)
    public TextView lblLightProgress;

    @BindView(R.id.lbl_IndicatorHR)
    public TextView lblAmbientHumidityProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicators_menu);
        ButterKnife.bind(this);
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

        SensorsServiceApi service = RetrofitInstance.getRetrofitInstance().create(SensorsServiceApi.class);
        getFloorHumidity(service);
        getLight(service);
        getAmbientHumidity(service);
        getTemperature(service);

        View indicatorHS = (View) findViewById(R.id.vw_IndicatorHS);
        View indicatorTemp = (View) findViewById(R.id.vw_IndicatorTemp);
        View indicatorLight = (View) findViewById(R.id.vw_IndicatorLight);
        View indicatorHr = (View) findViewById(R.id.vw_IndicatorHR);
        Button btnBack = (Button) findViewById(R.id.btn_InterfaceBack);

        indicatorHS.setOnClickListener(this);
        indicatorTemp.setOnClickListener(this);
        indicatorLight.setOnClickListener(this);
        indicatorHr.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void getFloorHumidity(SensorsServiceApi service) {
        Call<Integer> call = service.getFloorHumidity();
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                floorHumidityProgress.setProgress(response.body());
                setMessage(lblFloorHumidityProgress, response.body());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toasty.error(
                        getApplicationContext(),
                        "Error: " +  t.getLocalizedMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    @OnClick(R.id.btn_ImportIndicators)
    public void goToCustomization(){
        Intent i = new Intent(IndicatorsMenu.this, IndicatorRangeCustomization.class);
        startActivity(i);

    }

    private void getLight(SensorsServiceApi service) {
        Call<Integer> call = service.getLight();
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                lightProgress.setProgress(response.body());
                setMessage(lblLightProgress, response.body());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toasty.error(
                        getApplicationContext(),
                        "Error: " +  t.getLocalizedMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void getAmbientHumidity(SensorsServiceApi service) {
        Call<Integer> call = service.getAmbientHumidity();
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                ambientHumidityProgress.setProgress(response.body());
                setMessage(lblAmbientHumidityProgress, response.body());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toasty.error(
                        getApplicationContext(),
                        "Error: " +  t.getLocalizedMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void getTemperature(SensorsServiceApi service) {
        Call<Integer> call = service.getTemperature();
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                temperatureProgress.setProgress(response.body());
                setMessage(lblTemperatureProgress, response.body());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toasty.error(
                        getApplicationContext(),
                        "Error: " +  t.getLocalizedMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.vw_IndicatorHS:
                i = new Intent(IndicatorsMenu.this, DetailRegistryHS.class);
                startActivity(i);
                break;
            case R.id.vw_IndicatorTemp:
                i = new Intent(IndicatorsMenu.this, DetailRegistryTemp.class);
                startActivity(i);
                break;
            case R.id.vw_IndicatorLight:
                i = new Intent(IndicatorsMenu.this, DetailRegistryLight.class);
                startActivity(i);
                break;
            case R.id.vw_IndicatorHR:
                i = new Intent(IndicatorsMenu.this, DetailRegistryHR.class);
                startActivity(i);
                break;
            case R.id.btn_InterfaceBack:
                i = new Intent(IndicatorsMenu.this, AdminMenu.class);
                startActivity(i);
                break;
        }
    }

    private void setMessage(TextView textView, int value) {
        if (isBetween(value, 0, 25)) {
            textView.setText("Bajo");
        } else if (isBetween(value, 26, 50)) {
            textView.setText("Medio");
        } else if (isBetween(value, 51, 75)) {
            textView.setText("Óptimo");
        } else if (isBetween(value, 76, 100)) {
            textView.setText("Crítico");
        }
    }

    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }
}
