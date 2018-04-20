package com.example.android.popularmovies;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.android.popularmovies.data.model.Movie;
import com.example.android.popularmovies.utils.ApiUtils;
import com.squareup.picasso.Picasso;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

public class DetailActivity extends AppCompatActivity {

    private Movie mMovie;
    private ImageView mMovieBackdropImageView;
    private ImageView mMoviePosterImageView;
    private TextView mMovieReleaseDateTextView;
    private TextView mMovieRatingTextView;
    private TextView mMovieOriginalTitle;
    private TextView mMovieOverviewTextView;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mToolbar = findViewById(R.id.detail_toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mMovie = getIntent().getParcelableExtra(getString(R.string.MOVIE));

        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbarLayout.setTitle(mMovie.getTitle());
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedTitleTextStyle);
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        mMovieBackdropImageView = findViewById(R.id.iv_movie_backdrop);
        if (mMovie.getBackdropPath() != null) {
            String movieBackdropImageUrl = ApiUtils.getImageUrl(mMovie.getBackdropPath(), "w780");
            Picasso.with(this).load(movieBackdropImageUrl).into(mMovieBackdropImageView);
        }

        mMoviePosterImageView = findViewById(R.id.iv_movie_poster);
        if (mMovie.getPosterPath() != null) {
            String moviePosterImageUrl = ApiUtils.getImageUrl(mMovie.getPosterPath(), "w500");
            Picasso.with(this).load(moviePosterImageUrl).into(mMoviePosterImageView);
        }

        mMovieReleaseDateTextView = findViewById(R.id.tv_movie_release_date);
        String releaseDate = mMovie.getReleaseDate();
        String releaseYear = releaseDate.substring(0,4);
        mMovieReleaseDateTextView.setText(releaseYear);

        mMovieRatingTextView = findViewById(R.id.tv_movie_rating);
        String rating = Double.toString(mMovie.getVoteAverage());
        rating += "/10";
        mMovieRatingTextView.setText(rating);

        mMovieOriginalTitle = findViewById(R.id.tv_movie_original_title);
        mMovieOriginalTitle.setText(mMovie.getOriginalTitle());

        mMovieOverviewTextView = findViewById(R.id.tv_movie_overview);
        mMovieOverviewTextView.setText(mMovie.getOverview());
    }
}
