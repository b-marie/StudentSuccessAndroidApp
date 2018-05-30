package org.beemarie.bhellermobileappdevelopment.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
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

    public void update (ListItemMentor mentor) {
        new MentorRepository.updateAsyncTask(mentorDao).execute(mentor);
    }

    private static class updateAsyncTask extends AsyncTask<ListItemMentor, Void, Void> {

        private MentorDao mAsyncTaskDao;

        updateAsyncTask(MentorDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ListItemMentor... params) {
            mAsyncTaskDao.updateMentor(params[0]);
            return null;
        }
    }

    public void delete (ListItemMentor mentor) {
        new MentorRepository.deleteAsyncTask(mentorDao).execute(mentor);
    }

    private static class deleteAsyncTask extends AsyncTask<ListItemMentor, Void, Void> {

        private MentorDao mAsyncTaskDao;

        deleteAsyncTask(MentorDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ListItemMentor... params) {
            mAsyncTaskDao.deleteMentor(params[0]);
            return null;
        }
    }


}
