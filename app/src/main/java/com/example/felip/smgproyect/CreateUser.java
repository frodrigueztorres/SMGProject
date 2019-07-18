package com.example.felip.smgproyect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.felip.smgproyect.data.DatabaseInstance;
import com.example.felip.smgproyect.data.SMGDatabase;
import com.example.felip.smgproyect.data.model.User;
import com.example.felip.smgproyect.helper.CryptoHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CreateUser extends AppCompatActivity {

    @BindView(R.id.txt_NameCreateUser)
    public EditText name;

    @BindView(R.id.txt_LastnameCreateUser)
    public EditText lastName;

    @BindView(R.id.txt_UsernameCreateUser)
    public EditText username;

    @BindView(R.id.txt_EmailCreateUser)
    public EditText email;

    @BindView(R.id.txt_PasswordCreateUser)
    public EditText password;

    @BindView(R.id.txt_PasswordConfirmCreateUser)
    public EditText passworConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
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

    }

    @OnClick(R.id.btn_CreateUserFromList)
    public void createUser() {
        if (areAllFieldsValid()) {
            String userName = name.getText().toString();
            String userLastName = name.getText().toString();
            String userUsername = username.getText().toString();
            String userPass = CryptoHelper.hashWithSha(password.getText().toString());
            String userEmail = email.getText().toString();
            User user = new User(userUsername, userPass, userName, userLastName, userEmail);
            insertUser(user);
        } else {
            return;
        }
    }

    private void insertUser(User user) {
        SMGDatabase database = DatabaseInstance.getDatabaseInstance(getApplicationContext());
        Observable.fromCallable(() -> database.userDao().insert(user))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(this::userCreationSuccess)
                .doOnError(t -> Toasty.error(getApplicationContext(), "Error: " + t.getMessage()))
                .subscribe();
    }

    private void userCreationSuccess() {
        Toasty.success(getApplicationContext(), "Usuario creado con éxito").show();
        Intent i = new Intent(CreateUser.this, ListCreatedUsers.class);
        startActivity(i);
    }

    private boolean areAllFieldsValid() {
        if (username.getText().toString().length() == 0) {
            Toasty.error(getApplicationContext(), "El nombre de usuario debe tener al menos 1 caracter").show();
            return false;
        } else if (name.getText().toString().length() == 0) {
            Toasty.error(getApplicationContext(), "El nombre debe tener al menos 1 caracter").show();
            return false;
        } else if (password.getText().toString().length() < 6) {
            Toasty.error(getApplicationContext(), "La contraseña debe tener al menos 6 caracteres").show();
            return false;
        } else if (email.getText().toString().length() < 3) {
            Toasty.error(getApplicationContext(), "El correo electrónico debe tener al menos 3 caracteres").show();
            return false;
        } else if (lastName.getText().toString().length() == 0) {
            Toasty.error(getApplicationContext(), "El apellido debe tener al menos 1 caracter").show();
            return false;
        } else if (!password.getText().toString().equals(passworConfirm.getText().toString())) {
            Toasty.error(getApplicationContext(), "Las contraseñas no coinciden").show();
            return false;
        }
        return true;
    }

}

