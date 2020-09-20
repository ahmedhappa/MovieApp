package com.example.ahmed.movieapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ahmed.movieapp.R;

/**
 * Created by Ahmed on 11/07/2017.
 */

public class ListTrailersAdabter extends BaseAdapter {
    String[] Trailers;
    Context context;

    public ListTrailersAdabter(String[] trailers, Context context) {
        this.Trailers = trailers;
        this.context = context;
    }

    @Override
    public int getCount() {
        return Trailers.length;
    }

    @Override
    public Object getItem(int position) {
        return Trailers[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.listview_trailers_items, null);
        TextView TrailerNum = (TextView) convertView.findViewById(R.id.TrailerNum);
        TrailerNum.setText("Trailer " + (position+1));
        return convertView;
    }
}
