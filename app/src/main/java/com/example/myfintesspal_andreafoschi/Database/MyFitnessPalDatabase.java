package com.example.myfintesspal_andreafoschi.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myfintesspal_andreafoschi.ProfileDailyCalories;
import com.example.myfintesspal_andreafoschi.ProfileInformation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ProfileInformation.class, ProfileDailyCalories.class}, version = 3)
public abstract class MyFitnessPalDatabase extends RoomDatabase {

    public abstract ProfileInformationDAO profileInformationDAO();
    public abstract ProfileDailyCaloriesDAO profileDailyCaloriesDAO();

    private static volatile MyFitnessPalDatabase INSTANCE;

    static final ExecutorService executor = Executors.newFixedThreadPool(4);

    static MyFitnessPalDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (MyFitnessPalDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyFitnessPalDatabase.class, "myfitnesspal_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
