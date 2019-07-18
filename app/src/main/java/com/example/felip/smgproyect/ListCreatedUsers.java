package com.example.felip.smgproyect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.felip.smgproyect.adapter.UserAdapter;
import com.example.felip.smgproyect.data.DatabaseInstance;
import com.example.felip.smgproyect.data.SMGDatabase;
import com.example.felip.smgproyect.data.model.User;
import com.example.felip.smgproyect.ui.login.IntroMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListCreatedUsers extends AppCompatActivity {

    @BindView(R.id.rv_ListCreatedUsers)
    RecyclerView recyclerViewUsers;

    private ArrayList<User> userList;

    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_created_users);
        ButterKnife.bind(this);

        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();

        userAdapter = new UserAdapter(this, userList);
        recyclerViewUsers.setAdapter(userAdapter);

        getAllUsers();

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

    private void getAllUsers() {
        SMGDatabase database = DatabaseInstance.getDatabaseInstance(getApplicationContext());
        Log.i("", "Estamos viendo esto");
        database.userDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(this::populateUsers)
                .doOnComplete(() -> Toasty.info(getApplicationContext(), "No se encuentran users en la base de datos").show())
                .doOnError(t -> Log.e("Users", "Error al buscar usuarios: " + t.getMessage()))
                .subscribe();

    }

    private void populateUsers(List<User> users) {
        userList.addAll(users);
        userAdapter.notifyDataSetChanged();

    }

    @OnClick(R.id.btn_CreateUserFromList)
    public void goToCreateUser() {
        Intent i = new Intent(ListCreatedUsers.this, CreateUser.class);
        startActivity(i);
    }

    @OnClick(R.id.btn_BackListUsers)
    public void goBack() {
        Intent i = new Intent(ListCreatedUsers.this, AdminMenu  .class);
        startActivity(i);
    }
}
