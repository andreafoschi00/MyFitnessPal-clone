package com.example.myfintesspal_andreafoschi.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.myfintesspal_andreafoschi.ProfileInformation;

import java.text.ParseException;
import java.util.List;

public class ProfileInformationRepository {

    private final ProfileInformationDAO profileInformationDAO;
    private final LiveData<List<ProfileInformation>> profileInformationList;

    public ProfileInformationRepository(Application application) throws ParseException {
        ProfileInformationDatabase db = ProfileInformationDatabase.getDatabase(application);
        profileInformationDAO = db.profileInformationDAO();
        profileInformationList = profileInformationDAO.getAllProfilesCredentials();
    }

    public LiveData<List<ProfileInformation>> getProfileInformationList() {
        return profileInformationList;
    }

    public void addProfile(ProfileInformation profile){
        ProfileInformationDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                profileInformationDAO.addProfile(profile);
            }
        });
    }
}
