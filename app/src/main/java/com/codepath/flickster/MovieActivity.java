package com.codepath.flickster;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.codepath.flickster.adapters.MoviewArrayAdapter;
import com.codepath.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class MovieActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;

    ArrayList<Movie> movies;
    MoviewArrayAdapter moviewAdapter;
    ListView lvItems;

    private static AsyncHttpClient client = new AsyncHttpClient();;

    private final String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                callAPI();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        lvItems = (ListView) findViewById(R.id.lvMovies);
        movies = new ArrayList<>();
        moviewAdapter = new MoviewArrayAdapter(this, movies);
        lvItems.setAdapter(moviewAdapter);

        callAPI();
    }

    private void callAPI(){

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray movieJSONResults = null;

                try {
                    movieJSONResults = response.getJSONArray("results");
                    moviewAdapter.clear();
                    movies.addAll(Movie.fromJSONArray(movieJSONResults));
                    moviewAdapter.notifyDataSetChanged();

                    swipeContainer.setRefreshing(false);

                    // Log.d("DEBUG", movieJSONResults.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
