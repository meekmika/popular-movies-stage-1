package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.data.model.Movie;
import com.example.android.popularmovies.utils.ApiUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mika on 2018-04-13.
 */

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MoviePosterViewHolder> {

    private List<Movie> mMovies;
    private Context mContext;
    private final MovieAdapterOnClickHandler mClickHandler;

    public MoviePosterAdapter(MovieAdapterOnClickHandler clickHandler, Context context) {
        mMovies = new ArrayList<Movie>(0);
        mContext = context;
        mClickHandler = clickHandler;
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie selectedMovie);
    }

    class MoviePosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mMoviePosterImageView;

        public MoviePosterViewHolder(View view) {
            super(view);
            mMoviePosterImageView = view.findViewById(R.id.movie_poster_iv);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mMovies.get(adapterPosition));
        }
    }

    @Override
    public MoviePosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_poster_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MoviePosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviePosterViewHolder holder, int position) {
        String posterPath = mMovies.get(position).getPosterPath();
        String url = ApiUtils.getImageUrl(posterPath);
        Picasso.with(mContext).load(url).into(holder.mMoviePosterImageView);
        holder.mMoviePosterImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) return 0;
        return mMovies.size();
    }

    public void setMovieData(List<Movie> movieData) {
        mMovies = movieData;
        notifyDataSetChanged();
    }
}
