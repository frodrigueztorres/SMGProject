package com.example.felip.smgproyect;

import android.content.Intent;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IndicatorsMenu extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicators_menu);
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

        View indicatorHS = (View) findViewById(R.id.vw_IndicatorHS);
        View indicatorTemp = (View) findViewById(R.id.vw_IndicatorTemp);
        View indicatorLight = (View) findViewById(R.id.vw_IndicatorLight);
        View indicatorHr = (View) findViewById(R.id.vw_IndicatorHR);
        Button btnBack = (Button) findViewById(R.id.btn_InterfaceBack);
        Button btnImport = (Button) findViewById(R.id.btn_ImportIndicators);

        indicatorHS.setOnClickListener(this);
        indicatorTemp.setOnClickListener(this);
        indicatorLight.setOnClickListener(this);
        indicatorHr.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnImport.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.vw_IndicatorHS:
                i = new Intent(IndicatorsMenu.this, DetailRegistryHS.class);
                startActivity(i);
                break;
            case R.id.vw_IndicatorTemp:
                i = new Intent(IndicatorsMenu.this, DetailRegistryHS.class);
                startActivity(i);
                break;
            case R.id.vw_IndicatorLight:
                i = new Intent(IndicatorsMenu.this, DetailRegistryHS.class);
                startActivity(i);
                break;
            case R.id.vw_IndicatorHR:
                i = new Intent(IndicatorsMenu.this, DetailRegistryHS.class);
                startActivity(i);
                break;
            case R.id.btn_InterfaceBack:
                i = new Intent(IndicatorsMenu.this, AdminMenu.class);
                startActivity(i);
                break;
            case R.id.btn_ImportIndicators:
                i = new Intent(IndicatorsMenu.this, DetailRegistryHS.class);
                startActivity(i);
                break;
        }
    }
}
