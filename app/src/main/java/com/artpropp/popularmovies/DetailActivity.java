package com.artpropp.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.artpropp.popularmovies.databinding.ActivityDetailBinding;
import com.artpropp.popularmovies.models.Movie;
import com.artpropp.popularmovies.models.TrailerResponse;
import com.artpropp.popularmovies.utilities.ApiService;
import com.artpropp.popularmovies.viewmodels.DetailViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA = "movie_extra";

    private DetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(MOVIE_EXTRA)) {
            closeOnError();
            return;
        }
        Movie movie = intent.getParcelableExtra(MOVIE_EXTRA);

        mViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        mViewModel.init(this, movie);

        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        binding.setLifecycleOwner(this);
        binding.setModel(mViewModel);

        getTrailers(movie);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void getTrailers(Movie movie) {
        ApiService webservice = new ApiService(MainActivity.API_KEY);
        webservice.getTrailers(movie.getId(), new Callback<TrailerResponse>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mViewModel.setTrailers(response.body().getResults());
                }
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                 // silent failures
            }
        });
    }
}
