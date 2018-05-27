package org.beemarie.bhellermobileappdevelopment.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class CourseRepository {
    private CourseDao courseDao;
    private List<ListItemCourse> allCourses;

    public CourseRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        courseDao = db.courseDao();
        allCourses = courseDao.getAllCourses();
    }

    public List<ListItemCourse> getAllCourses() {
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

}
