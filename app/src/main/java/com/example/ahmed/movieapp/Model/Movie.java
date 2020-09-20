package com.example.ahmed.movieapp.Model;

import java.io.Serializable;

/**
 * Created by Ahmed on 06/07/2017.
 */

public class Movie implements Serializable {

    int Movie_id;
    Double Movie_vote_average;
    String Movie_title,Movie_poster_path,Movie_overview,Movie_release_date;


    public Movie() {
    }

    public Movie(int movie_id, Double movie_vote_average, String movie_title, String movie_poster_path, String movie_overview, String movie_release_date) {
        Movie_id = movie_id;
        Movie_vote_average = movie_vote_average;
        Movie_title = movie_title;
        Movie_poster_path = movie_poster_path;
        Movie_overview = movie_overview;
        Movie_release_date = movie_release_date;
    }

    public int getMovie_id() {
        return Movie_id;
    }

    public void setMovie_id(int movie_id) {
        Movie_id = movie_id;
    }

    public Double getMovie_vote_average() {
        return Movie_vote_average;
    }

    public void setMovie_vote_average(Double movie_vote_average) {
        Movie_vote_average = movie_vote_average;
    }

    public String getMovie_title() {
        return Movie_title;
    }

    public void setMovie_title(String movie_title) {
        Movie_title = movie_title;
    }

    public String getMovie_poster_path() {
        return Movie_poster_path;
    }

    public void setMovie_poster_path(String movie_poster_path) {
        Movie_poster_path = movie_poster_path;
    }

    public String getMovie_overview() {
        return Movie_overview;
    }

    public void setMovie_overview(String movie_overview) {
        Movie_overview = movie_overview;
    }

    public String getMovie_release_date() {
        return Movie_release_date;
    }

    public void setMovie_release_date(String movie_release_date) {
        Movie_release_date = movie_release_date;
    }
}
