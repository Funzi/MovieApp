package uco_396575.movio2.pv256.fi.muni.cz.movio;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movies;
    private OnMovieClickListener mListener;

    public interface OnMovieClickListener {
        void onClick(Movie movie);

        Drawable getDrawable(int pos);
    }

    public MovieAdapter(List<Movie> movieList, OnMovieClickListener listener) {
        movies = movieList;
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
        Drawable movieImage = mListener.getDrawable(position);
        holder.movieImage.setImageDrawable(movieImage);
        holder.bottomView.setBackgroundColor(getBottomViewBackground((BitmapDrawable) movieImage));
        holder.bottomView.getBackground().setAlpha(40);
    }

    public int getBottomViewBackground(BitmapDrawable drawable) {
        Bitmap movieBitmap = drawable.getBitmap();
        Palette palette = Palette.from(movieBitmap).generate();
        return palette.getDarkVibrantColor(Color.BLACK);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public RelativeLayout bottomView;
        public TextView movieName;
        public ImageView movieImage;
        public View layout;
        private OnMovieClickListener mListener;

        public ViewHolder(View v, OnMovieClickListener listener) {
            super(v);
            layout = v;
            movieName = v.findViewById(R.id.movie_name);
            movieImage = v.findViewById(R.id.movie_image);
            bottomView = v.findViewById(R.id.movie_bottom_view);
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

