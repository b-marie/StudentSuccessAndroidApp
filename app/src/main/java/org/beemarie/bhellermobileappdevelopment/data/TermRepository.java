package org.beemarie.bhellermobileappdevelopment.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class TermRepository {
    private TermDao termDao;
    private List<ListItemTerm> allTerms;

    public TermRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        termDao = db.termDao();
        allTerms = termDao.getAllTerms();
    }

    public List<ListItemTerm> getAllTerms() {
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


}
