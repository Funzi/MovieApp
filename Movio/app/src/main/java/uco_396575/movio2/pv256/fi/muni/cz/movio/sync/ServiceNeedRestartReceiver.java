package uco_396575.movio2.pv256.fi.muni.cz.movio.sync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class ServiceNeedRestartReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        UpdaterSyncAdapter.initializeSyncAdapter(context);
    }
}
