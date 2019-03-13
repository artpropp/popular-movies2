package com.artpropp.popularmovies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.artpropp.popularmovies.R;
import com.artpropp.popularmovies.database.FavoriteMovieEntry;
import com.artpropp.popularmovies.database.MovieDatabase;
import com.artpropp.popularmovies.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<String> mPageTitle;
    private MutableLiveData<List<Movie>> mMovies;
    private MutableLiveData<Integer> mFetchPage;

    private LiveData<List<FavoriteMovieEntry>> mFavoriteMovies;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mPageTitle = new MutableLiveData<>();
        mMovies = new MutableLiveData<>();
        mFetchPage = new MutableLiveData<>();

        MovieDatabase movieDatabase = MovieDatabase.getInstance(this.getApplication());
        mFavoriteMovies = movieDatabase.favoriteMovieDao().loadAll();
    }

    public void setLifeCycleOwner(LifecycleOwner lifeCycleOwner) {
        mFavoriteMovies.observe(lifeCycleOwner, favoriteMovieEntries -> {
            if (favoriteMovieEntries == null || !isFavoritesPagesSelected()) return;
            setMoviesFromFavorites(favoriteMovieEntries);
        });
    }

    private void setMoviesFromFavorites(List<FavoriteMovieEntry> favoriteMovieEntries) {
        List<Movie> movies = new ArrayList<>();
        if (favoriteMovieEntries != null) {
            for (FavoriteMovieEntry entry: favoriteMovieEntries) {
                movies.add(entry.toMovie());
            }
        }
        mMovies.setValue(movies);
    }

    private boolean isFavoritesPagesSelected() {
        String selected = mPageTitle.getValue();
        String favorites = getApplication().getString(R.string.favorite_movies);
        return selected != null && selected.equals(favorites);
    }

    public void setPageTitle(String pageTitle) {
        mPageTitle.setValue(pageTitle);
        if (isFavoritesPagesSelected()) {
            setMoviesFromFavorites(mFavoriteMovies.getValue());
        }
    }

    public LiveData<String> getPageTitle() {
        return mPageTitle;
    }

    public void setMovies(List<Movie> movies) {
        mMovies.setValue(movies);
    }

    public void addMovies(List<Movie> movies) {
        List<Movie> previousList = mMovies.getValue();
        if (previousList == null) {
            setMovies(movies);
            return;
        }
        previousList.addAll(movies);
        mMovies.setValue(previousList);
    }

    public LiveData<List<Movie>> getMovies() {
        return mMovies;
    }

    public void setFetchPage(int fetchPage) {
        mFetchPage.setValue(fetchPage);
    }

    public LiveData<Integer> getFetchPage() {
        return mFetchPage;
    }

}
