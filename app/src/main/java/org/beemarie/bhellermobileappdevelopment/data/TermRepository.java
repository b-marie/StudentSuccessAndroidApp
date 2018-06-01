package org.beemarie.bhellermobileappdevelopment.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class TermRepository {
    private TermDao termDao;
    private LiveData<List<ListItemTerm>> allTerms;

    public TermRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        termDao = db.termDao();
        allTerms = termDao.getAllTerms();
    }

    public LiveData<List<ListItemTerm>> getAllTerms() {
        return allTerms;
    }

    public void insert (ListItemTerm term) {
        new insertAsyncTask(termDao).execute(term);
    }

    private static class insertAsyncTask extends AsyncTask<ListItemTerm, Void, Void> {

        private TermDao mAsyncTaskDao;

        insertAsyncTask(TermDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ListItemTerm... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }

    }

    public void update (ListItemTerm term) {
        new TermRepository.updateAsyncTask(termDao).execute(term);
    }

    private static class updateAsyncTask extends AsyncTask<ListItemTerm, Void, Void> {

        private TermDao mAsyncTaskDao;

        updateAsyncTask(TermDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ListItemTerm... params) {
            mAsyncTaskDao.updateTerm(params[0]);
            return null;
        }
    }

    public void delete (ListItemTerm term) {
        new TermRepository.deleteAsyncTask(termDao).execute(term);
    }

    private static class deleteAsyncTask extends AsyncTask<ListItemTerm, Void, Void> {

        private TermDao mAsyncTaskDao;

        deleteAsyncTask(TermDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ListItemTerm... params) {
            mAsyncTaskDao.deleteTerm(params[0]);
            return null;
        }
    }


}
