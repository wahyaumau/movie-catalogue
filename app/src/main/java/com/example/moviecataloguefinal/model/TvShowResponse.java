package com.example.moviecataloguefinal.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TvShowResponse {
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private ArrayList<TvShow> tvShows;

    public TvShowResponse(int page, int totalResults, int totalPages, ArrayList<TvShow> tvShows) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.tvShows = tvShows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<TvShow> getTvShows() {
        return tvShows;
    }

    public void setTvShows(ArrayList<TvShow> tvShows) {
        this.tvShows = tvShows;
    }
}
