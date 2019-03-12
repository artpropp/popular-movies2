package com.artpropp.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.artpropp.popularmovies.databinding.ActivityDetailBinding;
import com.artpropp.popularmovies.models.Movie;
import com.artpropp.popularmovies.viewmodels.DetailViewModel;

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
        binding.setModel(mViewModel);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
