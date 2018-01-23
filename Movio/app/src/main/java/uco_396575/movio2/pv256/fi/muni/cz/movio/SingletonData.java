package uco_396575.movio2.pv256.fi.muni.cz.movio;

import java.util.ArrayList;
import java.util.List;

import uco_396575.movio2.pv256.fi.muni.cz.movio.model.Movie;

public class SingletonData {

    private List<Movie> movies = new ArrayList<>();

    private static SingletonData INSTANCE;

    private SingletonData() {}

    public static SingletonData getInstance() {
        if(INSTANCE == null) {
            return new SingletonData();
        } else {
            return INSTANCE;
        }
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
