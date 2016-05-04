package com.anthony_castelli.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private Movie[] mCurrentMovies;
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getMovies();

        mGridView = (GridView) findViewById(R.id.posterGridView);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO : Make an intent and open the page to display info
                Toast.makeText(MainActivity.this, "Open a movie page!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMovies() {
        String apiKey = getResources().getString(R.string.TMDB_request_key);
        String popularMoviesURL = "http://api.themoviedb.org/3/movie/popular?api_key=" + apiKey;

        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(popularMoviesURL)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();

                        if (response.isSuccessful()) {
                            mCurrentMovies = parseMovieDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mGridView.setAdapter(new MovieAdapter(MainActivity.this, mCurrentMovies));
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: " + e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception caught: " + e);
                    }
                }
            });
        } else {
            Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG).show();
        }
    }

    private Movie[] parseMovieDetails(String jsonData) throws JSONException {
        JSONObject movieObject = new JSONObject(jsonData);
        JSONArray movieArray = movieObject.getJSONArray("results");
        Movie[] movies = new Movie[movieArray.length()];

        for (int i = 0; i < movies.length; i++) {
            JSONObject jsonMovie = movieArray.getJSONObject(i);

            Log.i(TAG, jsonMovie.getString("poster_path"));

            Movie movie = new Movie();

            movie.setPoster(jsonMovie.getString("poster_path"));

            movies[i] = movie;
        }

        return movies;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;

        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    private void alertUserAboutError() {
        Toast.makeText(this, "Unable to get movie data right now.", Toast.LENGTH_LONG).show();
    }
}
