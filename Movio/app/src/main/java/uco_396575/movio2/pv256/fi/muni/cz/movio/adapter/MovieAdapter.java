package uco_396575.movio2.pv256.fi.muni.cz.movio.adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uco_396575.movio2.pv256.fi.muni.cz.movio.R;
import uco_396575.movio2.pv256.fi.muni.cz.movio.api.ApiHelper;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movies;
    private OnMovieClickListener mListener;

    public interface OnMovieClickListener {
        void onClick(Movie movie);
    }

    public MovieAdapter(List<Movie> movies, OnMovieClickListener listener) {
        this.movies = movies;
        mListener = listener;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.item_movie, parent, false);
        ViewHolder vh = new ViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Movie movie = movies.get(position);
        holder.movieName.setText(movie.getTitle());

        holder.movieRating.setText(Float.toString(movie.getVoteAverage()));
        String address = ApiHelper.getPictureAddressHigherQuality(movies.get(position).getPosterPath());
        Picasso.with(holder.movieImage.getContext())
                .load(address)
                .noPlaceholder()
                //.placeholder(R.drawable.star_wars)
                .into(holder.movieImage)
        ;

        holder.bottomView.setBackgroundColor(getBottomViewBackground(holder.movieImage));
        holder.bottomView.getBackground().setAlpha(40);
    }

    public int getBottomViewBackground(ImageView drawable) {
        if (drawable.getDrawable() == null) return Color.BLACK;
        Bitmap bitmap = ((BitmapDrawable) drawable.getDrawable()).getBitmap();
        Palette palette = Palette.from(bitmap).generate();
        return palette.getDarkVibrantColor(Color.BLACK);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.movie_bottom_view)
        public RelativeLayout bottomView;
        @BindView(R.id.movie_name)
        public TextView movieName;
        @BindView(R.id.movie_rating)
        public TextView movieRating;
        @BindView(R.id.movie_image)
        public ImageView movieImage;
        private OnMovieClickListener mListener;

        public ViewHolder(View v, OnMovieClickListener listener) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
            mListener = listener;
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(movies.get(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View view) {
            bottomView.setVisibility(bottomView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            return true;
        }
    }
}

