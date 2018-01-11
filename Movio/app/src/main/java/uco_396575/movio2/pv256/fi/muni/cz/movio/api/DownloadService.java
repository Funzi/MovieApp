package uco_396575.movio2.pv256.fi.muni.cz.movio.api;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import uco_396575.movio2.pv256.fi.muni.cz.movio.MainActivity;
import uco_396575.movio2.pv256.fi.muni.cz.movio.R;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.Movie;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.MovieListDto;


public class DownloadService extends IntentService {

    public DownloadService() {
        super("Download Service");
    }

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.star_wars)
                .setContentTitle(getString(R.string.download))
                .setContentText("Downloading Data")
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());

        Download();

    }

    private void Download(){
        sendNotificationText(getString(R.string.downloading_data));
        MovieClient client =new MovieClientRetrofitImpl();

        try {
            Response<MovieListDto> movies = client.getBestScifi().execute();
            onDownloadComplete(movies.body().getMovies());
        } catch (IOException e) {
            e.printStackTrace();
            sendNotificationText(getString(R.string.download_error));
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }

    private void sendNotificationText(String text){

        notificationBuilder.setProgress(100,0,true);
        notificationBuilder.setContentText(text);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendIntent(List<Movie> movies){
        Intent intent = new Intent(MainActivity.DOWNLOAD_INTENT);
        intent.putExtra(MainActivity.MOVIES_EXTRA_TAG, (ArrayList) movies);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    private void onDownloadComplete(List<Movie> movies){

        sendIntent(movies);
        notificationManager.cancel(0);
        notificationBuilder.setProgress(0,0,false);
        notificationBuilder.setContentText(getString(R.string.download_success));
        notificationManager.notify(0, notificationBuilder.build());

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }
}
