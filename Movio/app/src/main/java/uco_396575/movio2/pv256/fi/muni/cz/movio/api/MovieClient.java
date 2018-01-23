package uco_396575.movio2.pv256.fi.muni.cz.movio.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.MovieListDto;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.MoviePersonnel;


public interface MovieClient {

    String API_URL = "http://api.themoviedb.org/3/";//"https://api.themoviedb.org/3/movie/550";//"https://api.themoviedb.org";
    String API_KEY = "918a0448d083b9c38b5ea9e1a1519e6a";
    String API_KEY_PARAM = "api_key=" + API_KEY;
    String MOST_POPULAR = "discover/movie?sort_by=popularity.desc";
    String SCIENCE_FICTION_ID = "878";
    String BEST_SCIFI = "discover/movie?with_genres=" + SCIENCE_FICTION_ID + "&sort_by=vote_average.desc&vote_count.gte=10";

    @GET(MOST_POPULAR + "&" + API_KEY_PARAM)
    Call<MovieListDto> getMostPopular();

    @GET(BEST_SCIFI + "&" + API_KEY_PARAM)
    Call<MovieListDto> getBestScifi();

    @GET("movie/{id}/credits" + "?" + API_KEY_PARAM)
    Call<MoviePersonnel> getCredits(@Path("id") Long movieId);

}
