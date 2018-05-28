package org.beemarie.bhellermobileappdevelopment.logic;

import android.view.View;

import org.beemarie.bhellermobileappdevelopment.data.DataSourceInterface;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;
import org.beemarie.bhellermobileappdevelopment.view.TermViewInterface;

import java.util.List;

public class TermController {
    private ListItemTerm temporaryListItemTerm;
    private int temporaryListItemTermPosition;


    private TermViewInterface view;
    private DataSourceInterface dataSource;

    public TermController(TermViewInterface view, DataSourceInterface dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    public void onListItemTermClick(ListItemTerm term) {

    }


    public void getTermListFromDataSource() {
        view.setUpAdapterAndView(
                dataSource.getListOfTerms()
        );
    }
}
