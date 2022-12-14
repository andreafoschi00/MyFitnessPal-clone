package com.example.myfintesspal_andreafoschi.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myfintesspal_andreafoschi.Tables.CardItem;
import com.example.myfintesspal_andreafoschi.Tables.ProfileDailyCalories;
import com.example.myfintesspal_andreafoschi.Tables.ProfileInformation;
import com.example.myfintesspal_andreafoschi.Tables.ProfileWeight;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ProfileInformation.class, ProfileDailyCalories.class, ProfileWeight.class, CardItem.class}, version = 8)
public abstract class MyFitnessPalDatabase extends RoomDatabase {

    public abstract ProfileInformationDAO profileInformationDAO();
    public abstract ProfileDailyCaloriesDAO profileDailyCaloriesDAO();
    public abstract ProfileWeightDAO profileWeightDAO();
    public abstract CardItemDAO cardItemDAO();

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
