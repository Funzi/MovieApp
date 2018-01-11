package uco_396575.movio2.pv256.fi.muni.cz.movio.api;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
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
            Response<MovieListDto> response = mClient.getBestScifi().execute();
            if (!response.isSuccessful()) {
                return null;
            }
            List<Movie> movies = response.body().getMovies();
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
