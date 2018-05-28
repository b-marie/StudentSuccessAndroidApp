package org.beemarie.bhellermobileappdevelopment.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

@Database(entities = {ListItemTerm.class, ListItemCourse.class, ListItemAssessment.class, ListItemMentor.class}, version = 1, exportSchema = false)
@TypeConverters({DateTypeConverter.class, ListTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract MentorDao mentorDao();
    public abstract AssessmentDao assessmentDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized(AppDatabase.class) {
                if(INSTANCE == null){
                    //Create database if it doesn't already exist
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .addCallback(sRoomDatabaseCallback).build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TermDao tDao;
        private final CourseDao cDao;
        private final MentorDao mDao;
        private final AssessmentDao aDao;

        PopulateDbAsync(AppDatabase db) {
            tDao = db.termDao();
            cDao = db.courseDao();
            mDao = db.mentorDao();
            aDao = db.assessmentDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            tDao.deleteAllTerms();
            cDao.deleteAllCourses();
            mDao.deleteAllMentors();
            aDao.deleteAllAssessments();

            //Insert fake assessment
            ListItemAssessment assessment1 = new ListItemAssessment(1, "Pre-Assessment", "Objective Assessment", new Date(2018, 3, 15));
            aDao.insert(assessment1);

            //Insert fake mentor
            ListItemMentor mentor1 = new ListItemMentor(1, "Bob Jones", "534-234-6234", "BJones@wgu.edu");
            mDao.insert(mentor1);

            //Insert fake course
            ListItemCourse course1 = new ListItemCourse(1, "Intro to Basketweaving", new Date(2018, 02, 01), new Date(2018, 02, 28), "In Progress", mDao.getAllMentors(), aDao.getAllAssessments(), "Notes");
            cDao.insert(course1);

            ListItemTerm term1;
            term1 = new ListItemTerm("Term One", new Date(2018, 1, 1), new Date(2018, 6, 30), cDao.getAllCourses());
            tDao.insert(term1);
            return null;
        }
    }

}
