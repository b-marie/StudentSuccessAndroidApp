package org.beemarie.bhellermobileappdevelopment.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class CourseRepository {
    private CourseDao courseDao;
    private LiveData<List<ListItemCourse>> allCourses;

    public CourseRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        courseDao = db.courseDao();
        allCourses = courseDao.getAllCourses();
    }

    public LiveData<List<ListItemCourse>> getAllCourses() {
        return allCourses;
    }

    public void insert(ListItemCourse course) {
        new insertAsyncTask(courseDao).execute(course);
    }

    private static class insertAsyncTask extends AsyncTask<ListItemCourse, Void, Void> {
        private CourseDao mAsyncCourseDao;

        insertAsyncTask(CourseDao dao) {
            mAsyncCourseDao = dao;
        }

        @Override
        protected Void doInBackground(final ListItemCourse... params) {
            mAsyncCourseDao.insert(params[0]);
            return null;
        }

    }

    public void update (ListItemCourse course) {
        new CourseRepository.updateAsyncTask(courseDao).execute(course);
    }

    private static class updateAsyncTask extends AsyncTask<ListItemCourse, Void, Void> {

        private CourseDao mAsyncTaskDao;

        updateAsyncTask(CourseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ListItemCourse... params) {
            mAsyncTaskDao.updateCourse(params[0]);
            return null;
        }
    }

    public void delete (ListItemCourse course) {
        new CourseRepository.deleteAsyncTask(courseDao).execute(course);
    }

    private static class deleteAsyncTask extends AsyncTask<ListItemCourse, Void, Void> {

        private CourseDao mAsyncTaskDao;

        deleteAsyncTask(CourseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ListItemCourse... params) {
            mAsyncTaskDao.deleteCourse(params[0]);
            return null;
        }
    }

}
