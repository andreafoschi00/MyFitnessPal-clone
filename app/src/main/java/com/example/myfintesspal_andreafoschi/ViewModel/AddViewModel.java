package com.example.myfintesspal_andreafoschi.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.myfintesspal_andreafoschi.Database.ProfileInformationRepository;
import com.example.myfintesspal_andreafoschi.ProfileInformation;

import java.text.ParseException;

public class AddViewModel extends AndroidViewModel{

    private final ProfileInformationRepository repository;

    public AddViewModel(@NonNull Application application) throws ParseException {
        super(application);
        repository = new ProfileInformationRepository(application);
    }

    public void addProfile(ProfileInformation profile){
        repository.addProfile(profile);
    }
}
