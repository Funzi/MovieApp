package uco_396575.movio2.pv256.fi.muni.cz.movio;

import android.content.Context;
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

import com.squareup.picasso.Picasso;

import java.util.List;

import uco_396575.movio2.pv256.fi.muni.cz.movio.api.MovieClientOkHttpImpl;
import uco_396575.movio2.pv256.fi.muni.cz.movio.api.MovieDto;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<MovieDto> movies;
    private OnMovieClickListener mListener;
    private Context mContext;

    public interface OnMovieClickListener {
        void onClick(MovieDto movieDto);

        Drawable getDrawable(int pos);
    }

    public MovieAdapter(List<MovieDto> movies, OnMovieClickListener listener, Context context) {
        this.movies = movies;
        mListener = listener;
        mContext = context;
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
        final MovieDto movie = movies.get(position);
        holder.movieName.setText(movie.getTitle());
        String address = new MovieClientOkHttpImpl().getPicture(movies.get(position).getPosterPath());
        Picasso.with(mContext)
                .load(address)
                .placeholder(R.id.movie_image)
                .into(holder.movieImage);
        holder.bottomView.setBackgroundColor(getBottomViewBackground(holder.movieImage));
        holder.bottomView.getBackground().setAlpha(40);
    }

    public int getBottomViewBackground(ImageView drawable) {
        if(drawable.getDrawable() == null) return Color.BLACK;
        Bitmap bitmap = ((BitmapDrawable)drawable.getDrawable()).getBitmap();
        Palette palette = Palette.from(bitmap).generate();
        return palette.getDarkVibrantColor(Color.BLACK);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<MovieDto> movies) {
        this.movies = movies;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public RelativeLayout bottomView;
        public TextView movieName;
        public ImageView movieImage;
        private OnMovieClickListener mListener;

        public ViewHolder(View v, OnMovieClickListener listener) {
            super(v);
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

