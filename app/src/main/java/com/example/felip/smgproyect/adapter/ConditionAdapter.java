package com.example.felip.smgproyect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.felip.smgproyect.R;
import com.example.felip.smgproyect.service.ConditionsResponse;

import java.util.ArrayList;

public class ConditionAdapter extends RecyclerView.Adapter<ConditionHolder> {
    private Context context;

    private ArrayList<ConditionsResponse> conditionsList;

    public ConditionAdapter(Context context, ArrayList<ConditionsResponse> list) {
        this.context = context;
        this.conditionsList = list;
    }

    @NonNull
    @Override
    public ConditionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_condition, parent, false);
        return new ConditionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConditionHolder holder, int position) {
        ConditionsResponse condition = conditionsList.get(position);
        holder.setDetails(condition);

    }

    @Override
    public int getItemCount() {
        return conditionsList.size();
    }
}
