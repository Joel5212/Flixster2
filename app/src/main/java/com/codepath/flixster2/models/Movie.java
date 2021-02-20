package com.codepath.flixster2.models;

import org.json.JSONArray;
import org.json.JSONException;  //if any of the getters fail the constructor will notify and the getters are responsible for handling that exception
import org.json.JSONObject;
import org.parceler.Parcel;
import org.parceler.ParcelClass;

import java.util.ArrayList;
import java.util.List;
@Parcel()
public class Movie {
    int movieId;
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    double rating;
    String releaseDate;
    String adult;
    String originalLanguage;



    //Empty Constructor needed by the Parceler Library
    public Movie()
    {

    }



    public Movie(JSONObject jsonObject) throws JSONException   //extracting each field in the JSONObject class
    {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
        releaseDate = jsonObject.getString("release_date");
        adult = jsonObject.getString("adult");
        originalLanguage = jsonObject.getString("original_language");
        movieId = jsonObject.getInt("id");



    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i = 0;i < movieJsonArray.length(); i++)
        {
            movies.add(new Movie(movieJsonArray.getJSONObject(i))); // add a movie at each position of the array
        }
        return movies;

    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s" , posterPath);
    }

    public String getBackdropPath()
    {
       return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() {
        return rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getAdult() {
        return adult;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public int getMovieId()
    {
        return movieId;
    }
}
