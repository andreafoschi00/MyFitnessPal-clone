package com.example.myfintesspal_andreafoschi.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.myfintesspal_andreafoschi.ProfileInformation;

import java.text.ParseException;
import java.util.List;

public class ProfileInformationRepository {

    private final ProfileInformationDAO profileInformationDAO;
    private final LiveData<List<ProfileInformation>> profileInformationList;
    private int profileID;
    private int profileGoal;

    public ProfileInformationRepository(Application application) throws ParseException {
        MyFitnessPalDatabase db = MyFitnessPalDatabase.getDatabase(application);
        profileInformationDAO = db.profileInformationDAO();
        profileInformationList = profileInformationDAO.getAllProfilesCredentials();
    }

    public LiveData<List<ProfileInformation>> getProfileInformationList() {
        return profileInformationList;
    }

    public void addProfile(ProfileInformation profile){
        MyFitnessPalDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                profileInformationDAO.addProfile(profile);
            }
        });
    }

    public int getProfileIDFromMail(String email){
        MyFitnessPalDatabase.executor.execute(new Runnable() {
            @Override
            public void run() { profileID = profileInformationDAO.getAccountIDFromMail(email); }
        });
        return profileID;
    }

    public int getProfileGoalFromMail(String email){
        MyFitnessPalDatabase.executor.execute(new Runnable() {
            @Override
            public void run() { profileGoal = profileInformationDAO.getGoalFromMail(email); }
        });
        return profileGoal;
    }
}
