package com.httpsgocentralph.post_disaster.Fragment;

import android.app.AlertDialog;
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

import com.httpsgocentralph.post_disaster.Adapter.CalamityAdapter;
import com.httpsgocentralph.post_disaster.Database.DatabaseHelper;
import com.httpsgocentralph.post_disaster.Entity.Calamity;
import com.httpsgocentralph.post_disaster.Entity.Helper;
import com.httpsgocentralph.post_disaster.R;

import static android.content.ContentValues.TAG;

public class CalamityListFragment extends Fragment {
    DatabaseHelper db;
    View view;
    ListView listView;
    CalamityAdapter calamityAdapter;
    Calamity[] calamities;
    AlertDialog alert;
    int position;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_calamity_list, container, false);
        db = new DatabaseHelper(view.getContext());
        listView = (ListView)view.findViewById(R.id.listViewCL);

        retrieve();
        listener();
        return view;
    }

    private void listener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                Log.d(TAG, "Name: " + calamities[i].getName());
                alert();
            }
        });
    }
    public void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Menu");
        String[] menus = {"Household List", "Close"};
        final int LIST = 0;
        final int CLOSE = 1;
        builder.setItems(menus, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case LIST:
                        Fragment newFragment = new CalamityFamilyListFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("id", calamities[position].getId());
                        Log.d(TAG, "BUNDLER : " + calamities[position].getId());
                        newFragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        Log.d(TAG, "onClick: LIST");
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
        Cursor res = db.retrieve(Helper.TB_CALAMITIES, "", "id DESC");
        if(res.getCount() == 0){
            Log.d(TAG, "Calamity is Empty");
        }else{
            calamities = new Calamity[res.getCount()];
            int i = 0;

            while (res.moveToNext()){
                calamities[i] = new Calamity(
                        res.getString(0),
                        res.getString(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getString(6),
                        res.getString(7),
                        res.getString(8)
                );
                i++;
            }
            calamityAdapter = new CalamityAdapter(view.getContext(), 1, calamities);
            listView.setAdapter(calamityAdapter);
        }
    }

}
