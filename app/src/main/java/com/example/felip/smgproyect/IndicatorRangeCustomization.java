package com.example.felip.smgproyect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.felip.smgproyect.data.DatabaseInstance;
import com.example.felip.smgproyect.data.SMGDatabase;
import com.example.felip.smgproyect.data.model.ConditionConfiguration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class IndicatorRangeCustomization extends AppCompatActivity {

    @BindView(R.id.edit_humiditya_low)
    EditText editHumidityaLow;
    @BindView(R.id.edit_humiditya_medium)
    EditText editHumidityaMedium;
    @BindView(R.id.edit_humiditya_high)
    EditText editHumidityaHigh;
    @BindView(R.id.edit_humidityf_low)
    EditText editHumidityfLow;
    @BindView(R.id.edit_humidityf_medium)
    EditText editHumidityfMedium;
    @BindView(R.id.edit_humidityf_high)
    EditText editHumidityfHigh;
    @BindView(R.id.edit_temperature_low)
    EditText editTemperatureLow;
    @BindView(R.id.edit_temperature_medium)
    EditText editTemperatureMedium;
    @BindView(R.id.edit_temperature_high)
    EditText editTemperatureHigh;
    @BindView(R.id.edit_light_low)
    EditText editLightLow;
    @BindView(R.id.edit_light_medium)
    EditText editLightMedium;
    @BindView(R.id.edit_light_high)
    EditText editLightHigh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator_range_customization);
        ButterKnife.bind(this);

        SMGDatabase database = DatabaseInstance.getDatabaseInstance(getApplicationContext());

        database.configurationDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(this::fillData)
                .doOnComplete(() -> Toasty.error(getApplicationContext(), "Error: no se encontró data").show())
                .doOnError(t -> Log.e("", "Error: " + t.getMessage()))
                .subscribe();

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
    }

    private void fillData(List<ConditionConfiguration> conditionConfigurations) {
        for(ConditionConfiguration conditionConfiguration : conditionConfigurations)
            switch (conditionConfiguration.condition) {
                case LIGHT:
                    editLightHigh.setText(Integer.toString(conditionConfiguration.high));
                    editLightMedium.setText(Integer.toString(conditionConfiguration.medium));
                    editLightLow.setText(Integer.toString(conditionConfiguration.low));
                    break;
                case TEMPERATURE:
                    editTemperatureHigh.setText(Integer.toString(conditionConfiguration.high));
                    editTemperatureMedium.setText(Integer.toString(conditionConfiguration.medium));
                    editTemperatureLow.setText(Integer.toString(conditionConfiguration.low));
                    break;
                case AMBIENT_HUMIDITY:
                    editHumidityaHigh.setText(Integer.toString(conditionConfiguration.high));
                    editHumidityaMedium.setText(Integer.toString(conditionConfiguration.medium));
                    editHumidityaLow.setText(Integer.toString(conditionConfiguration.low));
                    break;
                case FLOOR_HUMIDITY:
                    editHumidityfHigh.setText(Integer.toString(conditionConfiguration.high));
                    editHumidityfMedium.setText(Integer.toString(conditionConfiguration.medium));
                    editHumidityfLow.setText(Integer.toString(conditionConfiguration.low));
                    break;
            }
    }

    @OnClick(R.id.btn_saveIndicatorsCustomization)
    public void deleteAndUpdate() {
        SMGDatabase database = DatabaseInstance.getDatabaseInstance(getApplicationContext());
        Completable.fromAction(() -> database.configurationDao().deleteAll())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(this::updateEverything)
                .subscribe();

    }
    private void updateEverything() {
        List<ConditionConfiguration> configList = new ArrayList<>();
        configList.add(getTemperature());
        configList.add(getAmbientHumidity());
        configList.add(getFloorHumidity());
        configList.add(getLight());
        SMGDatabase database = DatabaseInstance.getDatabaseInstance(getApplicationContext());
        Observable.fromCallable(() -> database.configurationDao().insertAll(configList))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(this::successUpdating)
                .subscribe();

    }

    private void successUpdating() {
        Toasty.success(getApplicationContext(), "Configuración actualizada con éxito").show();
        Intent i = new Intent(IndicatorRangeCustomization.this, IndicatorsMenu.class);
        startActivity(i);
    }

    private ConditionConfiguration getFloorHumidity() {
        int low = Integer.parseInt(editHumidityfLow.getText().toString());
        int medium = Integer.parseInt(editHumidityfMedium.getText().toString());
        int high = Integer.parseInt(editHumidityfHigh.getText().toString());
        return new ConditionConfiguration(ConditionConfiguration.Condition.FLOOR_HUMIDITY, low, medium, high);
    }

    private ConditionConfiguration getAmbientHumidity() {
        int low = Integer.parseInt(editHumidityaLow.getText().toString());
        int medium = Integer.parseInt(editHumidityaMedium.getText().toString());
        int high = Integer.parseInt(editHumidityaMedium.getText().toString());
        return new ConditionConfiguration(ConditionConfiguration.Condition.AMBIENT_HUMIDITY, low, medium, high);
    }

    private ConditionConfiguration getTemperature() {
        int low = Integer.parseInt(editTemperatureLow.getText().toString());
        int medium = Integer.parseInt(editTemperatureMedium.getText().toString());
        int high = Integer.parseInt(editTemperatureHigh.getText().toString());
        return new ConditionConfiguration(ConditionConfiguration.Condition.TEMPERATURE, low, medium, high);
    }

    private ConditionConfiguration getLight() {
        int low = Integer.parseInt(editLightLow.getText().toString());
        int medium = Integer.parseInt(editLightMedium.getText().toString());
        int high = Integer.parseInt(editLightHigh.getText().toString());
        return new ConditionConfiguration(ConditionConfiguration.Condition.LIGHT, low, medium, high);
    }

}
