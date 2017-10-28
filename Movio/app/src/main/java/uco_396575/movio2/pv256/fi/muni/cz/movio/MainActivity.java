package uco_396575.movio2.pv256.fi.muni.cz.movio;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final String DETAIL_TAG = "detail";
    private final String DETAIL_FRAGMENT_TAG = "detailFragment";
    private final String MAIN_FRAGMENT_TAG = "mainFragment";

    private boolean isShowingDetail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_main);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.list_fragment, MainFragment.newInstance(), MAIN_FRAGMENT_TAG).commit();
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
        if (isShowingDetail) addDetailFragment();

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(DETAIL_TAG, isShowingDetail);
        super.onSaveInstanceState(outState);
    }

    public void removeDetailFragment() {
        Fragment fragment = getFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_TAG);
        if (fragment != null) {
            getFragmentManager().popBackStack();
        }
    }

    private void addDetailFragment() {
        DetailFragment fragmentDetail = (DetailFragment) getFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_TAG);
        if (fragmentDetail != null)
            addDetailFragment(DetailFragment.newInstance(fragmentDetail.getMovie()));
        else isShowingDetail = false;
    }

    private void addDetailFragment(DetailFragment fragmentDetail) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft;
        ft = fm.beginTransaction();
        if (((App) getApplication()).isDualPane()) {
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
