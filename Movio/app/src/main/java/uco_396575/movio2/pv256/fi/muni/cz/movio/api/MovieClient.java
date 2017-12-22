package uco_396575.movio2.pv256.fi.muni.cz.movio.api;

import okhttp3.Call;


public interface MovieClient {

    String API_URL = "https://api.themoviedb.org/3/discover/movie";//"https://api.themoviedb.org/3/movie/550";//"https://api.themoviedb.org";
    String PICTURES_URL = "http://image.tmdb.org/t/p/w185//";
    String API_KEY = "918a0448d083b9c38b5ea9e1a1519e6a";


    Call getAllMovies();

    Call getLatestReleases();

    String getPicture(String pictureUrl);
}
