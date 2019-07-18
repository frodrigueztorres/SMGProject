package com.example.felip.smgproyect.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.felip.smgproyect.AdminMenu;
import com.example.felip.smgproyect.R;
import com.example.felip.smgproyect.data.DatabaseInstance;
import com.example.felip.smgproyect.data.SMGDatabase;
import com.example.felip.smgproyect.data.model.User;
import com.example.felip.smgproyect.helper.CryptoHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class IntroMenu extends AppCompatActivity {

    @BindView(R.id.txtUsername)
    EditText userName;

    @BindView(R.id.txtPassword)
    EditText userPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_menu);
        ButterKnife.bind(this);
        View decorView = getWindow().getDecorView();
        SMGDatabase database = DatabaseInstance.getDatabaseInstance(getApplicationContext());
        // THis is just to initialize the database and create the default user
        database.userDao().getUserByUsernameAndPassword("", "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void checkUser(String username, String password) {
        SMGDatabase database = DatabaseInstance.getDatabaseInstance(getApplicationContext());
        database.userDao().getUserByUsernameAndPassword(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(this::loginSuccess)
                .doOnComplete(this::showLoginFailed)
                .doOnError(t -> Log.e("error", "Error: " + t.getMessage()))
                .subscribe();
    }


    @OnClick(R.id.btnLogin)
    public void checkLogin() {
        checkUser(userName.getText().toString(),
                CryptoHelper.hashWithSha(userPassword.getText().toString()));
    }

    private void loginSuccess(User user) {
        Intent intent = new Intent(IntroMenu.this, AdminMenu.class);
        startActivity(intent);
        Toasty.success(getApplicationContext(), getString(R.string.success_user, user.username)).show();
    }

    private void showLoginFailed() {
        Toasty.error(getApplicationContext(), getString(R.string.error_user)).show();
    }

}
