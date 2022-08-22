package com.example.myfintesspal_andreafoschi.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.myfintesspal_andreafoschi.ProfileWeight;

import java.util.List;


@Dao
public interface ProfileWeightDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addProfileWeight(ProfileWeight profileWeight);

    @Transaction
    @Query("SELECT * FROM weight ORDER BY date")
    LiveData<List<ProfileWeight>> getAllWeights();

    @Transaction
    @Query("UPDATE weight SET weight = :weight WHERE profile_id = :id AND date = :date")
    void updateProfileWeight(int weight, int id, String date);

}
