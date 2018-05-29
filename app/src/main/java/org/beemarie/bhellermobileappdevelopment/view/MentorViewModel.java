package org.beemarie.bhellermobileappdevelopment.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;
import org.beemarie.bhellermobileappdevelopment.data.MentorRepository;
import org.beemarie.bhellermobileappdevelopment.data.TermRepository;

import java.util.List;

public class MentorViewModel extends AndroidViewModel {
    private MentorRepository mentorRepository;
    private LiveData<List<ListItemMentor>> allMentors;

    public MentorViewModel(Application application) {
        super(application);
        mentorRepository = new MentorRepository(application);
        allMentors = mentorRepository.getAllMentors();
    }

    LiveData<List<ListItemMentor>> getAllMentors() {return allMentors;}

    public void insert(ListItemMentor mentor) {mentorRepository.insert(mentor); }
}
