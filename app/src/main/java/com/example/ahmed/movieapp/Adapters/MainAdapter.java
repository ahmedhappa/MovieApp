package com.example.ahmed.movieapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.ahmed.movieapp.Model.Movie;
import com.example.ahmed.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ahmed on 06/07/2017.
 */

public class MainAdapter extends BaseAdapter {
    ImageView movieImg;
    Context context;
    List<Movie> Movies;

    public MainAdapter(List<Movie> Movies, Context context) {
        this.context = context;
        this.Movies = Movies;
    }

    @Override
    public int getCount() {
        return Movies.size();
    }

    @Override
    public Object getItem(int position) {
        return Movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.grid_item, null);
        movieImg = (ImageView) convertView.findViewById(R.id.movies_img);
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w500/" + Movies.get(position).getMovie_poster_path())
                .resize(540, 600)
                .into(movieImg);

        return convertView;
    }
}
