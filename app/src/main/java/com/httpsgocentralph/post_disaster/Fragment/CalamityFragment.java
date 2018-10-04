package com.httpsgocentralph.post_disaster.Fragment;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.httpsgocentralph.post_disaster.Account;
import com.httpsgocentralph.post_disaster.Database.DatabaseHelper;
import com.httpsgocentralph.post_disaster.Entity.Helper;
import com.httpsgocentralph.post_disaster.R;

import static android.content.ContentValues.TAG;

public class CalamityFragment extends Fragment {
    Spinner calamity;
    EditText date;
    Spinner damageStatus;
    EditText damageAmount;
    Button next;
    View view;

    String sCalamity, sDate, sDamageStatus, sDamageAmount;

    DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calamity, container, false);
        db = new DatabaseHelper(view.getContext());
        calamity = (Spinner)view.findViewById(R.id.calamity);
        date = (EditText)view.findViewById(R.id.dateCalamity);
        damageStatus = (Spinner) view.findViewById(R.id.calamityDamage);
        damageAmount = (EditText)view.findViewById(R.id.damageAmount);
        next = (Button)view.findViewById(R.id.calamityNextBtn);

        ArrayAdapter<CharSequence> adapterC = ArrayAdapter.createFromResource(view.getContext(), R.array.calamity_type, android.R.layout.simple_spinner_item);
        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        calamity.setAdapter(adapterC);

        ArrayAdapter<CharSequence> adapterD = ArrayAdapter.createFromResource(view.getContext(), R.array.damage_type, android.R.layout.simple_spinner_item);
        adapterD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        damageStatus.setAdapter(adapterD);
        listener();
        return view;
    }

    private void listener() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save(){
        sCalamity = calamity.getSelectedItem().toString().trim();
        sDate = date.getText().toString().trim();
        sDamageStatus = damageStatus.getSelectedItem().toString().trim();
        sDamageAmount = damageAmount.getText().toString().trim();

        if(sCalamity.equals("") || sDate.equals("") || sDamageStatus.equals("")){
            Helper.alert(Helper.ERROR_INPUT_TITLE, Helper.ERROR_INPUT_MESSAGE, view.getContext());
        }else{
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", sCalamity);
            contentValues.put("date", sDate);
            contentValues.put("damage_status", sDamageStatus);
            contentValues.put("damage_amount", sDamageAmount);
            contentValues.put("status", "Created");
            if(db.insert(Helper.TB_CALAMITIES, contentValues)){
                Helper.alert(Helper.DB_INSERT_SUCCESS_TITLE, Helper.DB_INSERT_SUCCESS_MESSAGE,view.getContext());
                Cursor res = db.retrieve(Helper.TB_HOUSEHOLDS, null, "id DESC");
                if(res.getCount() == 0){
                    Log.d(TAG, "Calamity is Empty");
                }else{
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()){
                        Log.d(TAG, "Name: " + res.getString(1));
                    }
                }
                Fragment newFragment = new CalamityFamilyListFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }else{
                Helper.alert(Helper.ERROR_INPUT_TITLE, Helper.DB_INSERT_ERROR_MESSAGE, view.getContext());
            }
        }
    }
}
