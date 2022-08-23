package com.example.myfintesspal_andreafoschi.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfintesspal_andreafoschi.R;
import com.example.myfintesspal_andreafoschi.Tables.CardItem;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder>  {

    private List<CardItem> cardItemList = new ArrayList<>();

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,
                parent, false);
        return new CardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardItem item = cardItemList.get(position);
        holder.checkBox.setChecked(item.isCollected());
        holder.textView.setText(item.getItem_name());
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }

    public void setData(List<CardItem> list){
        final CardItemDiffCallback diffCallback =
                new CardItemDiffCallback(this.cardItemList, list);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.cardItemList = new ArrayList<>(list);

        diffResult.dispatchUpdatesTo(this);

    }

}
