package org.beemarie.bhellermobileappdevelopment.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {ListItemTerm.class, ListItemCourse.class, ListItemAssessment.class, ListItemMentor.class}, version = 1)
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
                            .build();
                }
            }
        }

        return INSTANCE;
    }

}
