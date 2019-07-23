package com.example.moviecataloguefinal.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.moviecataloguefinal.dao.MovieDao;
import com.example.moviecataloguefinal.dao.TvShowDao;
import com.example.moviecataloguefinal.model.Movie;
import com.example.moviecataloguefinal.model.TvShow;

@android.arch.persistence.room.Database(entities = {Movie.class, TvShow.class}, version = 1, exportSchema = false)
@TypeConverters({TypeConverter.class})
public abstract class Database extends RoomDatabase {
    private static Database instance;

    public abstract MovieDao movieDao();

    public abstract TvShowDao tvShowDao();

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class, "favorite_movie_database")
                    .fallbackToDestructiveMigration().
                            addCallback(roomCallback).
                            build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private MovieDao movieDao;
        private TvShowDao tvShowDao;


        private PopulateDbAsyncTask(Database database) {
            movieDao = database.movieDao();
            tvShowDao = database.tvShowDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            ArrayList<Integer> ids = new ArrayList<>();
//            ids.add(2);
//            ids.add(4);
//            ids.add(4);
//            movieDao.insertMovie(new Movie(61, false, 6.5, "Spider-Man: Far from Home",
//                    253.781, "/2cAc4qH9Uh2NtSujJ90fIAMrw7T.jpg", "en", "Spider-Man: Far from Home",
//                    ids,"/dihW2yTsvQlust7mSuAqJDtqW7k.jpg", false, "overview", new Date()));
            return null;
        }
    }
}
