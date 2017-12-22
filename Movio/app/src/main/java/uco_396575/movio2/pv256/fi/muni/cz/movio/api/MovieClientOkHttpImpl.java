package uco_396575.movio2.pv256.fi.muni.cz.movio.api;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class MovieClientOkHttpImpl extends OkHttpClient implements MovieClient {
    private String url;
    public MovieClientOkHttpImpl() {
        initUrl();
    }

    private void initUrl() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(API_URL).newBuilder();
        urlBuilder.addQueryParameter("api_key", API_KEY);
        url = urlBuilder.build().toString();
    }

    @Override
    public Call getAllMovies() {
        return this.newCall(new Request.Builder()
                .header("api_key", API_KEY)
                .url(url)
                .build());
    }

    @Override
    public Call getLatestReleases() {
        return null;
    }

    @Override
    public String getPicture(String pictureUrl) {
        return PICTURES_URL + pictureUrl+ "?api_key=918a0448d083b9c38b5ea9e1a1519e6a" ;
    }
}
