package com.example.ahmed.movieapp.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ahmed.movieapp.Activities.DetailedActivity;
import com.example.ahmed.movieapp.Activities.MainActivity;
import com.example.ahmed.movieapp.Activities.SettingsActivity;
import com.example.ahmed.movieapp.Adapters.MainAdapter;
import com.example.ahmed.movieapp.CutomListener;
import com.example.ahmed.movieapp.Model.Database;
import com.example.ahmed.movieapp.Model.Movie;
import com.example.ahmed.movieapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed on 06/07/2017.
 */

public class MainFragment extends Fragment {
    String RequstedUrlMostPopular = "https://api.themoviedb.org/3/movie/popular?api_key=36d3944bbcad89f305ec7b255a7939b7";
    String RequstedUrlTopRated = "https://api.themoviedb.org/3/movie/top_rated?api_key=36d3944bbcad89f305ec7b255a7939b7";
    CutomListener cutomListener;
    boolean MyFavoriteMovies = false;
    Database database=null;
    List<Movie> Movies = new ArrayList<Movie>();
    GridView GridMoviesList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        setHasOptionsMenu(true);
        database=new Database(getContext());
        GridMoviesList = (GridView) view.findViewById(R.id.grid_id);

        return view;
    }

    @Override
    public void onStart() {
        Intent intent=getActivity().getIntent();
        MyFavoriteMovies=intent.getBooleanExtra("MyFavoriteMovies",false);
        if (MyFavoriteMovies) {
            Movies.clear();
            Movies=database.getMovieData();
            MainAdapter adapter=new MainAdapter(Movies,getContext());
            GridMoviesList.setAdapter(adapter);
            GridMoviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent intent = new Intent(getActivity(), DetailedActivity.class);
//                    intent.putExtra("Movie", Movies.get(position));
//                    startActivity(intent);
                    cutomListener.onClick(Movies.get(position));
                }
            });
        } else {
            changeSetteing();
        }
        super.onStart();
    }

    public void setListener (CutomListener listener){
        this.cutomListener=listener;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.favorite:
                MyFavoriteMovies = true;
                Intent favMov = new Intent(getActivity(), MainActivity.class);
                favMov.putExtra("MyFavoriteMovies", MyFavoriteMovies);
                startActivity(favMov);
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeSetteing() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mySharedPreferences", Activity.MODE_PRIVATE);
        String check = sharedPreferences.getString("MoviesType", "Not Found");
        if (check.equals("Most Popular")) {
            new GetMovies().execute(RequstedUrlMostPopular);
        } else if (check.equals("Top Rated")) {
            new GetMovies().execute(RequstedUrlTopRated);
        } else if (check.equals("Not Found")) {
            new GetMovies().execute(RequstedUrlMostPopular);
        }
    }

    private class GetMovies extends AsyncTask<String, String, List<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(final List<Movie> movies) {
            MainAdapter adapter = new MainAdapter(movies, getContext());
            GridMoviesList.setAdapter(adapter);
            GridMoviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent intent = new Intent(getActivity(), DetailedActivity.class);
//                    intent.putExtra("Movie", Movies.get(position));
//                    startActivity(intent);
                    cutomListener.onClick(movies.get(position));
                }
            });

        }

        @Override
        protected List<Movie> doInBackground(String... params) {
            String Messege = "";
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                BufferedReader BuffereMessege = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                Messege = BuffereMessege.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return JsonReader(Messege);
        }


    }

    protected List<Movie> JsonReader(String Messege) {
        try {
            Movies.clear();
            JSONObject mainObj = new JSONObject(Messege);
            JSONArray arrayOfMovies = mainObj.getJSONArray("results");
            for (int i = 0; i < arrayOfMovies.length(); i++) {
                int Movie_id = arrayOfMovies.getJSONObject(i).getInt("id");
                Double Movie_vote_average = arrayOfMovies.getJSONObject(i).getDouble("vote_average");
                String Movie_title = arrayOfMovies.getJSONObject(i).getString("title");
                String Movie_poster_path = arrayOfMovies.getJSONObject(i).getString("poster_path");
                String Movie_overview = arrayOfMovies.getJSONObject(i).getString("overview");
                String Movie_release_date = arrayOfMovies.getJSONObject(i).getString("release_date");
                Movies.add(new Movie(Movie_id, Movie_vote_average, Movie_title, Movie_poster_path, Movie_overview, Movie_release_date));
            }
            return Movies;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
