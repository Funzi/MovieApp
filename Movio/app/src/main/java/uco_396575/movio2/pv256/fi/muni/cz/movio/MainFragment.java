package uco_396575.movio2.pv256.fi.muni.cz.movio;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uco_396575.movio2.pv256.fi.muni.cz.movio.api.DownloadAsyncTask;
import uco_396575.movio2.pv256.fi.muni.cz.movio.api.MovieClient;
import uco_396575.movio2.pv256.fi.muni.cz.movio.api.MovieClientOkHttpImpl;
import uco_396575.movio2.pv256.fi.muni.cz.movio.api.MovieDto;

public class MainFragment extends Fragment implements MovieAdapter.OnMovieClickListener, DownloadAsyncTask.OnSuccesfullDownload {

    private RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;
    private MovieClient mClient;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClient = new MovieClientOkHttpImpl();
        mClient.getAllMovies();
        new DownloadAsyncTask(mClient, getActivity(), this).execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = view.findViewById(R.id.movie_list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MovieAdapter(new ArrayList<>(), this, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onClick(MovieDto movieDto) {
        ((MainActivity) getActivity()).addDetailFragmentWithMovie(movieDto);
    }

    //TODO change after getting real data
    @Override
    public Drawable getDrawable(int pos) {
        if(pos % 2 == 0) return ContextCompat.getDrawable(getActivity(), R.drawable.star_wars);
        else return ContextCompat.getDrawable(getActivity(), R.drawable.it);
    }

    @Override
    public void updateData(List<MovieDto> movies) {
        mAdapter.setMovies(movies);
        mAdapter.notifyDataSetChanged();
    }
}
