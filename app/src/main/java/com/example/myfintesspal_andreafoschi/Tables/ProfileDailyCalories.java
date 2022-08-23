package com.example.myfintesspal_andreafoschi.Tables;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "calories", indices = {@Index(value = {"date", "profile_id"}, unique = true)}, foreignKeys = {
        @ForeignKey(
                entity = ProfileInformation.class,
                parentColumns = "account_id",
                childColumns = "profile_id",
                onUpdate = CASCADE,
                onDelete = CASCADE
        )
})
public class ProfileDailyCalories {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "calories_id")
    private int id;

    @NonNull
    @ColumnInfo(name = "date")
    private final String date;

    @ColumnInfo(name = "breakfast_cal")
    private int breakfast_cal;

    @ColumnInfo(name = "lunch_cal")
    private int lunch_cal;

    @ColumnInfo(name = "snack_cal")
    private int snack_cal;

    @ColumnInfo(name = "dinner_cal")
    private int dinner_cal;

    @ColumnInfo(name = "training_cal")
    private int training_cal;

    @ColumnInfo(name = "daily_cal_left")
    private int daily_cal_left;

    @ColumnInfo(name = "profile_id")
    private final int account_id;

    public ProfileDailyCalories(@NonNull String date, int daily_cal_left, int account_id) {
        this.date = date;
        this.breakfast_cal = 0;
        this.lunch_cal = 0;
        this.snack_cal = 0;
        this.dinner_cal = 0;
        this.training_cal = 0;
        this.daily_cal_left = daily_cal_left;
        this.account_id = account_id;
    }

    public int getBreakfast_cal() {
        return breakfast_cal;
    }

    public void setBreakfast_cal(int breakfast_cal) {
        this.breakfast_cal = breakfast_cal;
    }

    public int getLunch_cal() {
        return lunch_cal;
    }

    public void setLunch_cal(int lunch_cal) {
        this.lunch_cal = lunch_cal;
    }

    public int getSnack_cal() {
        return snack_cal;
    }

    public void setSnack_cal(int snack_cal) {
        this.snack_cal = snack_cal;
    }

    public int getDinner_cal() {
        return dinner_cal;
    }

    public void setDinner_cal(int dinner_cal) {
        this.dinner_cal = dinner_cal;
    }

    public int getTraining_cal() {
        return training_cal;
    }

    public void setTraining_cal(int training_cal) {
        this.training_cal = training_cal;
    }

    public int getDaily_cal_left() {
        return daily_cal_left;
    }

    public void setDaily_cal_left(int daily_cal_left) {
        this.daily_cal_left = daily_cal_left;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public int getAccount_id() {
        return account_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
