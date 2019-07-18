package com.example.felip.smgproyect.data.model;

import androidx.room.TypeConverter;

import static com.example.felip.smgproyect.data.model.ConditionConfiguration.Condition.AMBIENT_HUMIDITY;
import static com.example.felip.smgproyect.data.model.ConditionConfiguration.Condition.FLOOR_HUMIDITY;
import static com.example.felip.smgproyect.data.model.ConditionConfiguration.Condition.LIGHT;
import static com.example.felip.smgproyect.data.model.ConditionConfiguration.Condition.TEMPERATURE;

public class ConditionConverter {

    @TypeConverter
    public static ConditionConfiguration.Condition toCondition(int condition) {
        if (condition == FLOOR_HUMIDITY.getCode()) {
            return FLOOR_HUMIDITY;
        } else if (condition == AMBIENT_HUMIDITY.getCode()) {
            return AMBIENT_HUMIDITY;
        } else if (condition == TEMPERATURE.getCode()) {
            return TEMPERATURE;
        } else if (condition == LIGHT.getCode()) {
            return LIGHT;
        } else {
            throw new IllegalArgumentException("Could not recognize condition");
        }
    }

    @TypeConverter
    public static Integer toInteger(ConditionConfiguration.Condition condition) {
        return condition.getCode();
    }
}
