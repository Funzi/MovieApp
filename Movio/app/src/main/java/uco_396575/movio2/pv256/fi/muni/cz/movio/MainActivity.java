package uco_396575.movio2.pv256.fi.muni.cz.movio;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import uco_396575.movio2.pv256.fi.muni.cz.movio.api.DownloadService;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.Movie;

public class MainActivity extends AppCompatActivity {

    public static String DOWNLOAD_INTENT = "download";
    public static String MOVIES_EXTRA_TAG = "movies";
    private final String DETAIL_TAG = "detail";
    private final String DETAIL_FRAGMENT_TAG = "detailFragment";
    private final String MAIN_FRAGMENT_TAG = "mainFragment";

    private boolean isShowingDetail = false;
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_main);
        registerReceiver();
        Intent intent = new Intent(this,DownloadService.class);
        startService(intent);

        initActionBar();
    }

    private void initActionBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        if(BuildConfig.LOGGING) {
            Log.i("","Logging is working!");
        }
    }

    private void addMainFragment(List<Movie> movies) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.list_fragment, MainFragment.newInstance(movies), MAIN_FRAGMENT_TAG).commit();
    }

    private void registerReceiver(){

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DOWNLOAD_INTENT);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(DOWNLOAD_INTENT)){

                List<Movie> movies = (ArrayList) intent.getSerializableExtra(MOVIES_EXTRA_TAG);
                addMainFragment(movies);
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        fm.popBackStack();
        isShowingDetail = false;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        isShowingDetail = savedInstanceState.getBoolean(DETAIL_TAG);
        removeDetailFragment();
        if (isShowingDetail) {
            addDetailFragment();
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(DETAIL_TAG, isShowingDetail);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //registerReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    public void removeDetailFragment() {
        Fragment fragment = getFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_TAG);
        if (fragment != null) {
            getFragmentManager().popBackStack();
        }
    }

    private void addDetailFragment() {
        DetailFragment fragmentDetail = (DetailFragment) getFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_TAG);
        if (fragmentDetail != null) {
            addDetailFragment(DetailFragment.newInstance(fragmentDetail.getMovie()));
        }
        else isShowingDetail = false;
    }

    private void addDetailFragment(DetailFragment fragmentDetail) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft;
        ft = fm.beginTransaction();
        if (getResources().getBoolean(R.bool.twoPaneMode)) {
            ft.replace(R.id.detail_fragment, fragmentDetail, DETAIL_FRAGMENT_TAG);
        } else {
            ft.replace(R.id.list_fragment, fragmentDetail, DETAIL_FRAGMENT_TAG);
        }
        ft.addToBackStack(null).commit();
        isShowingDetail = true;
    }

    public void addDetailFragmentWithMovie(Movie movie) {
        addDetailFragment(DetailFragment.newInstance(movie));
    }
}
