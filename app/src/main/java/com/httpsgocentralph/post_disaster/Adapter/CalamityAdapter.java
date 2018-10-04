package com.httpsgocentralph.post_disaster.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.httpsgocentralph.post_disaster.Entity.Calamity;
import com.httpsgocentralph.post_disaster.Entity.Household;
import com.httpsgocentralph.post_disaster.R;

public class CalamityAdapter extends ArrayAdapter<Calamity> {
    TextView name;
    Calamity calamity;
    public CalamityAdapter(@NonNull Context context, int resource, Calamity[] calamities) {
        super(context, resource, calamities);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        calamity = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_textview, parent, false);
        }
        name = (TextView) convertView.findViewById(R.id.textViewLayout);
        name.setText(calamity.getName() + " on " + calamity.getDate() + "(" + calamity.getStatus() + ")");
        return convertView;
    }
}
