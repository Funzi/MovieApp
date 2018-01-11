package uco_396575.movio2.pv256.fi.muni.cz.movio.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import uco_396575.movio2.pv256.fi.muni.cz.movio.model.Cast;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.Movie;


@Database(entities = {Movie.class, Cast.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    private static final String DATABASE_NAME = "myDb";

    public abstract MovieDao movieModel();

    public abstract CrewDao crewModel();


    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            // To simplify the codelab, allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
