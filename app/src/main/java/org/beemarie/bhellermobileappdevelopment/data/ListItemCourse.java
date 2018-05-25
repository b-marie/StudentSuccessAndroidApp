package org.beemarie.bhellermobileappdevelopment.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Model for courses
@Entity(tableName = "course_table")
public class ListItemCourse {
    @PrimaryKey(autoGenerate=true)
    @NonNull
    @ColumnInfo(name = "course_ID")
    private int courseID;

    @ColumnInfo(name = "course_name")
    private String courseName;

    @ColumnInfo(name = "course_start")
    private Date courseStartDate;

    @ColumnInfo(name = "course_end")
    private Date courseEndDate;

    @ColumnInfo(name = "course_status")
    private String courseStatus;

    @ColumnInfo(name = "course_mentors")
    private List<ListItemMentor> courseMentors;

    @ColumnInfo(name = "course_assessments")
    private List<ListItemAssessment> courseAssessments;

    @ColumnInfo(name = "course_notes")
    private String courseNotes;


    public ListItemCourse(int courseID, String courseName, Date courseStartDate, Date courseEndDate, String courseStatus, List<ListItemMentor> courseMentors, List<ListItemAssessment> courseAssessments, String courseNotes) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.courseMentors = courseMentors;
        this.courseAssessments = courseAssessments;
        this.courseNotes = courseNotes;
    }


    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(Date courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public Date getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(Date courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public List<ListItemMentor> getCourseMentors() {
        return courseMentors;
    }

    public void setCourseMentors(List<ListItemMentor> courseMentors) {
        this.courseMentors = courseMentors;
    }

    public List<ListItemAssessment> getCourseAssessments() {
        return courseAssessments;
    }

    public void setCourseAssessments(List<ListItemAssessment> courseAssessments) {
        this.courseAssessments = courseAssessments;
    }

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }
}
