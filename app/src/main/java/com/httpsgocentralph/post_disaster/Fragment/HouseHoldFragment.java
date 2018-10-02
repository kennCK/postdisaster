package com.httpsgocentralph.post_disaster.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.httpsgocentralph.post_disaster.R;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_household, container, false);
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
                // save
            }
        });

    }


}
