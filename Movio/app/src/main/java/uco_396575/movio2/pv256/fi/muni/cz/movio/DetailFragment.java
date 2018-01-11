package uco_396575.movio2.pv256.fi.muni.cz.movio;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uco_396575.movio2.pv256.fi.muni.cz.movio.adapter.CastAdapter;
import uco_396575.movio2.pv256.fi.muni.cz.movio.api.ApiHelper;
import uco_396575.movio2.pv256.fi.muni.cz.movio.api.DownloadCastAsyncTask;
import uco_396575.movio2.pv256.fi.muni.cz.movio.api.MovieClientRetrofitImpl;
import uco_396575.movio2.pv256.fi.muni.cz.movio.db.AppDatabase;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.Cast;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.Movie;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.MoviePersonnel;

public class DetailFragment extends Fragment implements DownloadCastAsyncTask.OnSuccessfulCastDownload {
    private final static String MOVIE_TAG = "movie";
    private Movie mMovie;
    private MovieViewHolder mViewHolder;
    private CastAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<Cast> cast;
    private AppDatabase mDb;


    public Movie getMovie() {
        return mMovie;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        mRecyclerView = view.findViewById(R.id.movie_cast_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        cast = new ArrayList<>();
        mAdapter = new CastAdapter(cast);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    public static DetailFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        DetailFragment fragment = new DetailFragment();
        args.putParcelable(MOVIE_TAG, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(MOVIE_TAG);
            new DownloadCastAsyncTask(new MovieClientRetrofitImpl(), this, mMovie.getId()).execute();
        }
        mDb = AppDatabase.getInMemoryDatabase(getActivity().getApplicationContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initMovie(view);
        super.onViewCreated(view, savedInstanceState);
    }

    void initMovie(View view) {
        mViewHolder = new MovieViewHolder(view);
        mViewHolder.name.setText(mMovie.getTitle());
        mViewHolder.description.setText(mMovie.getOverview());
        //mViewHolder.director.setText(mMovie);
        mViewHolder.releaseDate.setText(mMovie.getReleaseDate());
        String posterAddress = ApiHelper.getPictureAddress(mMovie.getPosterPath());
        Picasso.with(mViewHolder.poster.getContext())
                .load(posterAddress)
                .placeholder(R.drawable.star_wars)
                .into(mViewHolder.poster);
        String coverAddress = ApiHelper.getPictureAddressHigherQuality(mMovie.getBackdropPath());
        Picasso.with(mViewHolder.poster.getContext())
                .load(coverAddress)
                .noPlaceholder()
                .fit()
                .into(mViewHolder.cover);
        if(isMovieInDb()) {
            setFabPlus();
        } else {
            setFabMinus();
        }
        mViewHolder.fab.setOnClickListener(view1 -> {
            if(!isMovieInDb()) {
                addToDb();
            } else {
                removeFromDb();
            }
        });

    }

    private boolean isMovieInDb() {
        return mDb.movieModel().findMovieById(mMovie.getId()) != null;
    }

    private void addToDb() {
        mDb.movieModel().insertMovie(mMovie);
        setFabPlus();
    }

    private void removeFromDb() {
        mDb.movieModel().deleteMovie(mMovie);
        setFabMinus();
    }

    private void setFabPlus() {
        mViewHolder.fab.setImageResource(R.drawable.ic_remove_circle_black_24dp);
    }

    private void setFabMinus() {
        mViewHolder.fab.setImageResource(R.drawable.ic_add_circle_black_24dp);
    }

    @Override
    public void updateData(MoviePersonnel personnel) {
        mViewHolder.director.setText(personnel.getDirector().getName());
        this.cast = personnel.getCast();
        mAdapter.setCast(cast);
        mAdapter.notifyDataSetChanged();
    }

    static class MovieViewHolder {
        @BindView(R.id.movie_poster_image)
        ImageView poster;
        @BindView(R.id.movie_cover_image)
        ImageView cover;
        @BindView(R.id.movie_cast_list)
        RecyclerView actors;
        @BindView(R.id.movie_name)
        TextView name;
        @BindView(R.id.movie_release_date)
        TextView releaseDate;
        @BindView(R.id.movie_director_name)
        TextView director;
        @BindView(R.id.movie_description)
        TextView description;
        @BindView(R.id.movie_fab)
        FloatingActionButton fab;

        public MovieViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
