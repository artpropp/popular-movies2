package com.artpropp.popularmovies.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.artpropp.popularmovies.models.Movie;

@Entity(tableName = "favoriteMovies")
public class FavoriteMovieEntry {

    @PrimaryKey
    private int id;
    private String posterPath;
    private String overview;
    private String releaseDate;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String backdropPath;
    private Double popularity;
    private Integer voteCount;
    private Boolean video;
    private Double voteAverage;

    public FavoriteMovieEntry(int id,String posterPath, String overview, String releaseDate,
                              String originalTitle, String originalLanguage, String title,
                              String backdropPath, Double popularity, Integer voteCount,
                              Boolean video, Double voteAverage) {
        this.id = id;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Ignore
    public FavoriteMovieEntry(Movie movie) {
        this.id = movie.getId();
        this.posterPath = movie.getPosterPath();
        this.overview = movie.getOverview();
        this.releaseDate = movie.getReleaseDate();
        this.originalTitle = movie.getOriginalTitle();
        this.originalLanguage = movie.getOriginalLanguage();
        this.title = movie.getTitle();
        this.backdropPath = movie.getBackdropPath();
        this.popularity = movie.getPopularity();
        this.voteCount = movie.getVoteCount();
        this.video = movie.getVideo();
        this.voteAverage = movie.getVoteAverage();
    }

    @Ignore
    public Movie toMovie() {
        Movie movie = new Movie(id);
        movie.setPosterPath(posterPath);
        movie.setOverview(overview);
        movie.setReleaseDate(releaseDate);
        movie.setOriginalTitle(originalTitle);
        movie.setOriginalLanguage(originalLanguage);
        movie.setTitle(title);
        movie.setBackdropPath(backdropPath);
        movie.setPopularity(popularity);
        movie.setVoteCount(voteCount);
        movie.setVideo(video);
        movie.setVoteAverage(voteAverage);
        return movie;
    }

}
