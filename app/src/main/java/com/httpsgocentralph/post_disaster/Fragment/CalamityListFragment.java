package com.httpsgocentralph.post_disaster.Fragment;

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
import android.widget.ListView;

import com.httpsgocentralph.post_disaster.Database.DatabaseHelper;
import com.httpsgocentralph.post_disaster.Entity.Helper;
import com.httpsgocentralph.post_disaster.R;

import static android.content.ContentValues.TAG;

public class CalamityListFragment extends Fragment {
    DatabaseHelper db;
    View view;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    Button btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_calamity_list, container, false);
        db = new DatabaseHelper(view.getContext());
        listView = (ListView)view.findViewById(R.id.listViewCL);
        btn = (Button)view.findViewById(R.id.okBtnCL);

        retrieve();
        return view;
    }
    private void retrieve(){
        Cursor res = db.retrieve(Helper.TB_CALAMITIES, null, "id DESC");
        if(res.getCount() == 0){
            Log.d(TAG, "Calamity is Empty");
        }else{
            Log.d(TAG, "RES = " + res);
            String[] name = new String[res.getCount()];
            int i = 0;

            while (res.moveToNext()){
                name[i] = res.getString(1) + " on " + res.getString(2) + "(" + res.getString(5) + ")";
                i++;
            }

            arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, name);
            listView.setAdapter(arrayAdapter);
        }
    }
}