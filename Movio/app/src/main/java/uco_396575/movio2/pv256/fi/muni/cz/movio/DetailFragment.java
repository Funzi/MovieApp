package uco_396575.movio2.pv256.fi.muni.cz.movio;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment {
    private Movie mMovie;

    public Movie getMovie() {
        return mMovie;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    public static DetailFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        DetailFragment fragment = new DetailFragment();
        args.putParcelable("movie", movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable("movie");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView movieName = view.findViewById(R.id.movie_name);
        movieName.setText(mMovie.getTitle());
        super.onViewCreated(view, savedInstanceState);
    }
}
