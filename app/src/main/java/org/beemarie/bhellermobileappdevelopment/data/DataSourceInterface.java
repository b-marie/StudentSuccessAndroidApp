package org.beemarie.bhellermobileappdevelopment.data;

//Contract between classes to dictate how they can talk to each other without implementation details

import java.util.List;

public interface DataSourceInterface {

    List<ListItemTerm> getListOfTerms();
    List<ListItemCourse> getListOfCourses();
    List<ListItemMentor> getListOfMentors();
    List<ListItemAssessment> getListOfAssessments();



}
