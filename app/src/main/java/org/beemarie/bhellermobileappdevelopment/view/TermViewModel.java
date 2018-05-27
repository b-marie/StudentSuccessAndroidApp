package org.beemarie.bhellermobileappdevelopment.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;
import org.beemarie.bhellermobileappdevelopment.data.TermRepository;

import java.util.List;

public class TermViewModel extends AndroidViewModel {
    private TermRepository termRepository;
    private List<ListItemTerm> allTerms;

    public TermViewModel(Application application) {
        super(application);
        termRepository = new TermRepository(application);
        allTerms = termRepository.getAllTerms();
    }

    List<ListItemTerm> getAllTerms() {return allTerms;}

    public void insert(ListItemTerm term) {termRepository.insert(term); }



}
