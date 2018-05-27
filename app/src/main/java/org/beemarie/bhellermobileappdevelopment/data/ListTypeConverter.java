package org.beemarie.bhellermobileappdevelopment.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ListTypeConverter {
    @TypeConverter
    public String fromTermsList(List<ListItemTerm> terms) {
        if (terms == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ListItemTerm>>() {
        }.getType();
        String jsonString = gson.toJson(terms, type);
        return jsonString;
    }

    @TypeConverter
    public List<ListItemTerm> toTermsList(String termString) {
        if (termString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ListItemTerm>>() {
        }.getType();
        List<ListItemTerm> termsList = gson.fromJson(termString, type);
        return termsList;
    }

    @TypeConverter
    public String fromCoursesList(List<ListItemCourse> courses) {
        if (courses == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ListItemCourse>>() {
        }.getType();
        String jsonString = gson.toJson(courses, type);
        return jsonString;
    }

    @TypeConverter
    public List<ListItemCourse> toCoursesList(String courseString) {
        if (courseString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ListItemCourse>>() {
        }.getType();
        List<ListItemCourse> coursesList = gson.fromJson(courseString, type);
        return coursesList;
    }

    @TypeConverter
    public String fromMentorsList(List<ListItemMentor> mentors) {
        if (mentors == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ListItemMentor>>() {
        }.getType();
        String jsonString = gson.toJson(mentors, type);
        return jsonString;
    }

    @TypeConverter
    public List<ListItemMentor> toMentorsList(String mentorString) {
        if (mentorString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ListItemMentor>>() {
        }.getType();
        List<ListItemMentor> mentorsList = gson.fromJson(mentorString, type);
        return mentorsList;
    }

    @TypeConverter
    public String fromAssessmentsList(List<ListItemAssessment> assessments) {
        if (assessments == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ListItemAssessment>>() {
        }.getType();
        String jsonString = gson.toJson(assessments, type);
        return jsonString;
    }

    @TypeConverter
    public List<ListItemAssessment> toAssessmentsList(String assessmentString) {
        if (assessmentString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ListItemAssessment>>() {
        }.getType();
        List<ListItemAssessment> assessmentsList = gson.fromJson(assessmentString, type);
        return assessmentsList;
    }


}
