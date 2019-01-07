package com.artpropp.popularmovies.utilities;

import com.artpropp.popularmovies.models.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMovieDbApi {

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(
            @Query("page") int page,
            @Query("api_key") String apiKey
    );

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(
            @Query("page") int page,
            @Query("api_key") String apiKey
    );

}
