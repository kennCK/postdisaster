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
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.httpsgocentralph.post_disaster.Database.DatabaseHelper;
import com.httpsgocentralph.post_disaster.Entity.Calamity;
import com.httpsgocentralph.post_disaster.Entity.CalamityFamily;
import com.httpsgocentralph.post_disaster.Adapter.CalamityFamilyAdapter;
import com.httpsgocentralph.post_disaster.Entity.CalamityName;
import com.httpsgocentralph.post_disaster.Entity.Helper;
import com.httpsgocentralph.post_disaster.Entity.Household;
import com.httpsgocentralph.post_disaster.R;

import java.sql.Timestamp;
import java.util.Arrays;

import static android.content.ContentValues.TAG;

public class CalamityFamilyListFragment extends Fragment {
    View view;
    ListView listView;
    Button btn;
    DatabaseHelper db;

    CalamityFamily[] calamityFamilies;

    String calamityId;
    Boolean flag;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calamity_family_list, container, false);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            calamityId = bundle.getString("id");
        }

        listView = (ListView) view.findViewById(R.id.listViewCF);
        btn = (Button)view.findViewById(R.id.saveBtnCF);

        db = new DatabaseHelper(view.getContext());

        retrieve();



        listener();
        return view;
    }

    private void retrieve(){
        String condition = "calamity_id=" + calamityId;
        Log.d(TAG, "calamity_id : " + calamityId);
        Cursor res = db.retrieve(Helper.TB_CALAMITY_NAMES, condition, "id asc");
        if(res.getCount() == 0){
            flag = false;
            manage();
            Log.d(TAG, "Empty Result");
        }else{
            flag = true;
            int i = 0;
            calamityFamilies = new CalamityFamily[res.getCount()];
            while (res.moveToNext()){
                // get calamity
                Calamity calamity = null;
                Household household = null;
                Log.d(TAG, " Status : " + res.getString(3));
                CalamityName calamityName = new CalamityName(
                        res.getString(0),
                        res.getString(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getString(6)
                );
                Cursor calamities = db.retrieve(Helper.TB_CALAMITIES, "id=" + res.getString(1), "");
                Cursor households = db.retrieve(Helper.TB_HOUSEHOLDS, "id=" + res.getString(2), "");
                if(calamities.getCount() > 0){
                    while (calamities.moveToNext()){
                        calamity = new Calamity(
                                calamities.getString(0),
                                calamities.getString(1),
                                calamities.getString(2),
                                calamities.getString(3),
                                calamities.getString(4),
                                calamities.getString(5),
                                calamities.getString(6),
                                calamities.getString(7),
                                calamities.getString(8)
                        );
                    }
                }
                if(households.getCount() > 0){
                    while (households.moveToNext()){
                        household = new Household(
                                households.getString(0),
                                households.getString(1),
                                households.getString(2),
                                households.getString(3),
                                households.getString(4),
                                households.getString(5),
                                households.getString(6),
                                households.getString(7),
                                households.getString(8),
                                households.getString(9),
                                households.getString(10),
                                households.getString(11),
                                households.getString(12),
                                households.getString(13)
                        );
                    }
                }
                calamityFamilies[i] = new CalamityFamily(household, calamityName, calamity, view.getContext());
                calamityFamilies[i].setSelected(res.getString(3));
                i++;
            }
            CalamityFamilyAdapter adapter = new CalamityFamilyAdapter(view.getContext(), 1, calamityFamilies);
            listView.setAdapter(adapter);
        }
    }

    public void manage(){
        Cursor res = db.retrieve(Helper.TB_HOUSEHOLDS, "", "first_name ASC");

        if(res.getCount() == 0){
            Log.d(TAG, "Empty List");
        }else{
           calamityFamilies = new CalamityFamily[res.getCount()];
            int i = 0;
            while (res.moveToNext()){
                Household household = new Household(
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
                calamityFamilies[i] = new CalamityFamily(household, null, null, view.getContext());
                i++;
            }
            CalamityFamilyAdapter adapter = new CalamityFamilyAdapter(view.getContext(), 1, calamityFamilies);
            listView.setAdapter(adapter);
        }
    }

    public void listener(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    public void save(){
        int i = 0;
        while (i < calamityFamilies.length){
            if(flag == false){
                ContentValues contentValues = new ContentValues();
                contentValues.put("calamity_id", calamityId);
                contentValues.put("household_name_id", calamityFamilies[i].getHousehold().getId());
                contentValues.put("status", calamityFamilies[i].getSelected());
                contentValues.put("created_at", String.valueOf(new Timestamp(System.currentTimeMillis())));
                contentValues.put("updated_at", "");
                contentValues.put("deleted_at", "");
                long res = db.insert(Helper.TB_CALAMITY_NAMES, contentValues);
                if(res != -1){
                    i++;
                }else{
                    Helper.alert(Helper.ERROR_INPUT_TITLE, Helper.DB_INSERT_ERROR_MESSAGE, view.getContext());
                    break;
                }
            }else{
                // update
                ContentValues contentValues = new ContentValues();
                contentValues.put("status", calamityFamilies[i].getSelected());
                contentValues.put("updated_at", String.valueOf(new Timestamp(System.currentTimeMillis())));
                if(db.update(Helper.TB_CALAMITY_NAMES, contentValues, "id=" + calamityFamilies[i].getCalamityName().getId(), null)){
                    i++;
                }else{
                    Helper.alert(Helper.ERROR_INPUT_TITLE, Helper.DB_INSERT_ERROR_MESSAGE, view.getContext());
                    break;
                }
            }

        }
        if(i >= calamityFamilies.length){
            Helper.alert(Helper.DB_INSERT_SUCCESS_TITLE, Helper.DB_INSERT_SUCCESS_MESSAGE,view.getContext());
        }
    }
}
