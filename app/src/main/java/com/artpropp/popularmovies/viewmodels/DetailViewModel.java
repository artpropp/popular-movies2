package com.artpropp.popularmovies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.artpropp.popularmovies.R;
import com.artpropp.popularmovies.models.Movie;

import java.util.Locale;

public class DetailViewModel extends AndroidViewModel {

    private String mTitle;
    private String mReleaseYear;
    private String mPosterUrl;
    private String mVoteAverage;
    private String mOverview;
    private MutableLiveData<String> mButtonTitle;

    public DetailViewModel(@NonNull Application application) {
        super(application);

        mButtonTitle = new MutableLiveData<>();
        mButtonTitle.setValue(application.getString(R.string.mark_as_favorite));
    }

    public void init(Context context, Movie movie) {
        mTitle = movie.getTitle();
        mReleaseYear = movie.getReleaseDate().substring(0, movie.getReleaseDate().indexOf('-'));
        mPosterUrl = movie.getPosterPath();
        mVoteAverage = String.format(Locale.getDefault(), context.getString(R.string.vote_average), movie.getVoteAverage());
        mOverview = movie.getOverview();
    }

    public String getTitle() {
        return mTitle;
    }

    public String getReleaseYear() {
        return mReleaseYear;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public String getOverview() {
        return mOverview;
    }

    public LiveData<String> getButtonTitle() {
        return mButtonTitle;
    }
}
