package com.httpsgocentralph.post_disaster.Entity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Helper {
    public static final String DB_NAME = "post_disaster.db";
    public static final String TB_ACCOUNTS = "accounts";
    public static final String TB_HOUSEHOLDS = "household_names";
    public static final String TB_CALAMITIES = "calamities";
    public static final String TB_CALAMITY_NAMES = "calamity_names";

    public static final String ERROR_INPUT_TITLE = "Unable to Save!";
    public static final String ERROR_INPUT_MESSAGE = "Error Message! Please fill in all the required fields.";
    public static final String DB_INSERT_ERROR_MESSAGE = "Something went wrong with the database";
    public static final String DB_INSERT_SUCCESS_MESSAGE = "You request was successfully saved!";
    public static final String DB_INSERT_SUCCESS_TITLE = "Success Message!";


    public static final String MyPREFERENCES = "PDIGS";
    public static final String ACCOUNT_DATA = "ACCOUNT_DATA";


    public static void alert(String title, String message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setMessage(message);
        AlertDialog alert = builder.create();
        alert.show();
    }
}
