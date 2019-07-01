package com.example.felip.smgproyect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminMenu extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
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

        ImageView indicatorView = (ImageView) findViewById(R.id.iw_btnAdminIndicators);
        ImageView reportView = (ImageView) findViewById(R.id.iw_btnAdminReports);
        ImageView userView = (ImageView) findViewById(R.id.iw_btnAdminUsers);

        indicatorView.setOnClickListener(this);
        reportView.setOnClickListener(this);
        userView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.iw_btnAdminIndicators:
                i = new Intent(AdminMenu.this, IndicatorsMenu.class);startActivity(i);
                break;
            case R.id.iw_btnAdminReports:
                /*i = new Intent(AdminMenu.this, IndicatorsMenu.class);startActivity(i);*/
                break;
            case R.id.iw_btnAdminUsers:
                i = new Intent(AdminMenu.this, ListCreatedUsers.class);startActivity(i);
                break;
        }
    }
}

