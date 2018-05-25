package org.beemarie.bhellermobileappdevelopment.view;

import android.view.View;

import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;

import java.util.Date;
import java.util.List;

public interface TermViewInterface {

    void startTermDetailActivity(int ID, String name, Date start, Date end, List<ListItemCourse> courses, View viewRoot);

    void setUpAdapterAndView(List<ListItemTerm> listOfTerms);

}
