package org.beemarie.bhellermobileappdevelopment.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Model for Terms
@Entity(tableName = "term_table")
public class ListItemTerm {
    @PrimaryKey(autoGenerate=true)
    @NonNull
    @ColumnInfo(name = "term_ID")
    private int termID;

    @ColumnInfo(name = "term_name")
    private String termName;

    @ColumnInfo(name = "term_start")
    private Date termStartDate;

    @ColumnInfo(name = "term_end")
    private Date termEndDate;

    @ColumnInfo(name = "course_in_term")
    private List<ListItemCourse> coursesInTerm;

    public ListItemTerm(int termID, String termName, Date termStartDate, Date termEndDate, List<ListItemCourse> coursesInTerm) {
        this.termID = termID;
        this.termName = termName;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
        this.coursesInTerm = coursesInTerm;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Date getTermStartDate() {
        return termStartDate;
    }

    public void setTermStartDate(Date termStartDate) {
        this.termStartDate = termStartDate;
    }

    public Date getTermEndDate() {
        return termEndDate;
    }

    public void setTermEndDate(Date termEndDate) {
        this.termEndDate = termEndDate;
    }

    public List<ListItemCourse> getCoursesInTerm() {
        return coursesInTerm;
    }

    public void setCoursesInTerm(List<ListItemCourse> coursesInTerm) {
        this.coursesInTerm = coursesInTerm;
    }
}
