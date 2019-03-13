package com.artpropp.popularmovies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.artpropp.popularmovies.database.FavoriteMovieDao;
import com.artpropp.popularmovies.database.FavoriteMovieEntry;
import com.artpropp.popularmovies.database.MovieDatabase;
import com.artpropp.popularmovies.models.Movie;

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

    public void setPageTitle(String pageTitle) {
        mPageTitle.setValue(pageTitle);
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

    public LiveData<List<FavoriteMovieEntry>> getFavoriteMovies() {
        return mFavoriteMovies;
    }

}
