package com.example.felip.smgproyect.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.felip.smgproyect.data.model.ConditionConfiguration;
import com.example.felip.smgproyect.data.model.ConditionConverter;
import com.example.felip.smgproyect.data.model.User;

@Database(entities = {User.class, ConditionConfiguration.class}, version = 3)
@TypeConverters(ConditionConverter.class)
public abstract class SMGDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ConditionConfigurationDao configurationDao();
}
