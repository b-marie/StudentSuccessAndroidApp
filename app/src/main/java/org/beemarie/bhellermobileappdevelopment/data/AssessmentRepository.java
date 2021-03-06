package org.beemarie.bhellermobileappdevelopment.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class AssessmentRepository {
    private AssessmentDao assessmentDao;
    private LiveData<List<ListItemAssessment>> allAssessments;

    public AssessmentRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        assessmentDao = db.assessmentDao();
        allAssessments = assessmentDao.getAllAssessments();
    }

    public LiveData<List<ListItemAssessment>> getAllAssessments() {
        return allAssessments;
    }

    public void insert (ListItemAssessment assessment) {
        new AssessmentRepository.insertAsyncTask(assessmentDao).execute(assessment);
    }

    private static class insertAsyncTask extends AsyncTask<ListItemAssessment, Void, Void> {

        private AssessmentDao mAsyncTaskDao;

        insertAsyncTask(AssessmentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ListItemAssessment... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void update (ListItemAssessment assessment) {
        new AssessmentRepository.updateAsyncTask(assessmentDao).execute(assessment);
    }

    private static class updateAsyncTask extends AsyncTask<ListItemAssessment, Void, Void> {

        private AssessmentDao mAsyncTaskDao;

        updateAsyncTask(AssessmentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ListItemAssessment... params) {
            mAsyncTaskDao.updateAssessment(params[0]);
            return null;
        }
    }

    public void delete (ListItemAssessment assessment) {
        new AssessmentRepository.deleteAsyncTask(assessmentDao).execute(assessment);
    }

    private static class deleteAsyncTask extends AsyncTask<ListItemAssessment, Void, Void> {

        private AssessmentDao mAsyncTaskDao;

        deleteAsyncTask(AssessmentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ListItemAssessment... params) {
            mAsyncTaskDao.deleteAssessment(params[0]);
            return null;
        }
    }
}
