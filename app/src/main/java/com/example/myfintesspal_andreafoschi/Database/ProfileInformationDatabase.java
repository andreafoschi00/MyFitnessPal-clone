package com.example.myfintesspal_andreafoschi.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myfintesspal_andreafoschi.ProfileInformation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ProfileInformation.class}, version = 2)
public abstract class ProfileInformationDatabase extends RoomDatabase {

    public abstract ProfileInformationDAO profileInformationDAO();

    private static volatile ProfileInformationDatabase INSTANCE;

    static final ExecutorService executor = Executors.newFixedThreadPool(4);

    static ProfileInformationDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (ProfileInformationDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ProfileInformationDatabase.class, "myfitnesspal_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
