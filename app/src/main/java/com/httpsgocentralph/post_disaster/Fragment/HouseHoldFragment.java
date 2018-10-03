package com.httpsgocentralph.post_disaster.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.httpsgocentralph.post_disaster.Database.DatabaseHelper;
import com.httpsgocentralph.post_disaster.Entity.Helper;
import com.httpsgocentralph.post_disaster.R;

import static android.content.ContentValues.TAG;

public class HouseHoldFragment extends Fragment {
    EditText firstName;
    EditText lastName;
    EditText age;
    EditText address;
    EditText mobileNumber;
    Spinner gender;
    Spinner civilStatus;
    Spinner type;
    Button save;
    View view;
    DatabaseHelper db;

    String sFirstName, sLastName, sAge, sAddress, sMobileNumber, sGender, sCivilStatus, sType;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_household, container, false);

        db = new DatabaseHelper(view.getContext());
        firstName = (EditText)view.findViewById(R.id.firstName);
        lastName = (EditText)view.findViewById(R.id.lastName);
        age = (EditText)view.findViewById(R.id.age);
        address = (EditText)view.findViewById(R.id.address);
        mobileNumber = (EditText)view.findViewById(R.id.mobileNumber);

        gender = (Spinner)view.findViewById(R.id.gender);
        civilStatus = (Spinner)view.findViewById(R.id.civilStatus);
        type = (Spinner)view.findViewById(R.id.type);

        save = (Button)view.findViewById(R.id.saveBtnH);

        ArrayAdapter<CharSequence> adapterG = ArrayAdapter.createFromResource(view.getContext(), R.array.gender, android.R.layout.simple_spinner_item);
        adapterG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapterG);

        ArrayAdapter<CharSequence> adapterC = ArrayAdapter.createFromResource(view.getContext(), R.array.civil_status, android.R.layout.simple_spinner_item);
        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        civilStatus.setAdapter(adapterC);

        ArrayAdapter<CharSequence> adapterT = ArrayAdapter.createFromResource(view.getContext(), R.array.household_type, android.R.layout.simple_spinner_item);
        adapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapterT);
        genderListener();
        saveListener();

        return view;
    }

    private void genderListener(){
    }

    private void saveListener() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "SAVING!");
                save();
            }
        });

    }

    private void save(){
        init();
        if(validate()){
            ContentValues contentValues = new ContentValues();
            contentValues.put("first_name", sFirstName);
            contentValues.put("last_name", sLastName);
            contentValues.put("age", sAge);
            contentValues.put("mobile_number", sMobileNumber);
            contentValues.put("address", sAddress);
            contentValues.put("gender", sGender);
            contentValues.put("civil_status", sCivilStatus);
            contentValues.put("type", sType);
            contentValues.put("relation", "");
            contentValues.put("status", "household");

            if(db.insert(Helper.TB_HOUSEHOLDS, contentValues)){
                Helper.alert(Helper.DB_INSERT_SUCCESS_TITLE, Helper.DB_INSERT_SUCCESS_MESSAGE,view.getContext());
                Cursor res = db.retrieve(Helper.TB_HOUSEHOLDS, null);
                if(res.getCount() == 0){
                    Log.d(TAG, "Household is Empty");
                }else{
                    Log.d(TAG, "RES = " + res);
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()){
                        Log.d(TAG, "First Name: " + res.getString(1));
                    }
                }

            }else{
                Helper.alert(Helper.ERROR_INPUT_TITLE, Helper.DB_INSERT_ERROR_MESSAGE, view.getContext());
            }
        }
    }

    private boolean validate(){

        if(sFirstName.equals("") || sLastName.equals("") || sAge.equals("") || sAddress.equals(""
        ) || sMobileNumber.equals("") || sGender.equals("") || sCivilStatus.equals("") || sType.equals("")){
            Helper.alert(Helper.ERROR_INPUT_TITLE, Helper.ERROR_INPUT_MESSAGE, view.getContext());
            Log.d(TAG, "Error");
            return false;
        }else{
            Log.d(TAG, "No Error");
            return true;
        }
    }

    private void init(){
        sFirstName = firstName.getText().toString().trim();
        sLastName = lastName.getText().toString().trim();
        sAge = age.getText().toString().trim();
        sAddress = address.getText().toString().trim();
        sMobileNumber = mobileNumber.getText().toString().trim();
        sGender = gender.getSelectedItem().toString().trim();
        sCivilStatus = civilStatus.getSelectedItem().toString().trim();
        sType = type.getSelectedItem().toString().trim();
    }


}
