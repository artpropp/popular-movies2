package com.artpropp.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
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

    private static final String API_KEY = "";

    private MainViewModel mViewModel;
    ApiService mApiService;
    private int mPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovieListAdapter movieListAdapter = new MovieListAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.movie_posters_rv);
        int spanCount = 2;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 3;
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
        recyclerView.setAdapter(movieListAdapter);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mApiService = new ApiService(API_KEY);
        mApiService.addObserver(this);

        mViewModel.getFetchPage().observe(this, this::onPageSet);
        mViewModel.getMovies().observe(this, movies -> {
            movieListAdapter.setMovieList(movies);
            movieListAdapter.notifyDataSetChanged();
        });
        mViewModel.getPageTitle().observe(this, this::setActionBarTitle);

        if (mViewModel.getPageTitle().getValue() != null) {
            setActionBarTitle(mViewModel.getPageTitle().getValue());
        }
        if (mViewModel.getFetchPage().getValue() == null
                || mViewModel.getFetchPage().getValue() == 0) {
            mViewModel.setFetchPage(1);
            fetchPage(1);
        }
    }

    private void onPageSet(Integer page) {
        mPage = page == null ? 1 : page;
    }

    private void fetchPage(int page) {
        if (getSupportActionBar() == null || getSupportActionBar().getTitle() == null) return;
        if (getSupportActionBar().getTitle().equals(getString(R.string.top_rated_movies))) {
            mApiService.getTopRatedMovies(page);
        } else {
            mApiService.getPopularMovies(page);
        }
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
                mViewModel.setPageTitle(getString(R.string.popular_movies));
                mViewModel.setFetchPage(1);
                fetchPage(1);
                return true;

            case R.id.action_top_rated_movies:
                mViewModel.setPageTitle(getString(R.string.top_rated_movies));
                mViewModel.setFetchPage(1);
                fetchPage(1);
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
    public void onItemClick(Movie movie) {
        Intent detailActivityIntent = new Intent(this, DetailActivity.class);
        detailActivityIntent.putExtra(DetailActivity.MOVIE_EXTRA, movie);
        startActivity(detailActivityIntent);
    }

    @Override
    public void onLoadMore() {
        mViewModel.setFetchPage(++mPage);
        fetchPage(mPage);
    }

    @Override
    public void onSuccess(MoviesResponse moviesResponse) {
        List<Movie> movies = moviesResponse.getResults();
        if (mPage == 1) {
            mViewModel.setMovies(movies);
        } else {
            mViewModel.addMovies(movies);
        }
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(this, getString(R.string.fetching_error_message) + "\nReason: " + errorMessage, Toast.LENGTH_SHORT).show();
    }

}
