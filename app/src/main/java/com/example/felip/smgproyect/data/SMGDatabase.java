package com.example.felip.smgproyect.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.felip.smgproyect.data.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class SMGDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
