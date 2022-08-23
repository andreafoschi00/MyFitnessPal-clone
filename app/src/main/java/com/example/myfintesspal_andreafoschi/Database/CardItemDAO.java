package com.example.myfintesspal_andreafoschi.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.myfintesspal_andreafoschi.Tables.CardItem;

import java.util.List;

@Dao
public interface CardItemDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addCardItem(CardItem item);

    @Transaction
    @Query("SELECT * FROM shopping_list ORDER BY item_id")
    LiveData<List<CardItem>> getCardItems();

    @Transaction
    @Query("DELETE FROM shopping_list WHERE profile_id = :id")
    void deleteItems(int id);
}
