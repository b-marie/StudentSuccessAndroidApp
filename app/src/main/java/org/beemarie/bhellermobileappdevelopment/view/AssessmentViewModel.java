package org.beemarie.bhellermobileappdevelopment.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import org.beemarie.bhellermobileappdevelopment.data.AssessmentRepository;
import org.beemarie.bhellermobileappdevelopment.data.ListItemAssessment;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;
import org.beemarie.bhellermobileappdevelopment.data.TermRepository;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    private AssessmentRepository assessmentRepository;
    private LiveData<List<ListItemAssessment>> allAssessments;

    public AssessmentViewModel(Application application) {
        super(application);
        assessmentRepository = new AssessmentRepository(application);
        allAssessments = assessmentRepository.getAllAssessments();
    }

    LiveData<List<ListItemAssessment>> getAllAssessments() {return allAssessments;}

    public void insert(ListItemAssessment assessment) {assessmentRepository.insert(assessment); }
}
