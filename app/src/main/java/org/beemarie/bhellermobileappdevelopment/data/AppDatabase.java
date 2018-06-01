package org.beemarie.bhellermobileappdevelopment.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Database(entities = {ListItemTerm.class, ListItemCourse.class, ListItemAssessment.class, ListItemMentor.class}, version = 2, exportSchema = false)
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
//            tDao.deleteAllTerms();
//            cDao.deleteAllCourses();
//            mDao.deleteAllMentors();
//            aDao.deleteAllAssessments();
//
//            ListItemTerm term1;
//            term1 = new ListItemTerm("Term One", new Date(2018, 1, 1), new Date(2018, 6, 30));
//            tDao.insert(term1);
//
//            ListItemCourse course1 = new ListItemCourse("Intro to Basketweaving", new Date(2018, 02, 01), new Date(2018, 02, 28), "In Progress", "Notes", 1);
//            cDao.insert(course1);
////
//            //Insert fake assessment
//            ListItemAssessment assessment1 = new ListItemAssessment("Pre-Assessment", "Objective Assessment", new Date(2018, 3, 15), 1);
//            aDao.insert(assessment1);
//
////            Insert fake mentor
//            ListItemMentor mentor1 = new ListItemMentor("Bob Jones", "534-234-6234", "BJones@wgu.edu", 1);
//            mDao.insert(mentor1);

            //Insert fake course



            return null;
        }
    }

//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE term_table DROP COLUMN courses_in_term" );
//            database.execSQL("ALTER TABLE course_table DROP COLUMN course_mentors, course_assessments");
//            database.execSQL("ALTER TABLE course_table ADD COLUMN course_term_ID");
//            database.execSQL("ALTER TABLE mentor_table ADD COLUMN mentor_course_ID");
//            database.execSQL("ALTER TABLE assessment_table ADD COLUMN assessment_course_ID");
//        }
//    };



}
