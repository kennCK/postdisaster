package com.httpsgocentralph.post_disaster.Fragment;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.TypedArrayUtils;
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

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

public class EditHouseHoldFragment extends Fragment {
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

    String id;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_household, container, false);
        Bundle bundle = this.getArguments();

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
        save.setText("UPDATE");

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

        if(bundle != null){
            id = bundle.getString("id");
            retrieve();
        }

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
            contentValues.put("updated_at", String.valueOf(new Timestamp(System.currentTimeMillis())));
            boolean result = db.update(Helper.TB_HOUSEHOLDS, contentValues, "id=" + id, null);
            if(result){
                alert(Helper.DB_INSERT_SUCCESS_TITLE, Helper.DB_INSERT_SUCCESS_MESSAGE,view.getContext());
            }else{
                Helper.alert(Helper.ERROR_INPUT_TITLE, Helper.DB_INSERT_ERROR_MESSAGE, view.getContext());
            }
        }
    }

    public void alert(String title, String message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                backToList();
            }
        });
        builder.setMessage(message);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void backToList(){
        Fragment newFragment = new FamilyListFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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

    private void retrieve(){
        String condition = "id=" + id;
        Cursor res = db.retrieve(Helper.TB_HOUSEHOLDS, condition, "");
        if(res.getCount() == 0){
            Log.d(TAG, "Empty Result");
        }else{
            int i = 0;
            while (res.moveToNext()){
                firstName.setText(res.getString(3));
                lastName.setText(res.getString(4));
                age.setText(res.getString(5));
                mobileNumber.setText(res.getString(6));
                address.setText(res.getString(7));
                gender.setSelection(Arrays.asList(getResources().getStringArray(R.array.gender)).indexOf(res.getString(8)));
                civilStatus.setSelection(Arrays.asList(getResources().getStringArray(R.array.civil_status)).indexOf(res.getString(9)));
                type.setSelection(Arrays.asList(getResources().getStringArray(R.array.household_type)).indexOf(res.getString(10)));
                i++;
            }
        }
    }


}
