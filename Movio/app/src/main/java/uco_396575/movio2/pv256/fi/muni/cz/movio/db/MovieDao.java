package uco_396575.movio2.pv256.fi.muni.cz.movio.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import uco_396575.movio2.pv256.fi.muni.cz.movio.model.Movie;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface MovieDao {

    @Insert(onConflict = IGNORE)
    void insertMovie(Movie user);

    @Delete
    void deleteMovie(Movie user);

    @Query("select * from movie where id = :id")
    Movie findMovieById(int id);
}
