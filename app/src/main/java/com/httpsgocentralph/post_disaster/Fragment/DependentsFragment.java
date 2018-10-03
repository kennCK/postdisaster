package com.httpsgocentralph.post_disaster.Fragment;

import android.content.ContentValues;
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

public class DependentsFragment extends Fragment {
    EditText firstName;
    EditText lastName;
    EditText age;
    Spinner gender;
    Spinner relation;
    Button saveBtnFD;
    View view;

    DatabaseHelper db;

    String sFirstName,sLastName, sAge, sGender, sRelation;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dependents, container, false);
        db = new DatabaseHelper(view.getContext());
        firstName = (EditText)view.findViewById(R.id.firstNameFD);
        lastName = (EditText)view.findViewById(R.id.lastNameFD);
        age = (EditText)view.findViewById(R.id.ageFD);
        gender = (Spinner)view.findViewById(R.id.genderFD);
        relation = (Spinner)view.findViewById(R.id.relationshipFD);
        saveBtnFD = (Button)view.findViewById(R.id.saveBtnFD);

        ArrayAdapter<CharSequence> adapterG = ArrayAdapter.createFromResource(view.getContext(), R.array.gender, android.R.layout.simple_spinner_item);
        adapterG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapterG);

        ArrayAdapter<CharSequence> adapterR = ArrayAdapter.createFromResource(view.getContext(), R.array.relation, android.R.layout.simple_spinner_item);
        adapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relation.setAdapter(adapterR);

        btnListener();
        return view;
    }

    private void btnListener() {
        saveBtnFD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save(){
        sFirstName = firstName.getText().toString().trim();
        sLastName = lastName.getText().toString().trim();
        sAge = age.getText().toString().trim();
        sGender = gender.getSelectedItem().toString().trim();
        sRelation = relation.getSelectedItem().toString().trim();

        if(sFirstName.equals("") || sLastName.equals("") || sAge.equals("") || sGender.equals("") || sRelation.equals("")){
            Helper.alert(Helper.ERROR_INPUT_TITLE, Helper.ERROR_INPUT_MESSAGE, view.getContext());
        }else{
            ContentValues contentValues = new ContentValues();
            contentValues.put("first_name", sFirstName);
            contentValues.put("last_name", sLastName);
            contentValues.put("age", sAge);
            contentValues.put("mobile_number", "");
            contentValues.put("address", "");
            contentValues.put("gender", sGender);
            contentValues.put("civil_status", "");
            contentValues.put("type", "");
            contentValues.put("relation", sRelation);
            contentValues.put("status", "dependents");
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
}
