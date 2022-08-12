package com.example.myfintesspal_andreafoschi.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myfintesspal_andreafoschi.Database.ProfileInformationRepository;
import com.example.myfintesspal_andreafoschi.ProfileInformation;

import java.text.ParseException;
import java.util.List;

public class ListViewModel extends AndroidViewModel {

    public LiveData<List<ProfileInformation>> profilesInfo;

    public ListViewModel(@NonNull Application application) throws ParseException {
        super(application);
        ProfileInformationRepository repository = new ProfileInformationRepository(application);
        profilesInfo = repository.getProfileInformationList();
    }

    public LiveData<List<ProfileInformation>> getProfilesInfo() {
        return profilesInfo;
    }
}
