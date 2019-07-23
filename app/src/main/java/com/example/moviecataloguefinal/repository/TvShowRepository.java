package com.example.moviecataloguefinal.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.moviecataloguefinal.dao.TvShowDao;
import com.example.moviecataloguefinal.database.Database;
import com.example.moviecataloguefinal.helper.MappingHelper;
import com.example.moviecataloguefinal.model.TvShow;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TvShowRepository {
    private TvShowDao tvShowDao;
    private LiveData<List<TvShow>> liveDataAllTvShow;
    private Context context;

    public TvShowRepository(Context context) {
        Database database = Database.getInstance(context);
        tvShowDao = database.tvShowDao();
        liveDataAllTvShow = tvShowDao.getLiveDataAllTvShow();
        this.context = context;
    }

    public long insertTvShow(TvShow tvShow) {
        try {
            return new InsertTvShowAsyncTask(tvShowDao).execute(tvShow).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void insertListTvShow(List<TvShow> listTvShow) {
        new InsertListTvShowAsyncTask(tvShowDao).execute(listTvShow);
    }

    public LiveData<List<TvShow>> getLiveDataAllTvShow() {
        return liveDataAllTvShow;
    }

    public List<TvShow> getListAllTvShow() {
        try {
            return new GetListTvShowAsyncTask(tvShowDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cursor getCursorAllTvShow() {
        return tvShowDao.getCursorAllTvShow();
    }

    public Cursor getCursorTvShowById(long id) {
        try {
            return new GetCursorTvShowByIdAsyncTask(tvShowDao).execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TvShow getTvShowById(long id) {
        Cursor cursorTvShow = getCursorTvShowById(id);
        if (cursorTvShow.getCount() <= 0) {
            return null;
        }
        return MappingHelper.mapCursorToTvShow(cursorTvShow);
    }

    public int updateTvShow(TvShow tvShow) {
        try {
            return new UpdateTvShowAsyncTask(tvShowDao).execute(tvShow).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteTvShow(TvShow tvShow) {
        try {
            return new DeleteTvShowAsyncTask(tvShowDao).execute(tvShow).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteAllTvShow() {
        try {
            return new DeleteAllTvShowAsyncTask(tvShowDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static class InsertTvShowAsyncTask extends AsyncTask<TvShow, Void, Long> {
        private TvShowDao tvShowDao;

        private InsertTvShowAsyncTask(TvShowDao tvShowDao) {
            this.tvShowDao = tvShowDao;
        }

        @Override
        protected Long doInBackground(TvShow... tvShows) {
            tvShowDao.insertTvShow(tvShows[0]);
            return tvShows[0].getId();
        }
    }

    private static class InsertListTvShowAsyncTask extends AsyncTask<List<TvShow>, Void, Void> {
        private TvShowDao tvShowDao;

        private InsertListTvShowAsyncTask(TvShowDao tvShowDao) {
            this.tvShowDao = tvShowDao;
        }

        @Override
        protected Void doInBackground(List<TvShow>... lists) {
            tvShowDao.insertListTvShow(lists[0]);
            return null;
        }
    }

    private static class GetListTvShowAsyncTask extends AsyncTask<Void, Void, List<TvShow>> {
        private TvShowDao tvShowDao;

        private GetListTvShowAsyncTask(TvShowDao tvShowDao) {
            this.tvShowDao = tvShowDao;
        }

        @Override
        protected List<TvShow> doInBackground(Void... voids) {
            return tvShowDao.getListAllTvShow();
        }
    }

    private static class GetCursorTvShowByIdAsyncTask extends AsyncTask<Long, Void, Cursor> {
        private TvShowDao tvShowDao;

        private GetCursorTvShowByIdAsyncTask(TvShowDao tvShowDao) {
            this.tvShowDao = tvShowDao;
        }

        @Override
        protected Cursor doInBackground(Long... longs) {
            return tvShowDao.getCursorTvShowById(longs[0]);
        }
    }

    private static class UpdateTvShowAsyncTask extends AsyncTask<TvShow, Void, Integer> {
        private TvShowDao tvShowDao;

        private UpdateTvShowAsyncTask(TvShowDao tvShowDao) {
            this.tvShowDao = tvShowDao;
        }

        @Override
        protected Integer doInBackground(TvShow... tvShows) {
            tvShowDao.updateTvShow(tvShows[0]);
            return (int) tvShows[0].getId();
        }
    }

    private static class DeleteTvShowAsyncTask extends AsyncTask<TvShow, Void, Integer> {
        private TvShowDao tvShowDao;

        private DeleteTvShowAsyncTask(TvShowDao tvShowDao) {
            this.tvShowDao = tvShowDao;
        }

        @Override
        protected Integer doInBackground(TvShow... tvShows) {
            tvShowDao.deleteTvShow(tvShows[0]);
            return (int) tvShows[0].getId();
        }
    }

    private static class DeleteAllTvShowAsyncTask extends AsyncTask<Void, Void, Integer> {
        private TvShowDao tvShowDao;

        private DeleteAllTvShowAsyncTask(TvShowDao tvShowDao) {
            this.tvShowDao = tvShowDao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            int tvShowCount = tvShowDao.countAllTvShow();
            tvShowDao.deleteAllTvShow();
            return tvShowCount;
        }
    }
}
