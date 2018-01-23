package uco_396575.movio2.pv256.fi.muni.cz.movio;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import uco_396575.movio2.pv256.fi.muni.cz.movio.db.MovieManager;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.Movie;
import uco_396575.movio2.pv256.fi.muni.cz.movio.sync.UpdaterSyncAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static String DOWNLOAD_INTENT = "download";
    public static String MOVIES_EXTRA_TAG = "movies";
    public static String MOVIE_TYPE_TAG = "movie_type";
    private final String DETAIL_TAG = "detail";
    private final String DETAIL_FRAGMENT_TAG = "detailFragment";
    private final String MAIN_FRAGMENT_TAG = "mainFragment";


    private boolean isShowingDetail = false;
    private int movieType = 0;
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private MovieManager mMovieManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //UpdaterSyncAdapter.initializeSyncAdapter(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_main);
        registerReceiver();
        if(savedInstanceState != null) movieType = savedInstanceState.getInt(MOVIE_TYPE_TAG);
        //if(!movieType)
        startDownloadServiceForMovieType(1);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.list_fragment, MainFragment.newInstance(SingletonData.getInstance().getMovies()), MAIN_FRAGMENT_TAG).commit();

        initActionBar();
        setNavigationViewListener();
        UpdaterSyncAdapter.initializeSyncAdapter(this);

        mMovieManager = new MovieManager(this);
    }

    private void startDownloadServiceForMovieType(int type) {
        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra(MOVIE_TYPE_TAG, type);
        startService(intent);
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

        // mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        if (BuildConfig.LOGGING) {
            Log.i("", "Logging is working!");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.scifi_list: {
                startDownloadServiceForMovieType(0);
                getSupportActionBar().setTitle(R.string.scifi);
                movieType = 0;
                break;
            }
            case R.id.popular_list:
                startDownloadServiceForMovieType(1);
                getSupportActionBar().setTitle(R.string.popular);
                movieType = 1;
                break;
            case R.id.favourite_list:
                //List<Movie> movies = AppDatabase.getInMemoryDatabase(this).movieModel().getAllMovies();
                List<Movie> movies = mMovieManager.getMovies();
                addMainFragment(movies);
                getSupportActionBar().setTitle(R.string.favourite);
                break;
        }
        //close navigation drawer
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = mDrawerLayout.findViewById(R.id.nvView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void registerReceiver() {

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DOWNLOAD_INTENT);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DOWNLOAD_INTENT)) {
                List<Movie> movies = (ArrayList) intent.getSerializableExtra(MOVIES_EXTRA_TAG);
                SingletonData.getInstance().setMovies(movies);
                addMainFragment(movies);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.scifi_list:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.refresh:
                refreshData();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshData() {
        UpdaterSyncAdapter.syncImmediately(this);
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
        outState.putInt(MOVIE_TYPE_TAG, movieType);
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
        //unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    private void addMainFragment(List<Movie> movies) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        MainFragment frag = (MainFragment) fm.findFragmentByTag(MAIN_FRAGMENT_TAG);
        if (frag == null) {
            ft.replace(R.id.list_fragment, MainFragment.newInstance(movies), MAIN_FRAGMENT_TAG).commit();
        } else {
            frag.updateData(movies);
        }
    }

    private void addDetailFragment() {
        DetailFragment fragmentDetail = (DetailFragment) getFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_TAG);
        if (fragmentDetail != null) {
            addDetailFragment(DetailFragment.newInstance(fragmentDetail.getMovie()));
        } else isShowingDetail = false;
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

    public void removeDetailFragment() {
        Fragment fragment = getFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_TAG);
        if (fragment != null) {
            getFragmentManager().popBackStack();
        }
        Fragment fragmentMain = getFragmentManager().findFragmentByTag(MAIN_FRAGMENT_TAG);
        if (fragmentMain != null) {
            getFragmentManager().popBackStack();
        }
    }
}
