package com.codepath.flixster2.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.flixster2.DetailActivity;
import com.codepath.flixster2.R;
import com.codepath.flixster2.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context; //where the view is being constructed from
    List<Movie> movies;  //List of movies that the adapter needs to hold onto

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    //Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    //Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the movie at the passed in position
        Log.d("MovieAdapter", "onBindViewHolder");
        Movie movie = movies.get(position);
        //Bind the movie data into the VH, we are taking the member variables from viewholder and populating them with the data of movies
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {

        return movies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder   //ViewHolder is a representation of the row in the recycler view, holds refrences to each element in one row of the recycler view
    {
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);     //where the TextView and ImageView is coming from
            tvOverview = itemView.findViewById(R.id.tvOverview);  //we are defining the viewholder
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);


        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            //if phone is in landscape
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                //then imageUrl = back drop image
                imageUrl = movie.getBackdropPath();
            }
            else
            {
                //then imageUrl = back drop image
                imageUrl = movie.getPosterPath();
            }



            Glide.with(context).load(imageUrl).into(ivPoster);    //Glide is an in-built way to render images
            // 1. Register click listener on the whole row

            container.setOnClickListener(new View.OnClickListener() {  //registering click listener so we know when to switch to the new activity
                @Override
                public void onClick(View v) {
                    // 2. Navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));  //If object is Parceable then android will know how to break it down and receive it in the receiving activity
                    context.startActivity(i);//Starts detail activity
                }
            });
        }
    }
}
