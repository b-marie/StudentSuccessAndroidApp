package org.beemarie.bhellermobileappdevelopment.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Model for Terms
@Entity(tableName = "term_table")
public class ListItemTerm implements Parcelable{
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
    private LiveData<List<ListItemCourse>> coursesInTerm;

    public ListItemTerm(){}

    public ListItemTerm(int termID, String termName, Date termStartDate, Date termEndDate, LiveData<List<ListItemCourse>> coursesInTerm) {
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

    public LiveData<List<ListItemCourse>> getCoursesInTerm() {
        return coursesInTerm;
    }

    public void setCoursesInTerm(LiveData<List<ListItemCourse>> coursesInTerm) {
        this.coursesInTerm = coursesInTerm;
    }

    protected ListItemTerm(Parcel in) {
        termID = in.readInt();
        termName = in.readString();
        long tmpTermStartDate = in.readLong();
        termStartDate = tmpTermStartDate != -1 ? new Date(tmpTermStartDate) : null;
        long tmpTermEndDate = in.readLong();
        termEndDate = tmpTermEndDate != -1 ? new Date(tmpTermEndDate) : null;
        if (in.readByte() == 0x01) {
            coursesInTerm = getCoursesInTerm();
            in.readList((List) coursesInTerm, ListItemCourse.class.getClassLoader());
        } else {
            coursesInTerm = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(termID);
        dest.writeString(termName);
        dest.writeLong(termStartDate != null ? termStartDate.getTime() : -1L);
        dest.writeLong(termEndDate != null ? termEndDate.getTime() : -1L);
        if (coursesInTerm == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList((List) coursesInTerm);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ListItemTerm> CREATOR = new Parcelable.Creator<ListItemTerm>() {
        @Override
        public ListItemTerm createFromParcel(Parcel in) {
            return new ListItemTerm(in);
        }

        @Override
        public ListItemTerm[] newArray(int size) {
            return new ListItemTerm[size];
        }
    };
}
