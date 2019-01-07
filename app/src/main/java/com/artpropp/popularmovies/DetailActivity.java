package com.artpropp.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.artpropp.popularmovies.models.Movie;
import com.artpropp.popularmovies.utilities.ImageService;
import com.squareup.picasso.Callback;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA = "movie_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(MOVIE_EXTRA)) {
            closeOnError();
            return;
        }

        Movie movie = intent.getParcelableExtra(MOVIE_EXTRA);
        populateUI(movie);
    }

    private void populateUI(Movie movie) {
        final ImageView moviePosterImageView = findViewById(R.id.movie_poster_iv);
        ImageService.loadPoster(movie.getPosterPath(), moviePosterImageView, new Callback() {
            @Override
            public void onSuccess() {
                // nothing else to do here
            }

            @Override
            public void onError(Exception e) {
                moviePosterImageView.setVisibility(View.GONE);
            }
        });

        TextView titleTextView = findViewById(R.id.title_tv);
        titleTextView.setText(movie.getTitle());

        TextView releaseDateTextView = findViewById(R.id.release_date_tv);
        releaseDateTextView.append(" " + movie.getReleaseDate());

        TextView voteAverageTextView = findViewById(R.id.vote_average_tv);
        voteAverageTextView.append((" " + movie.getVoteAverage()));

        TextView plotSynopsisTextView = findViewById(R.id.plot_synopsis_tv);
        plotSynopsisTextView.setText(movie.getOverview());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
