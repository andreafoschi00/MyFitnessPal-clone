package com.example.myfintesspal_andreafoschi.Tables;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "weight", indices = {@Index(value = "profile_id", unique = true)}, foreignKeys = {
        @ForeignKey(
                entity = ProfileInformation.class,
                parentColumns = "account_id",
                childColumns = "profile_id",
                onUpdate = CASCADE,
                onDelete = CASCADE
        )
})
public class ProfileWeight {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "weight_id")
    private int id;

    @NonNull
    @ColumnInfo(name = "date")
    private final String date;

    @ColumnInfo(name = "weight")
    private int weight;

    @ColumnInfo(name = "profile_id")
    private final int profile_id;


    public ProfileWeight(@NonNull String date, int weight, int profile_id) {
        this.date = date;
        this.weight = weight;
        this.profile_id = profile_id;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getProfile_id() {
        return profile_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
