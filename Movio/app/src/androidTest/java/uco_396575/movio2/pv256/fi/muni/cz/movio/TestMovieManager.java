package uco_396575.movio2.pv256.fi.muni.cz.movio;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.List;

import uco_396575.movio2.pv256.fi.muni.cz.movio.db.MovieContract;
import uco_396575.movio2.pv256.fi.muni.cz.movio.db.MovieManager;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.Movie;

public class TestMovieManager extends AndroidTestCase{

    private static final String TAG = TestMovieManager.class.getSimpleName();

    private MovieManager mManager;
    private Movie movie;
    private Movie movie2;

    @Override
    protected void setUp() throws Exception {
        mManager = new MovieManager(mContext);
        movie = new Movie();
        movie.setTitle("title");
        movie.setOriginalTitle("title");
        movie.setId(1L);

        movie2 = new Movie();
        movie2.setTitle("title2");
        movie2.setOriginalTitle("title2");
        movie2.setId(2L);
    }

    @Override
    public void tearDown() throws Exception {
        mContext.getContentResolver().delete(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null
        );
    }

    public void testAddMovie() throws Exception {
        mManager.addMovie(movie);
        List<Movie> movies2 = mManager.getMovies();
        Log.d(TAG, movies2.toString());
        assertTrue(movies2.size() == 1);
    }

    public void testAdd2Movies() throws Exception {
        mManager.addMovie(movie);
        mManager.addMovie(movie2);
        List<Movie> movies2 = mManager.getMovies();
        Log.d(TAG, movies2.toString());
        assertTrue(movies2.size() == 2);
    }

    public void testDeleteMovie() throws Exception {
        mManager.addMovie(movie);
        mManager.removeMovie(movie);
        List<Movie> movies2 = mManager.getMovies();
        Log.d(TAG, movies2.toString());
        assertTrue(movies2.size() == 0);
    }

    public void testFindMovie() throws Exception {
        mManager.addMovie(movie);
        Movie testMovie = mManager.getMovieByTitle("title");
        assertTrue(testMovie.getOriginalTitle().equals(movie.getOriginalTitle()));
    }
}
