package org.beemarie.bhellermobileappdevelopment.data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MentorDao {
    @Query("SELECT * FROM mentor_table")
    LiveData<List<ListItemMentor>> getAllMentors();

    @Query("SELECT * FROM mentor_table")
    List<ListItemMentor> getAllMentorsList();

    @Query("SELECT * FROM mentor_table WHERE mentor_ID IN (:mentorIds)")
    List<ListItemMentor> loadAllMentorsByIds(int[] mentorIds);

    @Query("SELECT * FROM mentor_table WHERE mentor_ID = (:mentorId)")
    ListItemMentor getMentorByID(int mentorId);

    @Query("SELECT * FROM mentor_table WHERE mentor_name LIKE :mentor LIMIT 1")
    ListItemMentor findMentorByName(String mentor);

    @Insert
    void insert(ListItemMentor mentor);

    @Insert
    void insertAllMentors(ListItemMentor... mentors);

    @Delete
    void deleteMentor(ListItemMentor mentor);

    @Query("DELETE FROM mentor_table")
    void deleteAllMentors();

    @Query("SELECT COUNT(*) FROM mentor_table")
    int getCountOfMentors();

    @Update
    void updateMentor(ListItemMentor... mentors);


}
