package uco_396575.movio2.pv256.fi.muni.cz.movio.api;

import okhttp3.HttpUrl;

import static uco_396575.movio2.pv256.fi.muni.cz.movio.api.MovieClient.API_KEY;

/**
 * Created by david on 10-Jan-18.
 */

public class ApiHelper {

    private static String PICTURES_URL = "http://image.tmdb.org/t/p/w185//";
    private static String PICTURE_URL_HIGHER_QUALITY = "http://image.tmdb.org/t/p/w500//";

    public static String buildUrlWithApiKey(String url) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("api_key", API_KEY);
        return urlBuilder.build().toString();
    }

    public static String getPictureAddress(String pictureUrl) {
        return buildUrlWithApiKey(PICTURES_URL + pictureUrl);
    }

    public static String getPictureAddressHigherQuality(String pictureUrl) {
        return buildUrlWithApiKey(PICTURE_URL_HIGHER_QUALITY + pictureUrl);
    }
}
