package com.codemonk.introtopopularmovies.api.tmdb.rest.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TMDBResponse {

    @SerializedName("page")
    private Integer page;

    @SerializedName("results")
    private List<TMDBModel> results = new ArrayList<TMDBModel>();

    @SerializedName("total_results")
    private Integer totalResults;

    @SerializedName("total_pages")
    private Integer totalPages;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public ArrayList<TMDBModel> getResults() {
        return new ArrayList<TMDBModel>(results);
    }

    public void setResults(List<TMDBModel> results) {
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
