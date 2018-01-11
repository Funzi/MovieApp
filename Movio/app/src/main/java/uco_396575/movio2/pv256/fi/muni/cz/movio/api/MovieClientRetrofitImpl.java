package uco_396575.movio2.pv256.fi.muni.cz.movio.api;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.MovieListDto;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.MoviePersonnel;


public class MovieClientRetrofitImpl implements MovieClient{

    private MovieClient client;

    public MovieClientRetrofitImpl() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit = builder.client(httpClient.build()).build();

        client = retrofit.create(MovieClient.class);
    }


    @Override
    public Call<MovieListDto> getMostPopular() {
        return client.getMostPopular();
    }

    @Override
    public Call<MovieListDto> getBestScifi() {
        return client.getBestScifi();
    }

    @Override
    public Call<MoviePersonnel> getCredits(int movieId) {
        return client.getCredits(movieId);
    }
}
