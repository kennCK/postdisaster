package com.httpsgocentralph.post_disaster;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.httpsgocentralph.post_disaster.Database.DatabaseHelper;
import com.httpsgocentralph.post_disaster.Entity.Helper;
import com.httpsgocentralph.post_disaster.Utils.CustomSharedPreference;

import java.sql.Timestamp;

public class Register extends AppCompatActivity {

    EditText firstName, lastName, username, password, cPassword;
    Button register, login;

    DatabaseHelper db;
    CustomSharedPreference sharedpreferences;
    private String TAG = "REGISTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new DatabaseHelper(Register.this);
        sharedpreferences = new CustomSharedPreference(this);
        firstName = (EditText)findViewById(R.id.newFirstName);
        lastName = (EditText)findViewById(R.id.newLastName);
        username = (EditText)findViewById(R.id.newUsername);
        password = (EditText)findViewById(R.id.newPassword);
        cPassword = (EditText)findViewById(R.id.newConfirmPassword);

        register = (Button)findViewById(R.id.registerUserBtn);
        login = (Button)findViewById(R.id.backLoginBtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(Register.this, Login.class);
                startActivity(login);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

    }

    private void submit() {
        String sFirstName = firstName.getText().toString().trim();
        String sLastName = lastName.getText().toString().trim();
        String sUsername = username.getText().toString().trim();
        String sPassword = password.getText().toString().trim();
        String sCPassword = cPassword.getText().toString().trim();

        if(sFirstName.equals("") || sLastName.equals("") || sUsername.equals("") || sPassword.equals("")){
            Helper.alert(Helper.ERROR_INPUT_TITLE, Helper.ERROR_INPUT_MESSAGE, Register.this);
        }else if(!sPassword.equals(sCPassword)){
            Helper.alert(Helper.ERROR_INPUT_TITLE, "Please confirm your password.", Register.this);
        }else{
            String date = String.valueOf(new Timestamp(System.currentTimeMillis()));
            ContentValues contentValues = new ContentValues();
            contentValues.put("first_name", sFirstName);
            contentValues.put("last_name", sLastName);
            contentValues.put("username", sPassword);
            contentValues.put("password", sUsername);
            contentValues.put("created_at", date);
            contentValues.put("updated_at", "");
            contentValues.put("deleted_at", "");
            long result = db.insert(Helper.TB_ACCOUNTS, contentValues);

            if(result == -1){
                Helper.alert(Helper.ERROR_INPUT_TITLE, Helper.DB_INSERT_ERROR_MESSAGE, Register.this);
            }else{
                String data = "{";
                data += "'id': '" + result + "',";
                data += "'first_name': '" + sFirstName + "',";
                data += "'last_name': '" + sLastName + "',";
                data += "'username': '" + sUsername + "',";
                data += "'password': '" + sPassword + "',";
                data += "'created_at': '" + date + "',";
                data += "'updated_at': '" + "" + "',";
                data += "'deleted_at': '" + "" + "'";
                data += "}";
                Log.d(TAG, "RES : " + data);
                sharedpreferences.setAccountData(data);
                Intent accIntent = new Intent(Register.this, Main.class);
                startActivity(accIntent);
            }
        }
    }


}
