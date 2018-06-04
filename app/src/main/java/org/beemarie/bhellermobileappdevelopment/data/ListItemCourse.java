package org.beemarie.bhellermobileappdevelopment.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

//Model for courses
@Entity(tableName = "course_table", indices = {@Index("course_ID"), @Index("course_term_ID")},
        foreignKeys = {
                @ForeignKey(
                        entity=ListItemTerm.class,
                        parentColumns="term_ID",
                        childColumns="course_term_ID",
                        onDelete = CASCADE)})
public class ListItemCourse{

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

    @ColumnInfo(name = "course_notes")
    private String courseNotes;

    @ColumnInfo(name = "course_term_ID")
    private int courseTermID;

    @ColumnInfo(name = "course_start_notification")
    private boolean courseStartNotification;

    @ColumnInfo(name = "course_end_notification")
    private boolean courseEndNotification;

    @Ignore
    public ListItemCourse(int courseID, String courseName, Date courseStartDate, Date courseEndDate, String courseStatus, String courseNotes, int courseTermID, boolean courseStartNotification, boolean courseEndNotification) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.courseNotes = courseNotes;
        this.courseTermID = courseTermID;
        this.courseStartNotification = courseStartNotification;
        this.courseEndNotification = courseEndNotification;
    }

    public ListItemCourse(String courseName, Date courseStartDate, Date courseEndDate, String courseStatus, String courseNotes, int courseTermID, boolean courseStartNotification, boolean courseEndNotification) {
        this.courseName = courseName;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.courseNotes = courseNotes;
        this.courseTermID = courseTermID;
        this.courseStartNotification = courseStartNotification;
        this.courseEndNotification = courseEndNotification;
    }

    @Ignore
    public ListItemCourse() {}


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

    public boolean getCourseStartNotification() {return courseStartNotification;}

    public void setCourseStartNotification(boolean courseStartNotification) {this.courseStartNotification = courseStartNotification; }

    public boolean getCourseEndNotification() {return courseEndNotification;}

    public void setCourseEndNotification(boolean courseEndNotification) {this.courseEndNotification = courseEndNotification; }

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    public int getCourseTermID() {
        return courseTermID;
    }

    public void setCourseTermID(int courseTermID) {
        this.courseTermID = courseTermID;
    }

}
