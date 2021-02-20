package com.codepath.flixster2;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.flixster2.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {

    public static final String YOUTUBE_API_KEY = "AIzaSyB_EuUjFTTHTdYeJDFgk1o2MI0TC1M_ouU"; //Key for extracting youtube access on our apps
    public static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    TextView tvReleaseDate;
    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    TextView tvLanguage;
    TextView tvAdult;
    YouTubePlayerView youtubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        ratingBar = findViewById(R.id.ratingBar);
        tvReleaseDate = findViewById(R.id.tvReleaseDate);
        tvAdult = findViewById(R.id.tvAdult);
        tvLanguage = findViewById(R.id.tvLanguage);
        youtubePlayerView = findViewById(R.id.player);
        AsyncHttpClient client = new  AsyncHttpClient();   //Doing the same process we did with the movie data base


        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle.setText(movie.getTitle());                   //get the different details from the parcelable of the movie object
        tvOverview.setText(movie.getOverview());
        ratingBar.setRating((float)movie.getRating());
        tvReleaseDate.setText(movie.getReleaseDate());
        tvLanguage.setText(movie.getOriginalLanguage());
        tvAdult.setText(movie.getAdult());
        //extracting movie and is checking to see if the connection to the db is successful
        client.get(String.format(VIDEOS_URL, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if(results.length() == 0)
                    {
                        return;
                    }
                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d("DetailActivity",youtubeKey);
                    initializeYoutube(youtubeKey);  //we only want to initialize youtube once we have gotten the api response
                }
                catch(JSONException e){
                    Log.e("DetailActivity", "Failed to parse JSON", e);

                }
            }



            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

            }
        });




    }

    private void initializeYoutube( final String youtubeKey) { //needs to be final because it used inside of listener
        youtubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                Log.d("DetailActivity", "onInitializationSuccess");

                // do any work here to cue video, play video, etc.
                youTubePlayer.cueVideo(youtubeKey); //passing in the youtubeKey
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("DetailActivity", "onInitializationFailure");
            }
        });
    }
}