package com.example.ahmed.movieapp.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmed.movieapp.Adapters.ListTrailersAdabter;
import com.example.ahmed.movieapp.Model.Database;
import com.example.ahmed.movieapp.Model.Movie;
import com.example.ahmed.movieapp.R;
import com.example.ahmed.movieapp.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ahmed on 10/07/2017.
 */

public class DetailedFragment extends Fragment {
    TextView M_Name, M_Date, M_Time, M_Rate, M_Description, M_Reviews;
    NestedScrollView NCVControl;
    Button favButton;
    ImageView M_Image;
    ListView M_Tralires;
    Database database;
    Movie movie;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detailed_fragment, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            movie = (Movie) bundle.getSerializable("movie");
        } else {
            Intent intent = getActivity().getIntent();
            movie = (Movie) intent.getSerializableExtra("Movie");
        }


        M_Name = (TextView) view.findViewById(R.id.MovieName);
        M_Date = (TextView) view.findViewById(R.id.MovieDate);
        M_Time = (TextView) view.findViewById(R.id.MovieTime);
        M_Rate = (TextView) view.findViewById(R.id.MovieRate);
        NCVControl = (NestedScrollView) view.findViewById(R.id.nestedscrollview_Control);
        M_Reviews = (TextView) view.findViewById(R.id.Reviews_txt);
        M_Description = (TextView) view.findViewById(R.id.MovieDescripton);
        favButton = (Button) view.findViewById(R.id.FavButton);
        M_Image = (ImageView) view.findViewById(R.id.MovieImage);
        M_Tralires = (ListView) view.findViewById(R.id.movies_Trailers);
        database = new Database(getContext());


        int r = (int) (Math.random() * (200 - 90)) + 90;

        M_Name.setText(movie.getMovie_title());
        M_Date.setText(movie.getMovie_release_date().substring(0, 4));
        M_Time.setText(r + " Min");
        M_Rate.setText(movie.getMovie_vote_average() + "/10");
        M_Description.setText(movie.getMovie_overview());

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = database.insertMovie(movie);
                if (check) {
                    Toast.makeText(getContext(), "Insert Movie Done", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Insert Movie Error Or you marked it before", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Picasso.with(getContext())
                .load("https://image.tmdb.org/t/p/w500/" + movie.getMovie_poster_path())
                .into(M_Image);
        new GetMovieTrailers().execute("https://api.themoviedb.org/3/movie/" + movie.getMovie_id() + "/videos?api_key=36d3944bbcad89f305ec7b255a7939b7");
        new GetMovieReview().execute("https://api.themoviedb.org/3/movie/" + movie.getMovie_id() + "/reviews?api_key=36d3944bbcad89f305ec7b255a7939b7");
        return view;
    }

    private class GetMovieTrailers extends AsyncTask<String, String, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(final String[] TraliersKey) {
            ListTrailersAdabter adabter = new ListTrailersAdabter(TraliersKey, getContext());
            M_Tralires.setAdapter(adabter);
            Utility.setListViewHeightBasedOnChildren(M_Tralires);
            M_Tralires.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + TraliersKey[position]));
                    startActivity(intent);
                }
            });


        }

        @Override
        protected String[] doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String JsonMessege = reader.readLine();
                return readJsonMessege(JsonMessege);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class GetMovieReview extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String reviewsData) {
            if (reviewsData != "") {
                M_Reviews.setText(reviewsData);
            } else {
                M_Reviews.setText("There is No Reviews For that Movie Yet .. !");
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                NCVControl.setLayoutParams(param);
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String JsonMessege = reader.readLine();
                return readJsonMovieReview(JsonMessege);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    protected String[] readJsonMessege(String JsonMessege) {
        try {
            JSONObject mainObj = new JSONObject(JsonMessege);
            JSONArray mainArray = mainObj.getJSONArray("results");
            String[] TraliersKey = new String[mainArray.length() * 2];
            for (int i = 0; i < mainArray.length(); i++) {
                TraliersKey[i] = mainArray.getJSONObject(i).getString("key");
            }
            return TraliersKey;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String readJsonMovieReview(String messege) {
        try {
            JSONObject mainObj = new JSONObject(messege);
            JSONArray mainArr = mainObj.getJSONArray("results");
            String reviewsData = "";
            for (int i = 0; i < mainArr.length(); i++) {
                reviewsData = reviewsData + "Name : *" + mainArr.getJSONObject(i).getString("author") + "*";
                reviewsData = reviewsData + "\n" + "\n";
                reviewsData = reviewsData + "     $Content$ :" + mainArr.getJSONObject(i).getString("content");
                reviewsData = reviewsData + "\n" + "\n";
                reviewsData = reviewsData + "-------------------------------------------------------------";
                reviewsData = reviewsData + "\n" + "\n";
            }
            return reviewsData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
