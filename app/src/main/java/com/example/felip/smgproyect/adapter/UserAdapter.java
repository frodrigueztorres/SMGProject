package com.example.felip.smgproyect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.felip.smgproyect.R;
import com.example.felip.smgproyect.data.model.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserHolder> {
    private Context context;

    private ArrayList<User> userList;

    public UserAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.userList = list;
    };

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User user = userList.get(position);
        holder.setDetails(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
