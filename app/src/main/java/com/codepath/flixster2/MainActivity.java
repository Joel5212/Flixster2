package com.codepath.flixster2;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.flixster2.adapters.MovieAdapter;
import com.codepath.flixster2.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity";//

    List<Movie> movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        //Create an adapter
        final MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        //set the adapter on the recyclerview
        rvMovies.setAdapter(movieAdapter);

        // set a Layout Manager
        rvMovies.setLayoutManager(new LinearLayoutManager(this));



        AsyncHttpClient client = new  AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {   //get request on the url to get the currently playing movies, the movie dattabase speaks JSON so that's why we are using this response handler
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");    //Breakpoint will cause the program to stop once it is hit
                JSONObject jsonObject = json.jsonObject;         //Once on Success is hit, go to jsonObject -> nameValuePairs -> results -> key -> count which will show us how many movies we are parsing
                try {
                    JSONArray results = jsonObject.getJSONArray("results");  //turn JSON array into a list of movies     //As you are parsing and examining data in your JSON there might be some issue so use a try catch                                          //Data we want is a JSON object because i tis returned as an Object
                    Log.i(TAG, "Results " + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));  //will return a list of movie objects, we to keep adding and modifying and not creating brand new movies  ;
                    movieAdapter.notifyDataSetChanged(); //Everytime the data changes we need to let the adapter know so that the adapterview can rerender the recycler view
                    Log.i(TAG, "Movies: " + movies.size());   // verifying movies variable is sensible
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }


}