package org.beemarie.bhellermobileappdevelopment.data;

//Model for mentors

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "mentor_table")
public class ListItemMentor implements Parcelable {

    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "mentor_ID")
   private int mentorID;

    @ColumnInfo(name = "mentor_name")
   private String mentorName;

    @ColumnInfo(name = "mentor_phone")
   private String mentorPhoneNumber;

    @ColumnInfo(name = "mentor_email")
   private String mentorEmail;



    public ListItemMentor(String mentorName, String mentorPhoneNumber, String mentorEmail) {
        this.mentorName = mentorName;
        this.mentorPhoneNumber = mentorPhoneNumber;
        this.mentorEmail = mentorEmail;
    }

    @Ignore
    public ListItemMentor(int id, String mentorName, String mentorPhoneNumber, String mentorEmail) {
        this.mentorID = id;
        this.mentorName = mentorName;
        this.mentorPhoneNumber = mentorPhoneNumber;
        this.mentorEmail = mentorEmail;
    }

    @Ignore
    public ListItemMentor(Parcel in){}

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mentorID);
        dest.writeString(mentorName);
        dest.writeString(mentorPhoneNumber);
        dest.writeString(mentorEmail);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ListItemMentor> CREATOR = new Parcelable.Creator<ListItemMentor>() {
        @Override
        public ListItemMentor createFromParcel(Parcel in) {
            return new ListItemMentor(in);
        }

        @Override
        public ListItemMentor[] newArray(int size) {
            return new ListItemMentor[size];
        }
    };
}
