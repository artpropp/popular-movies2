package com.artpropp.popularmovies.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.artpropp.popularmovies.R;
import com.artpropp.popularmovies.models.Movie;
import com.artpropp.popularmovies.utilities.ImageService;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MoviePosterViewHolder> {

    public interface OnMovieClickListener {
        void onItemClick(Movie movie);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private List<Movie> mMovieList;
    private OnMovieClickListener mMovieClickListener;
    private OnLoadMoreListener mLoadMoreListener;

    public MainAdapter(OnMovieClickListener movieClickListener) {
        mMovieClickListener = movieClickListener;
        if (movieClickListener instanceof OnLoadMoreListener) {
            mLoadMoreListener = (OnLoadMoreListener) movieClickListener;
        }
    }

    public void setMovieList(List<Movie> movieList) {
        mMovieList = movieList;
    }

    void addMovies(List<Movie> movies) {
        mMovieList.addAll(movies);
    }

    ArrayList<Movie> getMovies() {
        return new ArrayList<>(mMovieList);
    }

    @NonNull
    @Override
    public MoviePosterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_holder_movie_poster, viewGroup, false);
        return new MoviePosterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviePosterViewHolder viewHolder, int i) {
        Movie movie = mMovieList.get(i);
        ImageService.loadTile(movie.getPosterPath(), viewHolder.imageView);
        viewHolder.itemView.setOnClickListener(v -> mMovieClickListener.onItemClick(movie));

        if (i == getItemCount() - 1 && mLoadMoreListener != null) {
            mLoadMoreListener.onLoadMore();
        }
    }

    @Override
    public int getItemCount() {
        if (mMovieList == null) return 0;
        return mMovieList.size();
    }

    class MoviePosterViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        MoviePosterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_iv);
        }

    }

}
