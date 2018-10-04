package com.httpsgocentralph.post_disaster.Entity;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.httpsgocentralph.post_disaster.R;

public class CalamityFamily {
    String name;
    ArrayAdapter<CharSequence> status;

    public CalamityFamily(String name, Context context) {
        this.name = name;
        this.status = ArrayAdapter.createFromResource(context, R.array.safety_status, android.R.layout.simple_spinner_item);
        status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayAdapter<CharSequence> getStatus() {
        return status;
    }
}
