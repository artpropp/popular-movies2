package com.artpropp.popularmovies.models;

import com.squareup.moshi.Json;

import org.json.JSONArray;

import java.util.List;

public class TrailerResponse {

    @Json(name = "id")
    private Integer id;
    @Json(name = "results")
    private List<Trailer> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }

}
