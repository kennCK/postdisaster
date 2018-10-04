package com.httpsgocentralph.post_disaster.Entity;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.httpsgocentralph.post_disaster.R;

public class CalamityFamily {
    Household household;
    CalamityName calamityName;
    Calamity calamity;
    ArrayAdapter<CharSequence> status;
    String selected;

    public CalamityFamily(Household household, CalamityName calamityName, Calamity calamity, Context context) {
        this.household = household;
        this.calamityName = calamityName;
        this.calamity = calamity;
        this.status = ArrayAdapter.createFromResource(context, R.array.safety_status, android.R.layout.simple_spinner_item);
        status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selected = (String) this.status.getItem(0);
        this.selected = null;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    public ArrayAdapter<CharSequence> getStatus() {
        return status;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public CalamityName getCalamityName() {
        return calamityName;
    }

    public void setCalamityName(CalamityName calamityName) {
        this.calamityName = calamityName;
    }

    public Calamity getCalamity() {
        return calamity;
    }

    public void setCalamity(Calamity calamity) {
        this.calamity = calamity;
    }
}
