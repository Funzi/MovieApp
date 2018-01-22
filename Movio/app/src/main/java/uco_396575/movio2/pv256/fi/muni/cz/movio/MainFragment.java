package uco_396575.movio2.pv256.fi.muni.cz.movio;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uco_396575.movio2.pv256.fi.muni.cz.movio.adapter.MovieAdapter;
import uco_396575.movio2.pv256.fi.muni.cz.movio.api.DownloadMovieAsyncTask;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.Movie;

public class MainFragment extends Fragment implements MovieAdapter.OnMovieClickListener, DownloadMovieAsyncTask.OnSuccessfulDownload {

    private static final String MOVIES_TAG = "movies";

    private RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;
    private List<Movie> movies;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static MainFragment newInstance() {
        return MainFragment.newInstance(new ArrayList());
    }

    public static MainFragment newInstance(List<Movie> movies) {
        Bundle args = new Bundle();
        args.putSerializable(MOVIES_TAG, (ArrayList) movies);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movies = (ArrayList<Movie>) getArguments().getSerializable(MOVIES_TAG);
        if(movies == null) movies = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = view.findViewById(R.id.movie_list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MovieAdapter(movies, this);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        initSwipeLayout();
        return view;
    }

    private void initSwipeLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            // Refresh items
            refreshItems();
        });


    }
    void refreshItems() {
        // Load items
        // ...

        // Load complete
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void onClick(Movie movie) {
        ((MainActivity) getActivity()).addDetailFragmentWithMovie(movie);
    }

    @Override
    public void updateData(List<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        mAdapter.setMovies(movies);
        mAdapter.notifyDataSetChanged();
    }
}
