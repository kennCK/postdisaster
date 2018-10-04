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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.httpsgocentralph.post_disaster.Database.DatabaseHelper;
import com.httpsgocentralph.post_disaster.Entity.CalamityFamily;
import com.httpsgocentralph.post_disaster.Entity.CalamityFamilyAdapter;
import com.httpsgocentralph.post_disaster.Entity.Helper;
import com.httpsgocentralph.post_disaster.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CalamityFamilyListFragment extends Fragment {
    View view;
    ListView listView;
    Button btn;
    DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calamity_family_list, container, false);
        listView = (ListView) view.findViewById(R.id.listViewCF);
        btn = (Button)view.findViewById(R.id.saveBtnCF);

        db = new DatabaseHelper(view.getContext());
        listener();
        manage();

        return view;
    }

    public void listener(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save
            }
        });
    }

    public void manage(){
        Cursor res = db.retrieve(Helper.TB_HOUSEHOLDS, null, "first_name ASC");

        if(res.getCount() == 0){
            Log.d(TAG, "Empty List");
        }else{
            CalamityFamily[] calamityFamilies = new CalamityFamily[res.getCount()];
            int i = 0;
            while (res.moveToNext()){
                String name = res.getString(1) + " " + res.getString(2);
                calamityFamilies[i] = new CalamityFamily(name, view.getContext());
                i++;
            }
            CalamityFamilyAdapter adapter = new CalamityFamilyAdapter(view.getContext(), 1, calamityFamilies);
            listView.setAdapter(adapter);
        }
    }
}
