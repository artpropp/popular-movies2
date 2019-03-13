package com.artpropp.popularmovies.models;

import com.squareup.moshi.Json;

import java.util.List;

public class ReviewResponse {

    @Json(name = "id")
    private Integer id;
    @Json(name = "page")
    private Integer page;
    @Json(name = "results")
    private List<Review> results = null;
    @Json(name = "total_results")
    private Integer totalResults;
    @Json(name = "total_pages")
    private Integer totalPages;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

}
