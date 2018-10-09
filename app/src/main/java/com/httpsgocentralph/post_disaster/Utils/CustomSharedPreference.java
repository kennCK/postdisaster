package com.httpsgocentralph.post_disaster.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.httpsgocentralph.post_disaster.Entity.Helper;

public class CustomSharedPreference {
    private SharedPreferences sharedPreferences;

    public CustomSharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public SharedPreferences getInstanceOfSharedPreference(){
        return sharedPreferences;
    }

    public void setAccountData(String accountData){
        sharedPreferences.edit().putString(Helper.ACCOUNT_DATA, accountData).apply();
    }

    public String getAccountData(){
        return sharedPreferences.getString(Helper.ACCOUNT_DATA, "");
    }

    public void setHouseHoldId(String id){
        sharedPreferences.edit().putString("HOUSEHOLD_ID", id).apply();
    }

    public String getHouseHoldId(){
        return sharedPreferences.getString("HOUSEHOLD_ID", "");
    }
}
