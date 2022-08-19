package com.example.myfintesspal_andreafoschi.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.myfintesspal_andreafoschi.ProfileInformation;

import java.text.ParseException;
import java.util.List;

@Dao
public interface ProfileInformationDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addProfile(ProfileInformation profile);

    @Transaction
    @Query("SELECT * FROM account ORDER BY account_id DESC")
    LiveData<List<ProfileInformation>> getAllProfilesCredentials() throws ParseException;

    @Transaction
    @Query("SELECT account_id FROM account WHERE account_email = :email")
    int getAccountIDFromMail(String email);

    @Transaction
    @Query("SELECT account_daygoal FROM account WHERE account_email = :email")
    int getGoalFromMail(String email);
}
