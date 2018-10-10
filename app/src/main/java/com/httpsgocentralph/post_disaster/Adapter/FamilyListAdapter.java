package com.httpsgocentralph.post_disaster.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.httpsgocentralph.post_disaster.Entity.CalamityFamily;
import com.httpsgocentralph.post_disaster.Entity.Household;
import com.httpsgocentralph.post_disaster.R;

public class FamilyListAdapter extends ArrayAdapter<Household> {
    TextView name;
    Household household;
    public FamilyListAdapter(@NonNull Context context, int resource, Household[] households) {
        super(context, resource, households);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        household = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_textview, parent, false);
        }
        name = (TextView) convertView.findViewById(R.id.textViewLayout);
        String completeName = household.getFirstName() + " " + household.getLastName();
        String type = (household.getStatus().equals("household")) ? household.getType() :
                household.getRelation();
        name.setText(completeName + " (" + type + ")");
        return convertView;
    }
}
