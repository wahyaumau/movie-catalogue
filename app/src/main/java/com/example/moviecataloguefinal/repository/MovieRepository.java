package com.example.moviecataloguefinal.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.moviecataloguefinal.dao.MovieDao;
import com.example.moviecataloguefinal.database.Database;
import com.example.moviecataloguefinal.helper.MappingHelper;
import com.example.moviecataloguefinal.model.Movie;
import com.example.moviecataloguefinal.widget.FavoriteMovieWidget;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieRepository {
    private MovieDao movieDao;
    private LiveData<List<Movie>> liveDataAllMovie;
    private Context context;

    public MovieRepository(Context context) {
        Database database = Database.getInstance(context);
        movieDao = database.movieDao();
        liveDataAllMovie = movieDao.getLiveDataAllMovie();
        this.context = context;
    }

    public long insertMovie(Movie movie) {
        try {
            long addedId = new InsertMovieAsyncTask(movieDao).execute(movie).get();
            FavoriteMovieWidget.updateWidget(context);
            return addedId;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void insertListMovie(List<Movie> listMovie) {
        new InsertListMovieAsyncTask(movieDao).execute(listMovie);
        FavoriteMovieWidget.updateWidget(context);
    }

    public LiveData<List<Movie>> getLiveDataAllMovie() {
        return liveDataAllMovie;
    }

    public List<Movie> getListAllMovie() {
        try {
            return new GetListAllMovieAsyncTask(movieDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cursor getCursorAllMovie() {
        return movieDao.getCursorAllMovie();
    }

    public Cursor getCursorMovieById(long id) {
        try {
            return new GetCursorMovieByIdAsyncTask(movieDao).execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Movie getMovieById(long id) {
        Cursor cursorMovie = getCursorMovieById(id);
        if (cursorMovie.getCount() <= 0) {
            return null;
        }
        return MappingHelper.mapCursorToMovie(cursorMovie);
    }

    public int updateMovie(Movie movie) {
        try {
            int updated = new UpdateMovieAsyncTask(movieDao).execute(movie).get();
            FavoriteMovieWidget.updateWidget(context);
            return updated;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteMovie(Movie movie) {
        try {
            int deleted = new DeleteMovieAsyncTask(movieDao).execute(movie).get();
            FavoriteMovieWidget.updateWidget(context);
            return deleted;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteAllMovie() {
        try {
            int deleted = new DeleteAllMovieAsyncTask(movieDao).execute().get();
            FavoriteMovieWidget.updateWidget(context);
            return deleted;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static class InsertMovieAsyncTask extends AsyncTask<Movie, Void, Long> {
        private MovieDao movieDao;

        private InsertMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Long doInBackground(Movie... movies) {
            movieDao.insertMovie(movies[0]);
            return movies[0].getId();
        }
    }

    private static class InsertListMovieAsyncTask extends AsyncTask<List<Movie>, Void, Void> {
        private MovieDao movieDao;

        public InsertListMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(List<Movie>... lists) {
            movieDao.insertListMovie(lists[0]);
            return null;
        }
    }

    private static class GetListAllMovieAsyncTask extends AsyncTask<Void, Void, List<Movie>> {
        private MovieDao movieDao;

        public GetListAllMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            return movieDao.getListAllMovie();
        }
    }

    private static class GetCursorMovieByIdAsyncTask extends AsyncTask<Long, Void, Cursor> {
        private MovieDao movieDao;

        private GetCursorMovieByIdAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Cursor doInBackground(Long... longs) {
            return movieDao.getCursorMovieById(longs[0]);
        }
    }

    private static class UpdateMovieAsyncTask extends AsyncTask<Movie, Void, Integer> {
        private MovieDao movieDao;

        private UpdateMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Integer doInBackground(Movie... movies) {
            movieDao.updateMovie(movies[0]);
            return (int) movies[0].getId();
        }
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<Movie, Void, Integer> {
        private MovieDao movieDao;

        private DeleteMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Integer doInBackground(Movie... movies) {
            movieDao.deleteMovie(movies[0]);
            return (int) movies[0].getId();
        }
    }

    private static class DeleteAllMovieAsyncTask extends AsyncTask<Void, Void, Integer> {
        private MovieDao movieDao;

        private DeleteAllMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            int allMovie = movieDao.countAllMovie();
            movieDao.deleteAllMovie();
            return allMovie;
        }
    }
}
