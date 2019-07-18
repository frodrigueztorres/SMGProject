package com.example.felip.smgproyect.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "condition_configuration")
public class ConditionConfiguration {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "condition")
    @TypeConverters(ConditionConverter.class)
    public Condition condition;

    @ColumnInfo(name = "low")
    public int low;

    @ColumnInfo(name = "medium")
    public int medium;

    @ColumnInfo(name = "high")
    public int high;

    public ConditionConfiguration(Condition condition, int low, int medium, int high) {
        this.condition = condition;
        this.low = low;
        this.medium = medium;
        this.high = high;
    }

    public enum Condition {
        FLOOR_HUMIDITY(0),
        AMBIENT_HUMIDITY(1),
        TEMPERATURE(2),
        LIGHT(3);

        private int code;

        Condition(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
