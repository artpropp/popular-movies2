package com.artpropp.popularmovies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.artpropp.popularmovies.R;
import com.artpropp.popularmovies.adapters.DetailAdapter;
import com.artpropp.popularmovies.models.Movie;
import com.artpropp.popularmovies.models.Trailer;

import java.util.List;
import java.util.Locale;

public class DetailViewModel extends AndroidViewModel {

    private DetailAdapter mAdapter;

    private String mTitle;
    private String mReleaseYear;
    private String mPosterUrl;
    private String mVoteAverage;
    private String mOverview;
    private MutableLiveData<String> mButtonTitle;
    private MutableLiveData<List<Trailer>> mTrailers;
    private LifecycleOwner mLifecycleOwner;

    public DetailViewModel(@NonNull Application application) {
        super(application);

        mTrailers = new MutableLiveData<>();
        mAdapter = new DetailAdapter(this);

        mButtonTitle = new MutableLiveData<>();
        mButtonTitle.setValue(application.getString(R.string.mark_as_favorite));
    }

    public void init(LifecycleOwner lifecycleOwner, Movie movie) {
        mTitle = movie.getTitle();
        mReleaseYear = movie.getReleaseDate().substring(0, movie.getReleaseDate().indexOf('-'));
        mPosterUrl = movie.getPosterPath();
        mVoteAverage = String.format(Locale.getDefault(), getApplication().getString(R.string.vote_average), movie.getVoteAverage());
        mOverview = movie.getOverview();

        mTrailers.observe(lifecycleOwner, trailers -> {
            mAdapter.setTrailers(trailers);
            mAdapter.notifyDataSetChanged();
        });
        mLifecycleOwner = lifecycleOwner;

        mAdapter.notifyDataSetChanged();
    }

    public DetailAdapter getAdapter() {
        return mAdapter;
    }

    public LifecycleOwner getLifecycleOwner() {
        return mLifecycleOwner;
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

    public void onButtonClick() {
        mButtonTitle.setValue(getApplication().getString(R.string.remove_from_favorites));
    }

    public void onTrailerClick(String site, String key) {
        // TODO: implement intent to view the trailer
    }

    public void setTrailers(List<Trailer> trailers) {
        mTrailers.setValue(trailers);
    }

    public LiveData<List<Trailer>> getTrailers() {
        return mTrailers;
    }
}
