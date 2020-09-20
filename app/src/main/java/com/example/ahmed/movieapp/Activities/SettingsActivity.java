package com.example.ahmed.movieapp.Activities;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;


import com.example.ahmed.movieapp.R;

public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment fragment=new SettingFragment();
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content,fragment);
        fragmentTransaction.commit();
    }

    public static class SettingFragment extends PreferenceFragment{

        String MoviesType="";
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_headers);
            ListPreference ListMovieType=(ListPreference) findPreference("preference_List");
            MoviesType=ListMovieType.getValue();
//            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//            String MoviesType=sharedPreferences.getString("preference_List","Not Found");

            ListMovieType.setSummary(ListMovieType.getValue().toString());
            ListMovieType.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    preference.setSummary(newValue.toString());

                    SharedPreferences sharedPreferences=getActivity().getSharedPreferences("mySharedPreferences",Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("MoviesType",newValue.toString());
                    editor.commit();
                    return true;
                }
            });

        }

    }

}


