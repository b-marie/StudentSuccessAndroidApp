package org.beemarie.bhellermobileappdevelopment.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class MentorRepository {
    private MentorDao mentorDao;
    private LiveData<List<ListItemMentor>> allMentors;

    public MentorRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mentorDao = db.mentorDao();
        allMentors = mentorDao.getAllMentors();
    }

    public LiveData<List<ListItemMentor>> getAllMentors() {
        return allMentors;
    }

    public void insert (ListItemMentor mentor) {
        new MentorRepository.insertAsyncTask(mentorDao).execute(mentor);
    }

    private static class insertAsyncTask extends AsyncTask<ListItemMentor, Void, Void> {

        private MentorDao mAsyncTaskDao;

        insertAsyncTask(MentorDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ListItemMentor... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
