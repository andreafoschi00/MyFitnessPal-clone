package com.example.myfintesspal_andreafoschi.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.myfintesspal_andreafoschi.Tables.ProfileWeight;

import java.text.ParseException;
import java.util.List;

public class ProfileWeightRepository {

    private final ProfileWeightDAO profileWeightDAO;
    private final LiveData<List<ProfileWeight>> weightList;

    public ProfileWeightRepository(Application application) throws ParseException {
        MyFitnessPalDatabase db = MyFitnessPalDatabase.getDatabase(application);
        profileWeightDAO = db.profileWeightDAO();
        weightList = profileWeightDAO.getAllWeights();
    }

    public LiveData<List<ProfileWeight>> getAllWeights() {
        return this.weightList;
    }

    public void addWeight(ProfileWeight weight){
        MyFitnessPalDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                profileWeightDAO.addProfileWeight(weight);
            }
        });
    }

    public void updateWeight(int weight, int id, String date){
        MyFitnessPalDatabase.executor.execute(new Runnable() {
            @Override
            public void run() { profileWeightDAO.updateProfileWeight(weight, id, date); }
        });
    }
}
