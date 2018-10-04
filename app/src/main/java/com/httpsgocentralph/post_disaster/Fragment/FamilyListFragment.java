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

import com.httpsgocentralph.post_disaster.Adapter.FamilyListAdapter;
import com.httpsgocentralph.post_disaster.Database.DatabaseHelper;
import com.httpsgocentralph.post_disaster.Entity.Helper;
import com.httpsgocentralph.post_disaster.Entity.Household;
import com.httpsgocentralph.post_disaster.R;

import java.sql.Timestamp;

import static android.content.ContentValues.TAG;

public class FamilyListFragment extends Fragment {
    DatabaseHelper db;
    View view;
    ListView listView;
    FamilyListAdapter familyListAdapter;
    Household[] households;
    AlertDialog alert;
    int position;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_family_list, container, false);
        db = new DatabaseHelper(view.getContext());
        listView = (ListView)view.findViewById(R.id.listViewFL);

        retrieve();
        listener();
        return view;
    }

    private void listener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                Log.d(TAG, "Name " + households[i].getFirstName() + " "  + households[i].getLastName());
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
                        if(households[position].getStatus().equals("household")){
                            // Household
                            Fragment newFragment = new EditHouseHoldFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", households[position].getId());
                            newFragment.setArguments(bundle);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }else{
                            // Dependent
                            Fragment newFragment = new EditDependentsFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", households[position].getId());
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
                        if(db.update(Helper.TB_HOUSEHOLDS, contentValues, "id=" + households[position].getId(), null)){
                            retrieve();
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

    private void retrieve(){
        Cursor res = db.retrieve(Helper.TB_HOUSEHOLDS, "", "first_name ASC");
        if(res.getCount() == 0){
            Log.d(TAG, "Calamity is Empty");
        }else{
            Log.d(TAG, "RES = " + res);
            String[] name = new String[res.getCount()];
            households = new Household[res.getCount()];
            int i = 0;

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
                        res.getString(13)
                );
                i++;
            }

            familyListAdapter = new FamilyListAdapter(view.getContext(), 1, households);
            listView.setAdapter(familyListAdapter);
        }
    }
}
