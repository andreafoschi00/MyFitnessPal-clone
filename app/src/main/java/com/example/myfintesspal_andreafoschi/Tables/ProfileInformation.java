package com.example.myfintesspal_andreafoschi.Tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "account", indices = {@Index(value = "account_email", unique = true)})
public class ProfileInformation {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "account_id")
    private int id;

    @NonNull
    @ColumnInfo(name = "account_gender")
    private final String gender;

    @NonNull
    @ColumnInfo(name = "account_date")
    private final String birth_date;

    @ColumnInfo(name = "account_height")
    private final int height;

    @ColumnInfo(name = "account_weight")
    private int weight;

    @ColumnInfo(name = "account_daygoal")
    private int dayGoal;

    @NonNull
    @ColumnInfo(name = "account_username")
    private final String username;

    @NonNull
    @ColumnInfo(name = "account_email")
    private final String email;

    @ColumnInfo(name = "account_password")
    private String password;

    public ProfileInformation(@NonNull String gender, @NonNull String birth_date, int height, int weight,
                              @NonNull String username, @NonNull String email, String password) throws ParseException {
        this.gender = gender;
        this.birth_date = birth_date;
        this.height = height;
        this.weight = weight;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dayGoal = calculateGoal(gender, birth_date, height, weight);
    }

    /**
     * Calculation based on Harris-Benedict formula.
     * @param gender gender
     * @param birth_date birth date
     * @param height height
     * @param weight weight
     * @return goal
     */
    public static int calculateGoal(String gender, String birth_date, int height, int weight) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALY);
        LocalDate date = LocalDate.parse(birth_date, formatter);
        Period period = Period.between(date, LocalDate.now());
        int age = period.getYears();
        double goal = 0;
        switch (gender){
            case "Male":
                goal = 66.5 + (13.75*weight) + (5.003*height) - (6.775*age);
            break;
            case "Female":
                goal = 655.1 + (9.5663*weight) + (1.85*height) - (4.676*age);
        }
        return (int) Math.round(goal);
    }

    public int getId() { return id; }

    @NonNull
    public String getGender() { return gender; }

    @NonNull
    public String getBirth_date() { return birth_date; }

    public int getHeight() { return height; }

    public int getWeight() { return weight; }

    public int getDayGoal() { return dayGoal; }

    @NonNull
    public String getUsername() { return username; }

    @NonNull
    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public void setId(int id){ this.id = id; }

    public void setWeight(int weight){ this.weight = weight; }

    public void setPassword(String password){ this.password = password; }

    public void setDayGoal(int goal){ this.dayGoal = goal; }
}
