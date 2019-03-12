package com.artpropp.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.artpropp.popularmovies.databinding.ActivityDetailBinding;
import com.artpropp.popularmovies.models.Movie;
import com.artpropp.popularmovies.utilities.ImageService;
import com.artpropp.popularmovies.viewmodels.DetailViewModel;
import com.squareup.picasso.Callback;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA = "movie_extra";

    private DetailViewModel mViewModel;
    private ActivityDetailBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(MOVIE_EXTRA)) {
            closeOnError();
            return;
        }

        Movie movie = intent.getParcelableExtra(MOVIE_EXTRA);
        mViewModel.init(this, movie);
        mDataBinding.setModel(mViewModel);
        loadImage();
    }

    private void loadImage() {
        ImageService.loadPoster(mViewModel.getPosterUrl(), mDataBinding.moviePosterIv, new Callback() {
            @Override
            public void onSuccess() {
                // nothing else to do here
            }

            @Override
            public void onError(Exception e) {
                mDataBinding.moviePosterIv.setVisibility(View.GONE);
            }
        });
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
