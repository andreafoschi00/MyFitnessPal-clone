package com.example.myfintesspal_andreafoschi.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myfintesspal_andreafoschi.Database.ProfileDailyCaloriesRepository;
import com.example.myfintesspal_andreafoschi.Database.ProfileInformationRepository;
import com.example.myfintesspal_andreafoschi.Database.ProfileWeightRepository;
import com.example.myfintesspal_andreafoschi.Tables.ProfileDailyCalories;
import com.example.myfintesspal_andreafoschi.Tables.ProfileInformation;
import com.example.myfintesspal_andreafoschi.Tables.ProfileWeight;

import java.text.ParseException;
import java.util.List;

public class ListViewModel extends AndroidViewModel {

    public LiveData<List<ProfileInformation>> profilesInfo;
    public LiveData<List<ProfileDailyCalories>> caloriesInfo;
    public LiveData<List<ProfileWeight>> weightInfo;

    public ListViewModel(@NonNull Application application) throws ParseException {
        super(application);
        ProfileInformationRepository repository = new ProfileInformationRepository(application);
        ProfileDailyCaloriesRepository caloriesRepository = new ProfileDailyCaloriesRepository(application);
        ProfileWeightRepository weightRepository = new ProfileWeightRepository(application);
        profilesInfo = repository.getProfileInformationList();
        caloriesInfo = caloriesRepository.getCaloriesList();
        weightInfo = weightRepository.getAllWeights();
    }

    public LiveData<List<ProfileInformation>> getProfilesInfo() {
        return profilesInfo;
    }

    public LiveData<List<ProfileDailyCalories>> getCaloriesInfo() { return caloriesInfo; }

    public LiveData<List<ProfileWeight>> getWeightInfo(){ return weightInfo; }
}
