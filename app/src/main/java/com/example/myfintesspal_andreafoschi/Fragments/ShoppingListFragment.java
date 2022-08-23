package com.example.myfintesspal_andreafoschi.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfintesspal_andreafoschi.Activities.MainActivity;
import com.example.myfintesspal_andreafoschi.R;
import com.example.myfintesspal_andreafoschi.RecyclerView.CardAdapter;
import com.example.myfintesspal_andreafoschi.Tables.CardItem;
import com.example.myfintesspal_andreafoschi.Utils.Utilities;
import com.example.myfintesspal_andreafoschi.ViewModel.AddViewModel;
import com.example.myfintesspal_andreafoschi.ViewModel.ListViewModel;

import java.util.List;
import java.util.Objects;

public class ShoppingListFragment extends Fragment {

    private CardAdapter adapter;

    private AddViewModel addViewModel;

    private EditText txt;

    private Button clear;

    private int id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shopping, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();
        if(activity != null){
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.shopping_list));
            setRecyclerView(activity);
            EditText editText = view.findViewById(R.id.shopping_title);
            clear = view.findViewById(R.id.clear_list);
            clear.setVisibility(View.INVISIBLE);

            SharedPreferences sharedPreferences = activity.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
            String pref_title = sharedPreferences.getString("title", "default");
            String pref_id = sharedPreferences.getString("id", "default");
            if(!pref_title.equals("default")){
                editText.setText(pref_title);
            }
            if(!pref_id.equals("default")){
                id = Integer.parseInt(pref_id);
            }

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("title", String.valueOf(editable));
                    editor.apply();
                }
            });

            addViewModel = new ViewModelProvider((ViewModelStoreOwner) activity)
                    .get(AddViewModel.class);

            ListViewModel listViewModel = new ViewModelProvider(activity).get(ListViewModel.class);
            listViewModel.getCardItems().observe(activity, new Observer<List<CardItem>>() {
                @Override
                public void onChanged(List<CardItem> cardItems) {
                    adapter.setData(cardItems);
                    if(adapter.getItemCount() > 0)
                        clear.setVisibility(View.VISIBLE);
                }
            });

            view.findViewById(R.id.fab_new_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    final EditText placeText = new EditText(getContext());
                    placeText.setHint(R.string.example_item);
                    builder.setTitle(R.string.item_title);
                    builder.setView(placeText);
                    LinearLayout layoutName = new LinearLayout(getContext());
                    layoutName.setOrientation(LinearLayout.VERTICAL);
                    layoutName.addView(placeText);
                    builder.setView(layoutName);
                    builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            txt = placeText;
                            collectInputItem();
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });

            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage(R.string.logout_msg)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SharedPreferences sharedPreferences = activity.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
                                    sharedPreferences.edit().remove("title").apply();
                                    editText.setText("");
                                    addViewModel.deleteItems(id);
                                    Toast.makeText(getContext(), R.string.items_cleared, Toast.LENGTH_SHORT).show();
                                    clear.setVisibility(View.INVISIBLE);
                                }
                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .create()
                            .show();
                }
            });
        }
    }

    private void collectInputItem() {
        String getInput = txt.getText().toString();
        if (getInput.trim().equals("")){
            Toast.makeText(getContext(), R.string.error_item, Toast.LENGTH_LONG).show();
        } else {
            addItem(getInput);
        }
    }

    private void addItem(String name) {
        addViewModel.addCardItem(new CardItem(name, false, id));
        Toast.makeText(getContext(), R.string.item_added, Toast.LENGTH_SHORT).show();
    }

    private void setRecyclerView(Activity activity) {
        RecyclerView recyclerView = activity.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
        adapter = new CardAdapter();
        recyclerView.setAdapter(adapter);
    }
}
