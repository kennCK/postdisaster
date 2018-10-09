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

import static android.content.ContentValues.TAG;

public class EditDependentsFragment extends Fragment {
    EditText firstName;
    EditText lastName;
    EditText age;
    Spinner gender;
    Spinner relation;
    Spinner list;
    Button saveBtnFD;
    View view;

    DatabaseHelper db;

    String id;

    String sFirstName,sLastName, sAge, sGender, sRelation;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dependents, container, false);
        Bundle bundle = this.getArguments();
        db = new DatabaseHelper(view.getContext());
        firstName = (EditText)view.findViewById(R.id.firstNameFD);
        lastName = (EditText)view.findViewById(R.id.lastNameFD);
        age = (EditText)view.findViewById(R.id.ageFD);
        gender = (Spinner)view.findViewById(R.id.genderFD);
        relation = (Spinner)view.findViewById(R.id.relationshipFD);
        list = (Spinner)view.findViewById(R.id.familyListDependents);
        list.setVisibility(View.INVISIBLE);
        saveBtnFD = (Button)view.findViewById(R.id.saveBtnFD);
        saveBtnFD.setText("UPDATE");

        ArrayAdapter<CharSequence> adapterG = ArrayAdapter.createFromResource(view.getContext(), R.array.gender, android.R.layout.simple_spinner_item);
        adapterG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapterG);

        ArrayAdapter<CharSequence> adapterR = ArrayAdapter.createFromResource(view.getContext(), R.array.relation, android.R.layout.simple_spinner_item);
        adapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relation.setAdapter(adapterR);

        if(bundle != null){
            id = bundle.getString("id");
            retrieve();
        }

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
            contentValues.put("gender", sGender);
            contentValues.put("relation", sRelation);
            contentValues.put("updated_at", String.valueOf(new Timestamp(System.currentTimeMillis())));
            boolean result = db.update(Helper.TB_HOUSEHOLDS, contentValues, "id=" + id, null);
            if(result){
                alert(Helper.DB_INSERT_SUCCESS_TITLE, Helper.DB_INSERT_SUCCESS_MESSAGE,view.getContext());
            }else{
                Helper.alert(Helper.ERROR_INPUT_TITLE, Helper.DB_INSERT_ERROR_MESSAGE, view.getContext());
            }
        }
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
                gender.setSelection(Arrays.asList(getResources().getStringArray(R.array.gender)).indexOf(res.getString(8)));
                relation.setSelection(Arrays.asList(getResources().getStringArray(R.array.relation)).indexOf(res.getString(11)));
                i++;
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
}
