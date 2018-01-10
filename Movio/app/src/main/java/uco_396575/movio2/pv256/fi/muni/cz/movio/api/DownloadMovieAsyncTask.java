package uco_396575.movio2.pv256.fi.muni.cz.movio.api;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.Movie;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.MovieListDto;


public class DownloadMovieAsyncTask extends AsyncTask<Void, List<Movie>, List<Movie>> {
    private MovieClient mClient;
    private OnSuccessfulDownload mListener;

    public interface OnSuccessfulDownload {
        void updateData(List<Movie> movies);

    }

    public DownloadMovieAsyncTask(MovieClient client, OnSuccessfulDownload listener) {
        mClient = client;
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        //check internet
        super.onPreExecute();
    }

    @Override
    protected List<Movie> doInBackground(Void... voids) {
        try {
            Response response = mClient.getBestScifi().execute();
            if (!response.isSuccessful()) {
                return null;
            }
            String result = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<MovieListDto>() {
            }.getType();
            MovieListDto movieList = gson.fromJson(result, type);
            List<Movie> movies = movieList.getMovies();
            return movies;
        } catch (IOException e) {
            //Parse exception
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies == null) {
            movies = new ArrayList<>();
        }
        mListener.updateData(movies);
        super.onPostExecute(movies);
    }
}
