package com.example.felip.smgproyect.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.felip.smgproyect.data.model.User;
import com.example.felip.smgproyect.helper.CryptoHelper;

import java.util.concurrent.Executors;

public class DatabaseInstance {
    private static SMGDatabase smgDatabase;

    public static SMGDatabase getDatabaseInstance(Context context) {
        if(smgDatabase == null) {
            smgDatabase = Room.databaseBuilder(context.getApplicationContext(), SMGDatabase.class, "smg_database")
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            String password = CryptoHelper.hashWithSha("root");
                            User user = new User("root", password);
                            Executors.newSingleThreadExecutor().execute(() ->
                                    getDatabaseInstance(context)
                                            .userDao()
                                            .insert(user));
                        }
                    })
                    .build();
        }
        return smgDatabase;
    }
}
