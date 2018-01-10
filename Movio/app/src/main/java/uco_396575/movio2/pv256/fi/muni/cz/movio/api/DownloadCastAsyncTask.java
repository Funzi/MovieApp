package uco_396575.movio2.pv256.fi.muni.cz.movio.api;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Response;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.MoviePersonnel;

/**
 * Created by david on 10-Jan-18.
 */

public class DownloadCastAsyncTask extends AsyncTask<Void, MoviePersonnel, MoviePersonnel> {
    private MovieClient mClient;
    private OnSuccessfulCastDownload mListener;
    private Integer movieId;

    public interface OnSuccessfulCastDownload {
        void updateData(MoviePersonnel movies);

    }

    public DownloadCastAsyncTask(MovieClient client, OnSuccessfulCastDownload listener, Integer movieId) {
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
            Response response = mClient.getCredits(movieId).execute();
            if (!response.isSuccessful()) {
                return null;
            }
            String result = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<MoviePersonnel>() {
            }.getType();
            MoviePersonnel moviePersonnel = gson.fromJson(result, type);
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
