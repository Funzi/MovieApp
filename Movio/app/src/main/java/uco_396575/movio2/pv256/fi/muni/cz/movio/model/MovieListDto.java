package uco_396575.movio2.pv256.fi.muni.cz.movio.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieListDto {
    @SerializedName("page")
    int page;

    @SerializedName("results")
    List<Movie> movies;

    @SerializedName("total_pages")
    int totalPages;

    @SerializedName("total_results")
    int totalResults;

    public int getPage() {
        return page;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
