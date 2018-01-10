package uco_396575.movio2.pv256.fi.muni.cz.movio.api;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class MovieClientOkHttpImpl extends OkHttpClient implements MovieClient {
    public MovieClientOkHttpImpl() {
        initUrl();
    }

    private void initUrl() {
    }

    @Override
    public Call getMostPopular() {
        return getCallWithSuffix(API_URL + MOST_POPULAR);
    }

    @Override
    public Call getBestScifi() {
        return getCallWithSuffix(API_URL + BEST_SCIFI);
    }

    @Override
    public Call getCredits(Integer movieId) {
        return getCallWithSuffix(API_URL + "/movie/" + movieId.toString() + "/credits");
    }


    @Override
    public String getPicture(String pictureUrl) {
        return buildUrlWithApiKey(PICTURES_URL + pictureUrl);
    }

    @Override
    public String getPictureHigherQuality(String pictureUrl) {
        return buildUrlWithApiKey(PICTURE_URL_HIGHER_QUALITY + pictureUrl);
    }

    Call getCallWithSuffix(String url) {
        return this.newCall(new Request.Builder()
                .url(buildUrlWithApiKey(url))
                .build());
    }

    private String buildUrlWithApiKey(String url) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("api_key", API_KEY);
        return urlBuilder.build().toString();
    }
}
