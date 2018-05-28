package org.beemarie.bhellermobileappdevelopment.data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM course_table")
    List<ListItemCourse> getAllCourses();

    @Query("SELECT * FROM course_table WHERE course_ID IN (:courseIds)")
    List<ListItemCourse> loadAllCoursesByIds(int[] courseIds);

    @Query("SELECT * FROM course_table WHERE course_name LIKE :course LIMIT 1")
    ListItemCourse findCourseByName(String course);

    @Insert
    void insert(ListItemCourse course);

    @Insert
    void insertAllCourses(ListItemCourse... courses);

    @Delete
    void deleteCourse(ListItemCourse course);

    @Query("DELETE FROM course_table")
    void deleteAllCourses();
}
