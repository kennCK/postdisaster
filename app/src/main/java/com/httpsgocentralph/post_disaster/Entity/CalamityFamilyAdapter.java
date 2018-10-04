package com.httpsgocentralph.post_disaster.Entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.httpsgocentralph.post_disaster.R;

import java.util.ArrayList;

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
        name.setText(calamityFamily.getName());
        spinner.setAdapter(calamityFamily.getStatus());
        return convertView;
    }
}
