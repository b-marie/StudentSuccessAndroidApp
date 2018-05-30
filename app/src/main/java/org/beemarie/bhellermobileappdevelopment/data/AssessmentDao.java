package org.beemarie.bhellermobileappdevelopment.data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface AssessmentDao {
    @Query("SELECT * FROM assessment_table")
    LiveData<List<ListItemAssessment>> getAllAssessments();

    @Query("SELECT * FROM assessment_table WHERE assessment_ID IN (:assessmentIds)")
    List<ListItemAssessment> loadAllAssessmentsByIds(int[] assessmentIds);

    @Query("SELECT * FROM assessment_table WHERE assessment_ID = (:assessmentId)")
    ListItemAssessment getAssessmentByID(int assessmentId);

    @Query("SELECT * FROM assessment_table WHERE assessment_name LIKE :assessment LIMIT 1")
    ListItemAssessment findAssessmentByName(String assessment);

    @Insert
    void insert(ListItemAssessment assessment);

    @Insert
    void insertAllAssessments(ListItemAssessment... assessments);

    @Delete
    void deleteAssessment(ListItemAssessment assessment);

    @Query("DELETE FROM assessment_table")
    void deleteAllAssessments();

    @Update
    void updateAssessment(ListItemAssessment... assessment);
}
