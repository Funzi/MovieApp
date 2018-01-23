package uco_396575.movio2.pv256.fi.muni.cz.movio.db;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uco_396575.movio2.pv256.fi.muni.cz.movio.model.Movie;

public class MovieManager {

    private final Context mContext;

    private static final String WHERE_ID = MovieContract.MovieEntry._ID + " = ?";
    private static final String WHERE_TITLE = MovieContract.MovieEntry.COLUMN_TITLE + " = ?";

    public static final int COL_MOVIE_ID = 0;
    private int COL_MOVIE_ORIGINAL_TITLE= 1;
    private int COL_MOVIE_VOTE_AVERAGE= 2;
    private int COL_MOVIE_RELEASE_DATE= 3;
    private int COL_MOVIE_POSTER_PATH= 4;
    private int COL_MOVIE_OVERVIEW= 5;
    private int COL_MOVIE_BACKDROP_PATH = 6;

    private static final String[] MOVIE_COLUMNS = {
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_OVERVIEW,
            MovieContract.MovieEntry.COLUMN_BACKDROP_PATH
    };
    

    public MovieManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public void createMovie(Movie movie) {
        checkMovie(movie);
        movie.setId(ContentUris.parseId(mContext.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, prepareMovieValues(movie))));
    }

    private ContentValues prepareMovieValues(Movie movie) {
        checkMovie(movie);

        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getOriginalTitle());
        values.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        values.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdropPath());


        return values;
    }

    public List<Movie> getMovies() {
        Cursor cursor = mContext.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, MOVIE_COLUMNS, null,null,null);
        if (cursor != null && cursor.moveToFirst()) {
            List<Movie> movies = new ArrayList<>(cursor.getCount());
            try {
                while (!cursor.isAfterLast()) {
                    movies.add(getMovie(cursor));
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            return movies;
        }

        return Collections.emptyList();
    }

    private Movie getMovie(Cursor cursor) {
        if(cursor == null )  { return null; }
        Movie movie = new Movie();
        movie.setId(cursor.getLong(COL_MOVIE_ID));
        movie.setBackdropPath(cursor.getString(COL_MOVIE_BACKDROP_PATH));
        movie.setOverview(cursor.getString(COL_MOVIE_OVERVIEW));
        movie.setPosterPath(cursor.getString(COL_MOVIE_POSTER_PATH));
        movie.setReleaseDate(cursor.getString(COL_MOVIE_RELEASE_DATE));
        movie.setOriginalTitle(cursor.getString(COL_MOVIE_ORIGINAL_TITLE));
        movie.setVoteAverage(cursor.getLong(COL_MOVIE_VOTE_AVERAGE));

        return movie;
    }

    public Movie getMovieByTitle(String title) {
        Cursor cursor = mContext.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, MOVIE_COLUMNS, WHERE_TITLE, new String[]{title.toString()},null);
        if(cursor != null && cursor.moveToFirst()) {
            return getMovie(cursor);
        }
        return null;
    }
    public void updateMovie(Movie movie) {
        checkMovie(movie);
        mContext.getContentResolver().update(MovieContract.MovieEntry.CONTENT_URI, prepareMovieValues(movie),
                WHERE_TITLE,new String[] { movie.getOriginalTitle()});
    }
    public void addMovie(Movie movie) {
        checkMovie(movie);
        mContext.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, prepareMovieValues(movie));
    }

    private void checkMovie(Movie movie) {
        if (movie == null) {
            throw new NullPointerException("movie == null");
        }
        if (movie.getId() == null) {
            throw new IllegalStateException("movie id cannot be null");
        }
    }

    public void removeMovie(Movie movie) {
        checkMovie(movie);

        mContext.getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI, WHERE_TITLE, new String[]{String.valueOf(movie.getOriginalTitle())});
    }


    public void updateMovies(List<Movie> movies) {
        for (Movie movie:movies) {
            updateMovie(movie);
        }
    }
}
