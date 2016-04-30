package com.anthony_castelli.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO : Actually load some movies in here
        Movie movie = new Movie();
        movie.setTitle("Interstellar");
        movie.setPoster("/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg");
        movie.setPlotSynopsis("Some people go to space or whatever");
        movie.setReleaseDate("the past");
        movie.setVoteAverage(5.0);

        final Movie[] movies = {movie, movie, movie, movie, movie, movie, movie, movie};

        GridView gridview = (GridView) findViewById(R.id.posterGridView);
        gridview.setAdapter(new MovieAdapter(this, movies));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO : Make an intent and open the page to display info
                Toast.makeText(MainActivity.this, "Open a movie page!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
