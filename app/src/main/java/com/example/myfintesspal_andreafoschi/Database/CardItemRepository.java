package com.example.myfintesspal_andreafoschi.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.myfintesspal_andreafoschi.Tables.CardItem;
import com.example.myfintesspal_andreafoschi.Tables.ProfileWeight;

import java.text.ParseException;
import java.util.List;

public class CardItemRepository {

    private final CardItemDAO cardItemDAO;
    private final LiveData<List<CardItem>> cardItemList;

    public CardItemRepository(Application application) throws ParseException {
        MyFitnessPalDatabase db = MyFitnessPalDatabase.getDatabase(application);
        cardItemDAO = db.cardItemDAO();
        cardItemList = cardItemDAO.getCardItems();
    }

    public LiveData<List<CardItem>> getCardItemList() {
        return this.cardItemList;
    }

    public void addItem(CardItem item){
        MyFitnessPalDatabase.executor.execute(new Runnable() {
            @Override
            public void run() { cardItemDAO.addCardItem(item); }
        });
    }

    public void deleteItems(int id){
        MyFitnessPalDatabase.executor.execute(new Runnable() {
            @Override
            public void run() { cardItemDAO.deleteItems(id); }
        });
    }
}
