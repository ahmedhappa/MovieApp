package com.example.ahmed.movieapp.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed on 13/07/2017.
 */

public class Database extends SQLiteOpenHelper {
    private static final String databaseName = "movieapp.dp";
    private static final int version = 1;
    private Context context;


    public Database(Context context) {
        super(context, databaseName, null, version);
        this.context = context;
    }

    private final String Movie_Table = "movie";
    private final String Movie_id = "movie_id";
    private final String Movie_vote_average = "movie_vote_average";
    private final String Movie_title = "movie_title";
    private final String Movie_poster_path = "movie_poster_path";
    private final String Movie_overview = "movie_overview";
    private final String Movie_release_date = "movie_release_date";

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("Create Table " + Movie_Table + "( " + Movie_id + " integer ," + Movie_vote_average + " Double,"
                    + Movie_title + " text," + Movie_poster_path + " text," + Movie_overview + " text,"
                    + Movie_release_date + " text)");
            Toast.makeText(context, "Table Created", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertMovie(Movie movie) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        SQLiteDatabase sqLiteDatabaseReader = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabaseReader.rawQuery("select * from " + Movie_Table + " where " + Movie_id + " = " + movie.getMovie_id(),null);
        if (cursor.moveToNext()){
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(Movie_id, movie.getMovie_id());
        values.put(Movie_vote_average, movie.getMovie_vote_average());
        values.put(Movie_title, movie.getMovie_title());
        values.put(Movie_poster_path, movie.getMovie_poster_path());
        values.put(Movie_overview, movie.getMovie_overview());
        values.put(Movie_release_date, movie.getMovie_release_date());
        long check = sqLiteDatabase.insert(Movie_Table, null, values);
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<Movie> getMovieData (){
        List<Movie> Movies=new ArrayList<Movie>();
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from " + Movie_Table,null);
        while (cursor.moveToNext()){
            Movies.add(new Movie(cursor.getInt(0),cursor.getDouble(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)));
        }
        return Movies;
    }

}
