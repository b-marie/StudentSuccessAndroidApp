package org.beemarie.bhellermobileappdevelopment.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class AssessmentRepository {
    private AssessmentDao assessmentDao;
    private List<ListItemAssessment> allAssessments;

    public AssessmentRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        assessmentDao = db.assessmentDao();
        allAssessments = assessmentDao.getAllAssessments();
    }

    public List<ListItemAssessment> getAllAssessments() {
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
}
