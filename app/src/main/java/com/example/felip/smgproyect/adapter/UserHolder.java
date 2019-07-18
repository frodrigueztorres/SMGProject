package com.example.felip.smgproyect.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.felip.smgproyect.R;
import com.example.felip.smgproyect.data.model.User;

public class UserHolder extends RecyclerView.ViewHolder {
    private TextView name, lastname, username, email;
    private ImageButton imgButton;

    public UserHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.item_name);
        lastname = itemView.findViewById(R.id.item_lastname);
        username = itemView.findViewById(R.id.item_username);
        email = itemView.findViewById(R.id.item_email);
    }

    public void setDetails(User user) {
        name.setText(user.name);
        lastname.setText(user.lastname);
        username.setText(user.username);
        email.setText(user.email);

    }

}
