package com.example.felip.smgproyect.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.felip.smgproyect.data.model.ConditionConfiguration;
import com.example.felip.smgproyect.data.model.User;
import com.example.felip.smgproyect.helper.CryptoHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import static com.example.felip.smgproyect.data.model.ConditionConfiguration.Condition.AMBIENT_HUMIDITY;
import static com.example.felip.smgproyect.data.model.ConditionConfiguration.Condition.FLOOR_HUMIDITY;
import static com.example.felip.smgproyect.data.model.ConditionConfiguration.Condition.LIGHT;
import static com.example.felip.smgproyect.data.model.ConditionConfiguration.Condition.TEMPERATURE;

public class DatabaseInstance {
    private static SMGDatabase smgDatabase;

    public static SMGDatabase getDatabaseInstance(Context context) {
        if (smgDatabase == null) {
            smgDatabase = Room.databaseBuilder(context.getApplicationContext(), SMGDatabase.class, "smg_database")
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            int low = 25;
                            int medium = 50;
                            int high = 75;

                            List<ConditionConfiguration> configurationList = new ArrayList<>();
                            configurationList.add(new ConditionConfiguration(LIGHT, low, medium, high));
                            configurationList.add(new ConditionConfiguration(TEMPERATURE, low, medium, high));
                            configurationList.add(new ConditionConfiguration(AMBIENT_HUMIDITY, low, medium, high));
                            configurationList.add(new ConditionConfiguration(FLOOR_HUMIDITY, low, medium, high));

                            String password = CryptoHelper.hashWithSha("root");
                            User user = new User("root", password, "root", "root", "root@root.com");
                            Executors.newSingleThreadExecutor().execute(() ->
                                    getDatabaseInstance(context)
                                            .userDao()
                                            .insert(user)
                            );
                            Executors.newSingleThreadExecutor().execute(() ->
                                    getDatabaseInstance(context)
                                            .configurationDao()
                                            .insertAll(configurationList)
                            );
                        }
                    })
                    .build();
        }
        return smgDatabase;
    }
}
