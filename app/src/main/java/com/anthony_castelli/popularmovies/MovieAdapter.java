package com.anthony_castelli.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MovieAdapter extends BaseAdapter {
    private Context mContext;
    private Movie[] mMovies;

    public MovieAdapter(Context c, Movie[] movies) {
        mContext = c;
        mMovies = movies;
    }

    public int getCount() {
        return mMovies.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            // Initialize attributes if not recycled
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        // Add the movie image to the current ImageView
        Movie movie;
        movie = mMovies[position];
        String requestedSize = "w185";
        String URL = "http://image.tmdb.org/t/p/" + requestedSize + movie.getPoster();
        Picasso.with(mContext).load(URL).into(imageView);

        return imageView;
    }
}
