package com.example.myfintesspal_andreafoschi.RecyclerView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.myfintesspal_andreafoschi.Tables.CardItem;

import java.util.List;

public class CardItemDiffCallback extends DiffUtil.Callback {

    private final List<CardItem> oldCardList;
    private final List<CardItem> newCardList;

    public CardItemDiffCallback(List<CardItem> oldCardList, List<CardItem> newCardList) {
        this.oldCardList = oldCardList;
        this.newCardList = newCardList;
    }

    @Override
    public int getOldListSize() {
        return oldCardList.size();
    }

    @Override
    public int getNewListSize() {
        return newCardList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldCardList.get(oldItemPosition) == newCardList.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final CardItem oldItem = oldCardList.get(oldItemPosition);
        final CardItem newItem = newCardList.get(newItemPosition);
        return oldItem.getItem_name().equals(newItem.getItem_name()) &&
                oldItem.isCollected() == newItem.isCollected() &&
                oldItem.getProfile_id() == newItem.getProfile_id() &&
                oldItem.getId() == newItem.getId();
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
