package uco_396575.movio2.pv256.fi.muni.cz.movio;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import uco_396575.movio2.pv256.fi.muni.cz.movio.api.MovieClient;
import uco_396575.movio2.pv256.fi.muni.cz.movio.api.MovieClientOkHttpImpl;

public class MainFragment extends Fragment implements MovieAdapter.OnMovieClickListener, DownloadMovieAsyncTask.OnSuccessfulDownload {

    private RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;
    private MovieClient mClient;
    private List<Movie> movies;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movies = new ArrayList<>();
        mClient = new MovieClientOkHttpImpl();
        mClient.getMostPopular();
        new DownloadMovieAsyncTask(mClient, this).execute();
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
        return view;
    }

    @Override
    public void onClick(Movie movie) {
        ((MainActivity) getActivity()).addDetailFragmentWithMovie(movie);
    }

    @Override
    public void updateData(List<Movie> movies) {
        this.movies = movies;
        mAdapter.setMovies(movies);
        mAdapter.notifyDataSetChanged();
    }
}
