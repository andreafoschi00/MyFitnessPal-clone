package com.example.myfintesspal_andreafoschi.Tables;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopping_list", indices = {@Index(value = {"item_name", "profile_id"}, unique = true)}, foreignKeys = {
        @ForeignKey(
                entity = ProfileInformation.class,
                parentColumns = "account_id",
                childColumns = "profile_id",
                onUpdate = CASCADE,
                onDelete = CASCADE
        )
})
public class CardItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    private int id;

    @NonNull
    @ColumnInfo(name = "item_name")
    private String item_name;

    @ColumnInfo(name = "collected")
    private boolean collected;

    @ColumnInfo(name = "profile_id")
    private final int profile_id;

    public CardItem(@NonNull String item_name, boolean collected, final int profile_id) {
        this.item_name = item_name;
        this.collected = collected;
        this.profile_id = profile_id;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    @NonNull
    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(@NonNull String item_name) {
        this.item_name = item_name;
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
