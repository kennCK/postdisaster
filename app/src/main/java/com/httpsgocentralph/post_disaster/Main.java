package com.httpsgocentralph.post_disaster;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.httpsgocentralph.post_disaster.Database.DatabaseHelper;
import com.httpsgocentralph.post_disaster.Entity.Account;
import com.httpsgocentralph.post_disaster.Fragment.CalamityFragment;
import com.httpsgocentralph.post_disaster.Fragment.CalamityListFragment;
import com.httpsgocentralph.post_disaster.Fragment.DependentsFragment;
import com.httpsgocentralph.post_disaster.Fragment.FamilyListFragment;
import com.httpsgocentralph.post_disaster.Fragment.HouseHoldFragment;
import com.httpsgocentralph.post_disaster.Fragment.SendFragment;
import com.httpsgocentralph.post_disaster.Utils.CustomSharedPreference;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    TextView username;


    CustomSharedPreference sharedpreferences;
    public Gson gson;
    GsonBuilder gsonBuilder;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedpreferences = new CustomSharedPreference(this);
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        account = gson.fromJson(sharedpreferences.getAccountData(), Account.class);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HouseHoldFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_head);
        }

        // username = (TextView) findViewById(R.id.mainUsername);
        // username.setText(account.getUsername());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_head:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HouseHoldFragment()).commit();
                break;
            case R.id.nav_dependents:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DependentsFragment()).commit();
                break;
            case R.id.nav_family_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FamilyListFragment()).commit();
                break;
            case R.id.nav_calamity:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CalamityFragment()).commit();
                break;
            case R.id.nav_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CalamityListFragment()).commit();
                break;
            case R.id.nav_send:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SendFragment()).commit();
                break;
            case R.id.nav_reset:
                new DatabaseHelper(Main.this).reset();
                break;
            case R.id.nav_logout:
                sharedpreferences.setAccountData("");
                Intent logoutIntent = new Intent(Main.this, Login.class);
                startActivity(logoutIntent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}
