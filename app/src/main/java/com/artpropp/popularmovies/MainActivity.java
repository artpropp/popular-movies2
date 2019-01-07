package com.artpropp.popularmovies;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.artpropp.popularmovies.models.Movie;
import com.artpropp.popularmovies.models.MoviesResponse;
import com.artpropp.popularmovies.utilities.ApiService;

import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements MovieListAdapter.OnMovieClickListener, MovieListAdapter.OnLoadMoreListener, ApiService.Observer {

    private static final String FETCH_PAGE = "fetch_page";
    private static final String MOVIE_LIST = "movie_list";
    private static final String API_KEY = "";

    private MovieListAdapter mMovieListAdapter;
    ApiService mApiService;
    private int fetchPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieListAdapter = new MovieListAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.movie_posters_rv);
        int spanCount = 2;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 3;
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
        recyclerView.setAdapter(mMovieListAdapter);

        mApiService = new ApiService(API_KEY);
        mApiService.addObserver(this);

        if (savedInstanceState != null) {
            fetchPage = savedInstanceState.getInt(FETCH_PAGE);
            mMovieListAdapter.setMovieList(savedInstanceState.getParcelableArrayList(MOVIE_LIST));
            mMovieListAdapter.notifyDataSetChanged();
        } else {
            fetchPage = 1;
        }
        mApiService.getPopularMovies(fetchPage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular_movies:
                setActionBarTitle(getString(R.string.popular_movies));
                fetchPage = 1;
                mApiService.getPopularMovies(fetchPage);
                return true;

            case R.id.action_top_rated_movies:
                setActionBarTitle(getString(R.string.top_rated_movies));
                fetchPage = 1;
                mApiService.getTopRatedMovies(fetchPage);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setActionBarTitle(String title) {
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(FETCH_PAGE, fetchPage);
        outState.putParcelableArrayList(MOVIE_LIST, mMovieListAdapter.getMovies());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent detailActivityIntent = new Intent(this, DetailActivity.class);
        detailActivityIntent.putExtra(DetailActivity.MOVIE_EXTRA, movie);
        startActivity(detailActivityIntent);
    }

    @Override
    public void onLoadMore() {
        if (getSupportActionBar() == null || getSupportActionBar().getTitle() == null) return;
        if (getSupportActionBar().getTitle().equals(getString(R.string.top_rated_movies))) {
            mApiService.getTopRatedMovies(fetchPage);
        } else {
            mApiService.getPopularMovies(fetchPage);
        }
    }

    @Override
    public void onSuccess(MoviesResponse moviesResponse) {
        List<Movie> movies = moviesResponse.getResults();
        if (fetchPage == 1) {
            mMovieListAdapter.setMovieList(movies);
        } else {
            mMovieListAdapter.addMovies(movies);
        }
        ++fetchPage;
        mMovieListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(this, getString(R.string.fetching_error_message) + "\nReason: " + errorMessage, Toast.LENGTH_SHORT).show();
    }

}
