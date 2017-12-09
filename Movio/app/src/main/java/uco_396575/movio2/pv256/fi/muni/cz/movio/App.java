package uco_396575.movio2.pv256.fi.muni.cz.movio;

import android.app.Application;
import android.os.StrictMode;

public class App extends Application {

    public void onCreate() {
        turnOnStrictMode();
        super.onCreate();
    }

    private void turnOnStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .build());
        }
    }
}
