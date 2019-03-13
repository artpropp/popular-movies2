package com.artpropp.popularmovies.utilities;

import android.support.annotation.NonNull;

import com.artpropp.popularmovies.models.MoviesResponse;
import com.artpropp.popularmovies.models.ReviewResponse;
import com.artpropp.popularmovies.models.TrailerResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiService implements Callback<MoviesResponse> {

    public interface Observer {
        void onSuccess(MoviesResponse moviesResponse);
        void onError(String errorMessage);
    }

    private static final String baseApiUrl = "https://api.themoviedb.org/3/";

    private final TheMovieDbApi mTheMovieDbApi;
    private final String mApiKey;
    private final List<Observer> mObservers = new ArrayList<>();

    public void addObserver(Observer observer) {
        if (mObservers.contains(observer)) return;
        mObservers.add(observer);
    }

    public ApiService(String apiKey) {
        mApiKey = apiKey;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseApiUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(httpClient)
                .build();
        mTheMovieDbApi = retrofit.create(TheMovieDbApi.class);
    }


    public void getPopularMovies(int page) {
        mTheMovieDbApi.getPopularMovies(page, mApiKey)
                .enqueue(this);
    }

    public void getTopRatedMovies(int page) {
        mTheMovieDbApi.getTopRatedMovies(page, mApiKey)
                .enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
        if (response.isSuccessful() && response.body() != null) {
            onSuccess(response.body());
        } else {
            onError(response.message());
        }
    }

    @Override
    public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
        onError(t.getMessage());
    }

    private void onSuccess(@NonNull MoviesResponse response) {
        for (Observer observer : mObservers) {
            observer.onSuccess(response);
        }
    }

    private void onError(@NonNull String errorMessage) {
        for (Observer observer : mObservers) {
            observer.onError(errorMessage);
        }
    }

    public void getTrailers(int id, Callback<TrailerResponse> callback) {
        mTheMovieDbApi.getTrailers(id, mApiKey).enqueue(callback);
    }

    public void getReviews(int id, Callback<ReviewResponse> callback) {
        mTheMovieDbApi.getReviews(id, mApiKey).enqueue(callback);
    }

}
