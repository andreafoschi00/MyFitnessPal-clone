package com.example.myfintesspal_andreafoschi.RecyclerView;

import android.graphics.Paint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfintesspal_andreafoschi.R;
import com.example.myfintesspal_andreafoschi.ViewModel.AddViewModel;

public class CardViewHolder extends RecyclerView.ViewHolder {

    CheckBox checkBox;
    TextView textView;

    AddViewModel addViewModel;


    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.checkbox_item);
        textView = itemView.findViewById(R.id.textview_item);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                else
                    textView.setPaintFlags(0);
            }
        });
    }
}
