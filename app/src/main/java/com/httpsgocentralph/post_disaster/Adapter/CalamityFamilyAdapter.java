package com.httpsgocentralph.post_disaster.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.httpsgocentralph.post_disaster.Entity.CalamityFamily;
import com.httpsgocentralph.post_disaster.R;

import java.util.Arrays;

import static android.content.ContentValues.TAG;

public class CalamityFamilyAdapter extends ArrayAdapter<CalamityFamily> {
    TextView name;
    Spinner spinner;
    CalamityFamily calamityFamily;
    public CalamityFamilyAdapter(@NonNull Context context, int resource, CalamityFamily[] list) {
        super(context, resource, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        calamityFamily = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_select, parent, false);
        }
        name = (TextView) convertView.findViewById(R.id.selectText);
        spinner = (Spinner) convertView.findViewById(R.id.selectStatus);
        String completeName = calamityFamily.getHousehold().getFirstName() + " " + calamityFamily.getHousehold().getLastName();
        String type = (calamityFamily.getHousehold().getStatus().equals("household")) ? calamityFamily.getHousehold().getType() :
                calamityFamily.getHousehold().getRelation();
        name.setText(completeName + "\n(" + type + ")");
        spinner.setAdapter(calamityFamily.getStatus());

        Log.d(TAG, "getView: " + calamityFamily.getSelected());
        if(calamityFamily.getSelected() != null){
            spinner.setSelection(Arrays.asList(convertView.getResources().getStringArray(R.array.safety_status)).indexOf(calamityFamily.getSelected()));
        }
        listener(position);
        return convertView;
    }

    private void listener(final int position) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                calamityFamily = getItem(position);
                calamityFamily.setSelected(adapterView.getSelectedItem().toString().trim());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
