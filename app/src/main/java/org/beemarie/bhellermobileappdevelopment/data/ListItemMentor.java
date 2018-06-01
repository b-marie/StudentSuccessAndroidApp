package org.beemarie.bhellermobileappdevelopment.data;

//Model for mentors

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "mentor_table", indices = {@Index("mentor_ID"), @Index("mentor_course_ID")},
        foreignKeys = {
                @ForeignKey(
                        entity=ListItemCourse.class,
                        parentColumns="course_ID",
                        childColumns="mentor_course_ID",
                        onDelete = CASCADE)})
public class ListItemMentor{


    @PrimaryKey(autoGenerate=true)
    @NonNull
    @ColumnInfo(name = "mentor_ID")
   private int mentorID;

    @ColumnInfo(name = "mentor_name")
   private String mentorName;

    @ColumnInfo(name = "mentor_phone")
   private String mentorPhoneNumber;

    @ColumnInfo(name = "mentor_email")
   private String mentorEmail;

    @ColumnInfo(name="mentor_course_ID")
    private int mentorCourseID;

    @Ignore
    public ListItemMentor(int id, String mentorName, String mentorPhoneNumber, String mentorEmail, int mentorCourseID) {
        this.mentorID = id;
        this.mentorName = mentorName;
        this.mentorPhoneNumber = mentorPhoneNumber;
        this.mentorEmail = mentorEmail;
        this.mentorCourseID = mentorCourseID;
    }

    @Ignore
    public ListItemMentor(){}



    public ListItemMentor(String mentorName, String mentorPhoneNumber, String mentorEmail, int mentorCourseID) {
        this.mentorName = mentorName;
        this.mentorPhoneNumber = mentorPhoneNumber;
        this.mentorEmail = mentorEmail;
        this.mentorCourseID = mentorCourseID;
    }




    @Ignore
    public ListItemMentor(Parcel in){}

    @Ignore
    public ListItemMentor(Parcelable parcelableExtra) {
    }

    public int getMentorID() {
        return mentorID;
    }

    public void setMentorID(int mentorID) {
        this.mentorID = mentorID;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorPhoneNumber() {
        return mentorPhoneNumber;
    }

    public void setMentorPhoneNumber(String mentorPhoneNumber) {
        this.mentorPhoneNumber = mentorPhoneNumber;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public int getMentorCourseID() {
        return mentorCourseID;
    }

    public void setMentorCourseID(int mentorCourseID) {
        this.mentorCourseID = mentorCourseID;
    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(mentorID);
//        dest.writeString(mentorName);
//        dest.writeString(mentorPhoneNumber);
//        dest.writeString(mentorEmail);
//        dest.writeInt(mentorCourseID);
//    }
//
//    @SuppressWarnings("unused")
//    public static final Parcelable.Creator<ListItemMentor> CREATOR = new Parcelable.Creator<ListItemMentor>() {
//        @Override
//        public ListItemMentor createFromParcel(Parcel in) {
//            return new ListItemMentor(in);
//        }
//
//        @Override
//        public ListItemMentor[] newArray(int size) {
//            return new ListItemMentor[size];
//        }
//    };
}
