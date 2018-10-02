package com.httpsgocentralph.post_disaster.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.httpsgocentralph.post_disaster.Account;
import com.httpsgocentralph.post_disaster.R;

public class CalamityFragment extends Fragment {
    Spinner calamity;
    EditText date;
    Spinner damageStatus;
    EditText damageAmount;
    Button next;
    View view1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view1 = inflater.inflate(R.layout.fragment_calamity, container, false);

        calamity = (Spinner)view1.findViewById(R.id.calamity);
        date = (EditText)view1.findViewById(R.id.dateCalamity);
        damageStatus = (Spinner) view1.findViewById(R.id.calamityDamage);
        damageAmount = (EditText)view1.findViewById(R.id.damageAmount);
        next = (Button)view1.findViewById(R.id.calamityNextBtn);

        ArrayAdapter<CharSequence> adapterC = ArrayAdapter.createFromResource(view1.getContext(), R.array.calamity_type, android.R.layout.simple_spinner_item);
        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        calamity.setAdapter(adapterC);

        ArrayAdapter<CharSequence> adapterD = ArrayAdapter.createFromResource(view1.getContext(), R.array.damage_type, android.R.layout.simple_spinner_item);
        adapterD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        damageStatus.setAdapter(adapterD);
        listener();
        return view1;
    }

    private void listener() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new CalamityFamilyListFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
