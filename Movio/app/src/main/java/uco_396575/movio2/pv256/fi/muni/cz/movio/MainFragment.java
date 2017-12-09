package uco_396575.movio2.pv256.fi.muni.cz.movio;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements MovieAdapter.OnMovieClickListener {

    private RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = view.findViewById(R.id.movie_list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MovieAdapter(getMovies(), this);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @NonNull
    private List<Movie> getMovies() {
        List<Movie> input = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            input.add(new Movie("Movie " + i));
        }
        return input;
    }

    @Override
    public void onClick(Movie movie) {
        ((MainActivity) getActivity()).addDetailFragmentWithMovie(movie);
    }
}
