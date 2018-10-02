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

public class DependentsFragment extends Fragment {
    EditText firstName;
    EditText lastName;
    EditText age;
    Spinner gender;
    Spinner relation;
    Button saveBtnFD;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dependents, container, false);
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
        return view;
    }
}
