package uco_396575.movio2.pv256.fi.muni.cz.movio.api;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Response;


public class DownloadAsyncTask extends AsyncTask<Void,List<MovieDto>,List<MovieDto>>{
    private MovieClient mClient;
    private Context mContext;
    private OnSuccesfullDownload mListener;

    public interface OnSuccesfullDownload {
        void updateData(List<MovieDto> movies);

    }

    public DownloadAsyncTask(MovieClient client, Context context, OnSuccesfullDownload listener) {
        mClient = client;
        mContext = context;
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        //check internet
        super.onPreExecute();
    }

    @Override
    protected List<MovieDto> doInBackground(Void... voids) {
        try {
            Response response = mClient.getAllMovies().execute();
            if (!response.isSuccessful()) {
                return null;
            }
            String result = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<MovieListDto>() {}.getType();
            MovieListDto movieList = gson.fromJson(result, type);
            List<MovieDto> movies = movieList.getMovieDtos();

            return movieList.getMovieDtos();
        } catch (IOException e) {
            //Parse exception
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<MovieDto> movies) {
        mListener.updateData(movies);
        super.onPostExecute(movies);
    }
}
