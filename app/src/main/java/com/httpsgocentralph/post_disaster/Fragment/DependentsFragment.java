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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.httpsgocentralph.post_disaster.Database.DatabaseHelper;
import com.httpsgocentralph.post_disaster.Entity.Account;
import com.httpsgocentralph.post_disaster.Entity.Helper;
import com.httpsgocentralph.post_disaster.Entity.Household;
import com.httpsgocentralph.post_disaster.R;
import com.httpsgocentralph.post_disaster.Utils.CustomSharedPreference;

import java.sql.Timestamp;

import static android.content.ContentValues.TAG;

public class DependentsFragment extends Fragment {
    EditText firstName;
    EditText lastName;
    EditText age;
    Spinner gender, relation, list;
    Button saveBtnFD;
    View view;

    DatabaseHelper db;

    String sFirstName,sLastName, sAge, sGender, sRelation;
    Household[] households;

    Account account;

    CustomSharedPreference sharedpreferences;
    public Gson gson;
    GsonBuilder gsonBuilder;
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
        list = (Spinner)view.findViewById(R.id.familyListDependents);
        saveBtnFD = (Button)view.findViewById(R.id.saveBtnFD);

        sharedpreferences = new CustomSharedPreference(view.getContext());
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        account = gson.fromJson(sharedpreferences.getAccountData(), Account.class);

        initFamilyList();

        ArrayAdapter<CharSequence> adapterG = ArrayAdapter.createFromResource(view.getContext(), R.array.gender, android.R.layout.simple_spinner_item);
        adapterG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapterG);

        ArrayAdapter<CharSequence> adapterR = ArrayAdapter.createFromResource(view.getContext(), R.array.relation, android.R.layout.simple_spinner_item);
        adapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relation.setAdapter(adapterR);

        btnListener();
        return view;
    }

    private void initFamilyList() {
        String condition = "(account_id = '" + account.getId() + "' and under = '" + "" + "')";
        String sort = "created_at desc";
        Cursor res = db.retrieve(Helper.TB_HOUSEHOLDS, condition, sort);
        if(res.getCount() == 0 ){
            Log.d(TAG, "initFamilyList: Empty");
        }else{
            int i = 0;
            households = new Household[res.getCount()];
            String[] array = new String[res.getCount()];
            while (res.moveToNext()){
                households[i] = new Household(
                        res.getString(0),
                        res.getString(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getString(6),
                        res.getString(7),
                        res.getString(8),
                        res.getString(9),
                        res.getString(10),
                        res.getString(11),
                        res.getString(12),
                        res.getString(13),
                        res.getString(14),
                        res.getString(15)
                );
                array[i] = households[i].getFirstName() + " " + households[i].getLastName();
                i++;
            }
            ArrayAdapter<CharSequence> familyListAdapter = new ArrayAdapter<CharSequence>(view.getContext(), android.R.layout.simple_dropdown_item_1line, array);
            familyListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            list.setAdapter(familyListAdapter);
        }
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
            int position = list.getSelectedItemPosition();
            ContentValues contentValues = new ContentValues();
            contentValues.put("account_id", account.getId());
            contentValues.put("under", households[position].getId());
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
            contentValues.put("created_at", String.valueOf(new Timestamp(System.currentTimeMillis())));
            contentValues.put("updated_at", "");
            contentValues.put("deleted_at", "");
            long result = db.insert(Helper.TB_HOUSEHOLDS, contentValues);
            if(result != -1){
                Helper.alert(Helper.DB_INSERT_SUCCESS_TITLE, Helper.DB_INSERT_SUCCESS_MESSAGE,view.getContext());
                Cursor res = db.retrieve(Helper.TB_HOUSEHOLDS, "", "first_name ASC");
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
