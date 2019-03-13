package com.artpropp.popularmovies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.artpropp.popularmovies.MainActivity;
import com.artpropp.popularmovies.R;
import com.artpropp.popularmovies.adapters.DetailAdapter;
import com.artpropp.popularmovies.database.FavoriteMovieDao;
import com.artpropp.popularmovies.database.FavoriteMovieEntry;
import com.artpropp.popularmovies.database.MovieDatabase;
import com.artpropp.popularmovies.models.Movie;
import com.artpropp.popularmovies.models.Review;
import com.artpropp.popularmovies.models.ReviewResponse;
import com.artpropp.popularmovies.models.Trailer;
import com.artpropp.popularmovies.models.TrailerResponse;
import com.artpropp.popularmovies.utilities.ApiService;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends AndroidViewModel {

    public interface OnTrailerClickListener {
        void onTrailerClick(String site, String key);
    }

    private DetailAdapter mAdapter;

    private boolean initialized = false;
    private String mTitle;
    private String mReleaseYear;
    private String mPosterUrl;
    private String mVoteAverage;
    private String mOverview;
    private MutableLiveData<String> mButtonTitle;
    private MutableLiveData<List<Trailer>> mTrailers;
    private MutableLiveData<List<Review>> mReviews;
    private LifecycleOwner mLifecycleOwner;
    private OnTrailerClickListener mListener;

    private Movie mMovie;
    private FavoriteMovieDao mFavoriteMovieDao;
    private LiveData<FavoriteMovieEntry> mFavoriteMovieEntryLiveData;
    private Executor mExecutor;


    public DetailViewModel(@NonNull Application application) {
        super(application);

        mTrailers = new MutableLiveData<>();
        mReviews = new MutableLiveData<>();
        mAdapter = new DetailAdapter(this);

        mButtonTitle = new MutableLiveData<>();

        MovieDatabase movieDatabase = MovieDatabase.getInstance(this.getApplication());
        mFavoriteMovieDao = movieDatabase.favoriteMovieDao();
        mExecutor = Executors.newSingleThreadExecutor();
    }

    public void init(LifecycleOwner lifecycleOwner, Movie movie) {
        mLifecycleOwner = lifecycleOwner;
        if (mLifecycleOwner instanceof OnTrailerClickListener) {
            mListener = (OnTrailerClickListener) mLifecycleOwner;
        }
        if (initialized) return;
        mTitle = movie.getTitle();
        mReleaseYear = movie.getReleaseDate().substring(0, movie.getReleaseDate().indexOf('-'));
        mPosterUrl = movie.getPosterPath();
        mVoteAverage = String.format(Locale.getDefault(), getApplication().getString(R.string.vote_average), movie.getVoteAverage());
        mOverview = movie.getOverview();

        mTrailers.observe(lifecycleOwner, trailers -> {
            mAdapter.setTrailers(trailers);
            mAdapter.notifyDataSetChanged();
        });
        mReviews.observe(lifecycleOwner, reviews -> {
            mAdapter.setReviews(reviews);
            mAdapter.notifyDataSetChanged();
        });

        mAdapter.notifyDataSetChanged();
        fetchTrailers(movie);
        fetchReviews(movie);
        fetchFavorite(movie);

        initialized = true;
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
        mExecutor.execute(() -> {
            FavoriteMovieEntry entry = new FavoriteMovieEntry(mMovie);
            if (mFavoriteMovieEntryLiveData.getValue() == null) {
                mFavoriteMovieDao.insert(entry);
            } else {
                mFavoriteMovieDao.delete(entry);
            }
        });
    }

    public void onTrailerClick(String site, String key) {
        if (mListener != null) {
            mListener.onTrailerClick(site, key);
        }
    }

    private void setTrailers(List<Trailer> trailers) {
        mTrailers.setValue(trailers);
    }

    public LiveData<List<Trailer>> getTrailers() {
        return mTrailers;
    }

    private void fetchTrailers(Movie movie) {
        ApiService webservice = new ApiService(MainActivity.API_KEY);
        webservice.getTrailers(movie.getId(), new Callback<TrailerResponse>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    setTrailers(response.body().getResults());
                }
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                // silent failures
            }
        });
    }

    private void setReviews(List<Review> reviews) {
        mReviews.setValue(reviews);
    }

    private void fetchReviews(Movie movie) {
        ApiService webservice = new ApiService(MainActivity.API_KEY);
        webservice.getReviews(movie.getId(), new Callback<ReviewResponse>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    setReviews(response.body().getResults());
                }
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                // silent failures
            }
        });
    }

    private void fetchFavorite(Movie movie) {
        mMovie = movie;
        mFavoriteMovieEntryLiveData = mFavoriteMovieDao.loadById(movie.getId());
        mFavoriteMovieEntryLiveData.observe(getLifecycleOwner(), favoriteMovieEntry -> {
            if (favoriteMovieEntry != null) {
                mButtonTitle.setValue(getApplication().getString(R.string.remove_from_favorites));
            } else {
                mButtonTitle.setValue(getApplication().getString(R.string.mark_as_favorite));
            }
        });
    }

}
