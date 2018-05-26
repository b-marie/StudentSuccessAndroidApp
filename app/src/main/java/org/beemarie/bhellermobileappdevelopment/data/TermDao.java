package org.beemarie.bhellermobileappdevelopment.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TermDao {
    @Query("SELECT * FROM term_table")
    LiveData<List<ListItemTerm>> getAllTerms();

    @Query("SELECT * FROM term_table WHERE term_ID IN (:termIds)")
    List<ListItemTerm> loadAllTermsByIds(int[] termIds);

    @Query("SELECT * FROM term_table WHERE term_name LIKE :term LIMIT 1")
    ListItemTerm findTermByName(String term);

    @Insert
    void insert(ListItemTerm term);

    @Insert
    void insertAllTerms(ListItemTerm... terms);

    @Delete
    void deleteTerm(ListItemTerm term);


}
