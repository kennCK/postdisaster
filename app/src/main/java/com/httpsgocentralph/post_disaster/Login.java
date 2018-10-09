package com.httpsgocentralph.post_disaster;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.httpsgocentralph.post_disaster.Database.DatabaseHelper;
import com.httpsgocentralph.post_disaster.Entity.Helper;
import com.httpsgocentralph.post_disaster.Utils.CustomSharedPreference;

import org.json.JSONException;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";
    EditText username, password;
    Button login, register;
    DatabaseHelper db;
    CustomSharedPreference sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DatabaseHelper(Login.this);
        db.create();
        sharedpreferences = new CustomSharedPreference(this);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button)findViewById(R.id.loginBtn);
        register = (Button)findViewById(R.id.registerBtn);

        if(!sharedpreferences.getAccountData().equals("")){
            Intent accIntent = new Intent(Login.this, Main.class);
            startActivity(accIntent);
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent = new Intent(Login.this, Register.class);
                startActivity(regIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    login();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void login() throws JSONException {
        String sUsername = username.getText().toString().trim();
        ProgressDialog progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Verifying QR...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        String sPassword = password.getText().toString().trim();
        if(sUsername.equals("") || sPassword.equals("")){
            progressDialog.dismiss();
            Helper.alert(Helper.ERROR_INPUT_TITLE, Helper.ERROR_INPUT_MESSAGE, Login.this);
        }else{
            progressDialog.dismiss();
            String condition = "( username = '" + sUsername + "' AND password = '" + sPassword + "')";

            // String condition = "(username = '" + sUsername + "')";
            Cursor res = db.retrieve(Helper.TB_ACCOUNTS, condition, "");
            if(res.getCount() == 0){
                Helper.alert("Unable to Login", "Username and password did not exist.", Login.this);
            }else{
                String data = "{";
                while (res.moveToNext()){
                    data += "'id': '" + res.getString(0) + "',";
                    data += "'first_name': '" + res.getString(1) + "',";
                    data += "'last_name': '" + res.getString(2) + "',";
                    data += "'username': '" + res.getString(3) + "',";
                    data += "'password': '" + res.getString(4) + "',";
                    data += "'created_at': '" + res.getString(5) + "',";
                    data += "'updated_at': '" + res.getString(6) + "',";
                    data += "'deleted_at': '" + res.getString(7) + "'";
                }
                data += "}";
                Log.d(TAG, "RES : " + data);
                // Toast.makeText(this, data, Toast.LENGTH_LONG).show();
                sharedpreferences.setAccountData(data);
                Intent accIntent = new Intent(Login.this, Main.class);
                startActivity(accIntent);
            }
        }
    }

}
