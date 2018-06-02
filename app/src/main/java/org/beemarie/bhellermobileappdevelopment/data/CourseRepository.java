package org.beemarie.bhellermobileappdevelopment.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CourseRepository {
    private CourseDao courseDao;
    private LiveData<List<ListItemCourse>> allCourses;
    List<ListItemCourse> courses;
    private String courseName;
    ListItemCourse courseToReturn;
    List<ListItemCourse> coursesToReturn;
    ListItemCourse[] coursesList;

    public CourseRepository(){}

    public CourseRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        courseDao = db.courseDao();
        allCourses = courseDao.getAllCourses();
    }

    public LiveData<List<ListItemCourse>> getAllCourses() {
        return allCourses;
    }


    public ListItemCourse getCourseByID(final int ID) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                courseToReturn = courseDao.getCourseByID(ID);
            }
        });
        return courseToReturn;
    }

    public List<ListItemCourse> getCoursesByTermID(final int ID) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                coursesToReturn = courseDao.loadAllCoursesByTermID(ID);
            }
        });
        return coursesToReturn;
    }

    public List<ListItemCourse> getAllCoursesList()
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                courses = courseDao.getAllCoursesArrayList();
            }
        });
        return courses;
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

    public List<ListItemCourse> getAllCoursesAsList () {
        List<ListItemCourse> courses =  getAsyncTask.getCourses();
        return courses;
    }

    private static class getAsyncTask extends AsyncTask<Void, Void, List<ListItemCourse>> {

        private static CourseDao mAsyncTaskDao;
        private List<ListItemCourse> courses;
        getAsyncTask(CourseDao dao) {
            mAsyncTaskDao = dao;
        }

        public static List<ListItemCourse> getCourses() {
            return mAsyncTaskDao.getAllCoursesArrayList();
        }

        @Override
        protected List<ListItemCourse> doInBackground(Void... voids) {
            return null;
        }
    }

}
