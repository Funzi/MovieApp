package uco_396575.movio2.pv256.fi.muni.cz.movio.api;

import okhttp3.Call;


public interface MovieClient {

    String API_URL = "https://api.themoviedb.org/3";//"https://api.themoviedb.org/3/movie/550";//"https://api.themoviedb.org";
    String PICTURES_URL = "http://image.tmdb.org/t/p/w185//";
    String PICTURE_URL_HIGHER_QUALITY = "http://image.tmdb.org/t/p/w500//";
    String API_KEY = "918a0448d083b9c38b5ea9e1a1519e6a";
    String MOST_POPULAR = "/discover/movie?sort_by=popularity.desc";
    String SCIENCE_FICTION_ID = "878";
    String BEST_SCIFI = "/discover/movie?with_genres=" + SCIENCE_FICTION_ID + "&sort_by=vote_average.desc&vote_count.gte=10";


    Call getMostPopular();

    Call getBestScifi();

    Call getCredits(Integer movieId);

    String getPicture(String pictureUrl);

    String getPictureHigherQuality(String pictureUrl);
}
