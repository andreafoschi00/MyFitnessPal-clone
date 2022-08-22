package com.example.myfintesspal_andreafoschi.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.myfintesspal_andreafoschi.Database.ProfileDailyCaloriesRepository;
import com.example.myfintesspal_andreafoschi.Database.ProfileInformationRepository;
import com.example.myfintesspal_andreafoschi.Database.ProfileWeightRepository;
import com.example.myfintesspal_andreafoschi.ProfileDailyCalories;
import com.example.myfintesspal_andreafoschi.ProfileInformation;
import com.example.myfintesspal_andreafoschi.ProfileWeight;

import java.text.ParseException;

public class AddViewModel extends AndroidViewModel{

    private final ProfileInformationRepository repository;
    private final ProfileDailyCaloriesRepository caloriesRepository;
    private final ProfileWeightRepository weightRepository;

    public AddViewModel(@NonNull Application application) throws ParseException {
        super(application);
        repository = new ProfileInformationRepository(application);
        caloriesRepository = new ProfileDailyCaloriesRepository(application);
        weightRepository = new ProfileWeightRepository(application);
    }

    public void addProfile(ProfileInformation profile){
        repository.addProfile(profile);
    }

    public void addCalories(ProfileDailyCalories calories) {
        caloriesRepository.addCalories(calories);
    }

    public void addWeight(ProfileWeight weight) { weightRepository.addWeight(weight);}

    public void updateBreakfast(int calories, int id, String date) {
        caloriesRepository.updateBreakfast(calories, id, date);
    }

    public void updateLunch(int calories, int id, String date) {
        caloriesRepository.updateLunch(calories, id, date);
    }

    public void updateSnack(int calories, int id, String date) {
        caloriesRepository.updateSnack(calories, id, date);
    }

    public void updateDinner(int calories, int id, String date) {
        caloriesRepository.updateDinner(calories, id, date);
    }

    public void updateTraining(int calories, int id, String date) {
        caloriesRepository.updateTraining(calories, id, date);
    }

    public void updateCaloriesLeft(int calories, int id, String date) {
        caloriesRepository.updateCaloriesLeft(calories, id, date);
    }

    public void updateWeight(int weight, int id, String date){
        weightRepository.updateWeight(weight, id, date);
    }

    public void updatePassword(String newPassword, int id) {
        repository.updatePassword(newPassword, id);
    }
}
