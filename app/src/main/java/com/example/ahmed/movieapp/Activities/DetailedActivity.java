package com.example.ahmed.movieapp.Activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ahmed.movieapp.Fragments.DetailedFragment;
import com.example.ahmed.movieapp.R;

public class DetailedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        DetailedFragment detailedFragment = new DetailedFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.detailed_Frame_to_replace, detailedFragment);
        transaction.commit();
    }

}
