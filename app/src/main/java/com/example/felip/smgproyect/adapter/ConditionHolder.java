package com.example.felip.smgproyect.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.felip.smgproyect.R;
import com.example.felip.smgproyect.service.ConditionsResponse;

public class ConditionHolder extends RecyclerView.ViewHolder {
    private TextView conditionDate, conditionValue;
    public ConditionHolder(@NonNull View itemView) {
        super(itemView);
        conditionDate = itemView.findViewById(R.id.condition_date);
        conditionValue = itemView.findViewById(R.id.condition_value);
    }

    public void setDetails(ConditionsResponse condition) {
        conditionDate.setText(condition.getDate());
        conditionValue.setText(Integer.toString(condition.getValue()));
    }
}
