package uco_396575.movio2.pv256.fi.muni.cz.movio;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uco_396575.movio2.pv256.fi.muni.cz.movio.api.MovieDto;

public class DetailFragment extends Fragment {
    private final static String MOVIE_TAG = "movie";
    private MovieDto mMovieDto;

    public MovieDto getMovie() {
        return mMovieDto;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    public static DetailFragment newInstance(MovieDto movieDto) {
        Bundle args = new Bundle();
        DetailFragment fragment = new DetailFragment();
        args.putParcelable(MOVIE_TAG, movieDto);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mMovieDto = getArguments().getParcelable(MOVIE_TAG);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView movieName = view.findViewById(R.id.movie_name);
        movieName.setText(mMovieDto.getTitle());
        super.onViewCreated(view, savedInstanceState);
    }
}
