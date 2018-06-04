package org.beemarie.bhellermobileappdevelopment.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

//Model for Assessments
@Entity(tableName = "assessment_table", indices = {@Index("assessment_ID"), @Index("assessment_course_ID")},
        foreignKeys = {
        @ForeignKey(
                entity=ListItemCourse.class,
                parentColumns="course_ID",
                childColumns="assessment_course_ID",
                onDelete = CASCADE)})
public class ListItemAssessment{

    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "assessment_ID")
    private int assessmentID;

    @ColumnInfo(name = "assessment_name")
    private String assessmentName;

    @ColumnInfo(name = "assessment_type")
    private String assessmentType;

    @ColumnInfo(name = "assessment_due_date")
    private Date assessmentDueDate;

    @ColumnInfo(name = "assessment_course_ID")
    private int assessmentCourseID;

    @ColumnInfo(name = "assessment_due_date_notification")
    private boolean assessmentDueDateNotification;

    @Ignore
    public ListItemAssessment(int assessmentID, String assessmentName, String assessmentType, Date assessmentDueDate, int assessmentCourseID, boolean assessmentDueDateNotification) {
        this.assessmentID = assessmentID;
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.assessmentDueDate = assessmentDueDate;
        this.assessmentCourseID = assessmentCourseID;
        this.assessmentDueDateNotification = assessmentDueDateNotification;
    }

    public ListItemAssessment(String assessmentName, String assessmentType, Date assessmentDueDate, int assessmentCourseID, boolean assessmentDueDateNotification) {
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.assessmentDueDate = assessmentDueDate;
        this.assessmentCourseID = assessmentCourseID;
        this.assessmentDueDateNotification = assessmentDueDateNotification;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public Date getAssessmentDueDate() {
        return assessmentDueDate;
    }

    public void setAssessmentDueDate(Date assessmentDueDate) {
        this.assessmentDueDate = assessmentDueDate;
    }

    public int getAssessmentCourseID() {return assessmentCourseID;}

    public void setAssessmentCourseID(int assessmentCourseID) {this.assessmentCourseID = assessmentCourseID;}

    public boolean getAssessmentDueDateNotification() {
        return assessmentDueDateNotification;
    }

    public void setAssessmentDueDateNotification(boolean assessmentDueDateNotification) {
        this.assessmentDueDateNotification = assessmentDueDateNotification;
    }

    //
//    protected ListItemAssessment(Parcel in) {
//        assessmentID = in.readInt();
//        assessmentName = in.readString();
//        assessmentType = in.readString();
//        long tmpAssessmentDueDate = in.readLong();
//        assessmentDueDate = tmpAssessmentDueDate != -1 ? new Date(tmpAssessmentDueDate) : null;
//        assessmentCourseID = in.readInt();
//    }

}
