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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.httpsgocentralph.post_disaster.Adapter.FamilyListAdapter;
import com.httpsgocentralph.post_disaster.Database.DatabaseHelper;
import com.httpsgocentralph.post_disaster.Entity.Account;
import com.httpsgocentralph.post_disaster.Entity.Helper;
import com.httpsgocentralph.post_disaster.Entity.Household;
import com.httpsgocentralph.post_disaster.R;
import com.httpsgocentralph.post_disaster.Utils.CustomSharedPreference;

import java.sql.Timestamp;

import static android.content.ContentValues.TAG;

public class FamilyListFragment extends Fragment {
    DatabaseHelper db;
    View view;
    ListView listView;
    FamilyListAdapter familyListAdapter;
    Household[] dependents;
    AlertDialog alert;
    int position, spinnerPosition;
    Spinner list;
    Household[] households;

    Account account;

    CustomSharedPreference sharedpreferences;
    public Gson gson;
    GsonBuilder gsonBuilder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_family_list, container, false);
        db = new DatabaseHelper(view.getContext());
        listView = (ListView)view.findViewById(R.id.listViewFL);
        list = (Spinner)view.findViewById(R.id.familyListSummary);

        sharedpreferences = new CustomSharedPreference(view.getContext());
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        account = gson.fromJson(sharedpreferences.getAccountData(), Account.class);
        initFamilyList();
        listener();
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

        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerPosition = i;
                retrieve(households[spinnerPosition].getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void listener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                Log.d(TAG, "Name " + dependents[i].getFirstName() + " "  + dependents[i].getLastName());
                alert();
            }
        });
    }

    public void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Menu");
        String[] menus = {"Edit", "Delete", "Close"};
        final int EDIT = 0;
        final int DELETE = 1;
        final int CLOSE = 2;
        builder.setItems(menus, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case EDIT:
                        if(dependents[position].getStatus().equals("household")){
                            // Household
                            Fragment newFragment = new EditHouseHoldFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", dependents[position].getId());
                            newFragment.setArguments(bundle);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }else{
                            // Dependent
                            Fragment newFragment = new EditDependentsFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", dependents[position].getId());
                            newFragment.setArguments(bundle);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }

                        Log.d(TAG, "onClick: EDIT");
                        break;
                    case DELETE:
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("deleted_at", String.valueOf(new Timestamp(System.currentTimeMillis())));
                        if(db.update(Helper.TB_HOUSEHOLDS, contentValues, "id=" + dependents[position].getId(), null)){
                            retrieve(households[spinnerPosition].getId());
                        }
                        Log.d(TAG, "onClick: DELETE");
                        break;
                    case CLOSE:
                        Log.d(TAG, "onClick: CLOSE");
                        if(alert != null){
                            alert.dismiss();
                        }
                        break;
                }
            }
        });
        builder.setCancelable(true);
        alert = builder.create();
        alert.show();
    }

    private void retrieve(String id){
        String condition = "(under= '" + id + "' OR id = '" + id + "')";
        Cursor res = db.retrieve(Helper.TB_HOUSEHOLDS, condition, "first_name ASC");
        if(res.getCount() == 0){
            Log.d(TAG, "Calamity is Empty");
        }else{
            Log.d(TAG, "RES = " + res);
            String[] name = new String[res.getCount()];
            dependents = new Household[res.getCount()];
            int i = 0;

            while (res.moveToNext()){
                dependents[i] = new Household(
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
                i++;
            }

            familyListAdapter = new FamilyListAdapter(view.getContext(), 1, dependents);
            listView.setAdapter(familyListAdapter);
        }
    }
}
