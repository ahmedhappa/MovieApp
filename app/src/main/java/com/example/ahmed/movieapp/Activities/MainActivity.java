package com.example.ahmed.movieapp.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ahmed.movieapp.CutomListener;
import com.example.ahmed.movieapp.Fragments.DetailedFragment;
import com.example.ahmed.movieapp.Fragments.MainFragment;
import com.example.ahmed.movieapp.Model.Movie;
import com.example.ahmed.movieapp.R;

public class MainActivity extends AppCompatActivity implements CutomListener {
    MainFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment = new MainFragment();
        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction ft = fManager.beginTransaction();
        ft.replace(R.id.Frame_to_replace, fragment);
        ft.commit();
        fragment.setListener(this);
    }


    @Override
    public void onClick(Movie movie) {
        if (null != findViewById(R.id.Frame_to_replace1)) {
            Bundle bundle=new Bundle();
            bundle.putSerializable("movie",movie);
            DetailedFragment detailedFragment=new DetailedFragment();
            detailedFragment.setArguments(bundle);
            FragmentManager fManager = getSupportFragmentManager();
            FragmentTransaction ft = fManager.beginTransaction();
            ft.replace(R.id.Frame_to_replace1,detailedFragment);
            ft.commit();
        }else {
            Intent intent=new Intent(getApplicationContext(),DetailedActivity.class);
            intent.putExtra("Movie",movie);
            startActivity(intent);
        }
    }
}
