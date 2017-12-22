package uco_396575.movio2.pv256.fi.muni.cz.movio.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by david on 16-Dec-17.
 */

public class MovieListDto {
    @SerializedName("page")
    int page;

    @SerializedName("results")
    List<MovieDto> movieDtos;

    @SerializedName("total_pages")
    int totalPages;

    @SerializedName("total_results")
    int totalResults;

    public int getPage() {
        return page;
    }

    public List<MovieDto> getMovieDtos() {
        return movieDtos;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
