package com.example.myfintesspal_andreafoschi.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.myfintesspal_andreafoschi.ProfileDailyCalories;

import java.util.List;

@Dao
public interface ProfileDailyCaloriesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCalories(ProfileDailyCalories calories);

    @Transaction
    @Query("SELECT * FROM calories ORDER BY date DESC")
    LiveData<List<ProfileDailyCalories>> getAllCalories();

    @Transaction
    @Query("UPDATE calories SET breakfast_cal = :calories WHERE profile_id = :id AND date = :date")
    void updateBreakfast(int calories, int id, String date);

    @Transaction
    @Query("UPDATE calories SET lunch_cal = :calories WHERE profile_id = :id AND date = :date")
    void updateLunch(int calories, int id, String date);

    @Transaction
    @Query("UPDATE calories SET snack_cal = :calories WHERE profile_id = :id AND date = :date")
    void updateSnack(int calories, int id, String date);

    @Transaction
    @Query("UPDATE calories SET dinner_cal = :calories WHERE profile_id = :id AND date = :date")
    void updateDinner(int calories, int id, String date);

    @Transaction
    @Query("UPDATE calories SET training_cal = :calories WHERE profile_id = :id AND date = :date")
    void updateTraining(int calories, int id, String date);

    @Transaction
    @Query("UPDATE calories SET daily_cal_left = :calories WHERE profile_id = :id AND date = :date")
    void updateCaloriesLeft(int calories, int id, String date);
}
