package org.beemarie.bhellermobileappdevelopment.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Model for courses
@Entity(tableName = "course_table")
public class ListItemCourse implements Parcelable{
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
    private ArrayList<ListItemMentor> courseMentors;

    @ColumnInfo(name = "course_assessments")
    private ArrayList<ListItemAssessment> courseAssessments;

    @ColumnInfo(name = "course_notes")
    private String courseNotes;

    @Ignore
    public ListItemCourse(int courseID, String courseName, Date courseStartDate, Date courseEndDate, String courseStatus, ArrayList<ListItemMentor> courseMentors, ArrayList<ListItemAssessment> courseAssessments, String courseNotes) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.courseMentors = courseMentors;
        this.courseAssessments = courseAssessments;
        this.courseNotes = courseNotes;
    }

    public ListItemCourse(String courseName, Date courseStartDate, Date courseEndDate, String courseStatus, ArrayList<ListItemMentor> courseMentors, ArrayList<ListItemAssessment> courseAssessments, String courseNotes) {
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

    public ArrayList<ListItemMentor> getCourseMentors() {
        return courseMentors;
    }

    public void setCourseMentors(ArrayList<ListItemMentor> courseMentors) {
        this.courseMentors = courseMentors;
    }

    public ArrayList<ListItemAssessment> getCourseAssessments() {
        return courseAssessments;
    }

    public void setCourseAssessments(ArrayList<ListItemAssessment> courseAssessments) {
        this.courseAssessments = courseAssessments;
    }

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    protected ListItemCourse(Parcel in) {
        courseID = in.readInt();
        courseName = in.readString();
        long tmpCourseStartDate = in.readLong();
        courseStartDate = tmpCourseStartDate != -1 ? new Date(tmpCourseStartDate) : null;
        long tmpCourseEndDate = in.readLong();
        courseEndDate = tmpCourseEndDate != -1 ? new Date(tmpCourseEndDate) : null;
        courseStatus = in.readString();
        if (in.readByte() == 0x01) {
            courseMentors = new ArrayList<ListItemMentor>();
            in.readList(courseMentors, ListItemMentor.class.getClassLoader());
        } else {
            courseMentors = null;
        }
        if (in.readByte() == 0x01) {
            courseAssessments = new ArrayList<ListItemAssessment>();
            in.readList(courseAssessments, ListItemAssessment.class.getClassLoader());
        } else {
            courseAssessments = null;
        }
        courseNotes = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(courseID);
        dest.writeString(courseName);
        dest.writeLong(courseStartDate != null ? courseStartDate.getTime() : -1L);
        dest.writeLong(courseEndDate != null ? courseEndDate.getTime() : -1L);
        dest.writeString(courseStatus);
        if (courseMentors == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(courseMentors);
        }
        if (courseAssessments == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(courseAssessments);
        }
        dest.writeString(courseNotes);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ListItemCourse> CREATOR = new Parcelable.Creator<ListItemCourse>() {
        @Override
        public ListItemCourse createFromParcel(Parcel in) {
            return new ListItemCourse(in);
        }

        @Override
        public ListItemCourse[] newArray(int size) {
            return new ListItemCourse[size];
        }
    };

    public ListItemMentor findMentorByID(int mentorID) {
        ListItemMentor mentor;
        ArrayList<ListItemMentor> mentors = this.getCourseMentors();
        if(mentors.isEmpty()) {
            return null;
        } else {
            for(int i = 0; i < mentors.size(); i++) {
                if(mentors.get(i).getMentorID() == mentorID) {
                    mentor = mentors.get(i);
                    return mentor;
                }
            }
        }
        return mentor = new ListItemMentor();
    }

    public boolean isMentorInList(int mentorID) {
        ArrayList<ListItemMentor> mentors = this.getCourseMentors();
        if(mentors.isEmpty()) {
            return false;
        } else {
            for(int i = 0; i < mentors.size(); i++) {
                if(mentors.get(i).getMentorID() == mentorID) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeMentorByID(int mentorID){
        if(isMentorInList(mentorID)) {
            ArrayList<ListItemMentor> mentors = this.getCourseMentors();
            if(mentors.isEmpty()) {
                return;
            } else {
                for(int i = 0; i < mentors.size(); i++) {
                    if(mentors.get(i).getMentorID() == mentorID)  {
                        ListItemMentor mentor = mentors.get(i);
                        mentors.remove(mentor);
                        return;
                    }
                }
            }
        }
    }
}
