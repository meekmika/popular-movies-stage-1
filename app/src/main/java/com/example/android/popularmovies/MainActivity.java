package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.popularmovies.data.model.Movie;
import com.example.android.popularmovies.data.model.TMDBResponse;
import com.example.android.popularmovies.data.remote.TMDBService;
import com.example.android.popularmovies.utils.ApiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MoviePosterAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Nullable @BindView(R.id.rv_movies) RecyclerView mRecyclerView;
    @Nullable @BindView(R.id.error_message_display) LinearLayout mErrorMessageDisplay;
    @Nullable @BindView(R.id.pb_loading_indicator) ProgressBar mLoadingIndicator;
    private TMDBService mService;
    private MoviePosterAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        mService = ApiUtils.getTMDBService();
        mAdapter = new MoviePosterAdapter(this, this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        if(isOnline()) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
            loadAnswers();
            showData();
        }
        else showErrorMessage();
    }

    public void loadAnswers() {
        mService.getPopular().enqueue(new Callback<TMDBResponse>() {
            @Override
            public void onResponse(Call<TMDBResponse> call, Response<TMDBResponse> response) {
                if(response.isSuccessful()) {
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    mAdapter.setMovieData(response.body().getResults());
                    Log.d(TAG, "posts loaded from API");
                } else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                    Log.v(TAG, "Request error. Status code: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<TMDBResponse> call, Throwable t) {
                Log.d(TAG, "error loading from API");
            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onClick(Movie selectedMovie) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(getString(R.string.MOVIE), selectedMovie);
        startActivity(intentToStartDetailActivity);
    }

    private void showErrorMessage() {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void showData() {
        mErrorMessageDisplay.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
