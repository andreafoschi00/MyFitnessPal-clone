package com.example.myfintesspal_andreafoschi.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.myfintesspal_andreafoschi.Tables.ProfileDailyCalories;

import java.text.ParseException;
import java.util.List;

public class ProfileDailyCaloriesRepository {

    private final ProfileDailyCaloriesDAO profileDailyCaloriesDAO;
    private final LiveData<List<ProfileDailyCalories>> caloriesList;

    public ProfileDailyCaloriesRepository(Application application) throws ParseException {
        MyFitnessPalDatabase db = MyFitnessPalDatabase.getDatabase(application);
        profileDailyCaloriesDAO = db.profileDailyCaloriesDAO();
        caloriesList = profileDailyCaloriesDAO.getAllCalories();
    }

    public LiveData<List<ProfileDailyCalories>> getCaloriesList() {
        return this.caloriesList;
    }

    public void addCalories(ProfileDailyCalories calories){
        MyFitnessPalDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                profileDailyCaloriesDAO.addCalories(calories);
            }
        });
    }

    public void updateBreakfast(int calories, int id, String date){
        MyFitnessPalDatabase.executor.execute(new Runnable() {
            @Override
            public void run() { profileDailyCaloriesDAO.updateBreakfast(calories, id, date); }
        });
    }

    public void updateLunch(int calories, int id, String date){
        MyFitnessPalDatabase.executor.execute(new Runnable() {
            @Override
            public void run() { profileDailyCaloriesDAO.updateLunch(calories, id, date); }
        });
    }

    public void updateSnack(int calories, int id, String date){
        MyFitnessPalDatabase.executor.execute(new Runnable() {
            @Override
            public void run() { profileDailyCaloriesDAO.updateSnack(calories, id, date); }
        });
    }

    public void updateDinner(int calories, int id, String date){
        MyFitnessPalDatabase.executor.execute(new Runnable() {
            @Override
            public void run() { profileDailyCaloriesDAO.updateDinner(calories, id, date); }
        });
    }

    public void updateTraining(int calories, int id, String date){
        MyFitnessPalDatabase.executor.execute(new Runnable() {
            @Override
            public void run() { profileDailyCaloriesDAO.updateTraining(calories, id, date); }
        });
    }

    public void updateCaloriesLeft(int calories, int id, String date){
        MyFitnessPalDatabase.executor.execute(new Runnable() {
            @Override
            public void run() { profileDailyCaloriesDAO.updateCaloriesLeft(calories, id, date); }
        });
    }
}
