package uco_396575.movio2.pv256.fi.muni.cz.movio.api;

import android.os.AsyncTask;

import java.io.IOException;

import retrofit2.Response;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.MoviePersonnel;


public class DownloadCastAsyncTask extends AsyncTask<Void, MoviePersonnel, MoviePersonnel> {
    private MovieClient mClient;
    private OnSuccessfulCastDownload mListener;
    private Long movieId;

    public interface OnSuccessfulCastDownload {
        void updateData(MoviePersonnel movies);

    }

    public DownloadCastAsyncTask(MovieClient client, OnSuccessfulCastDownload listener, Long movieId) {
        mClient = client;
        mListener = listener;
        this.movieId = movieId;
    }

    @Override
    protected void onPreExecute() {
        //check internet
        super.onPreExecute();
    }

    @Override
    protected MoviePersonnel doInBackground(Void... voids) {
        try {
            Response<MoviePersonnel> response = mClient.getCredits(movieId).execute();
            if (!response.isSuccessful()) {
                return null;
            }
            MoviePersonnel moviePersonnel = response.body();

            return moviePersonnel;
        } catch (IOException e) {
            //Parse exception
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(MoviePersonnel crew) {
        if (crew == null) {
            return;
        }
        mListener.updateData(crew);
        super.onPostExecute(crew);
    }
}
