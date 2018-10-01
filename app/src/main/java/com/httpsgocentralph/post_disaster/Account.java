package com.httpsgocentralph.post_disaster;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.httpsgocentralph.post_disaster.Fragment.DependentsFragment;
import com.httpsgocentralph.post_disaster.Fragment.FamilyListFragment;
import com.httpsgocentralph.post_disaster.Fragment.HouseHoldFragment;

public class Account extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
            case R.id.nav_send:
                Toast.makeText(this, "Send", Toast.LENGTH_LONG).show();
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
