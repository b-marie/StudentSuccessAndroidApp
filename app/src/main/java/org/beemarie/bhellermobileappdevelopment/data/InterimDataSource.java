package org.beemarie.bhellermobileappdevelopment.data;


//Data source made as interim for development


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class InterimDataSource implements DataSourceInterface {
    private static final int SIZE_OF_COLLECTION = 6;

    private final String[] termNames = {
            "Term One",
            "Term Two",
            "Term Three",
            "Term Four",
            "Term Five",
            "Term Six",
    };

    private final Date[] dates = {
            new Date(2018, 1, 1),
            new Date(2018, 7, 1),
            new Date(2019, 1, 1),
            new Date(2019, 7, 1),
            new Date(2020, 1, 1),
            new Date(2020, 7, 1),
    };

    private final String[] courseNames = {
            "Software Engineering",
            "Data Management for Programmers",
            "Org Behavior and Leadership",
            "Basketweaving",
            "MBAs for Dummies",
            "How to get a job 101",
    };

    private final String[] status = {
            "In Progress",
            "Planned",
            "Canceled",
            "Rescheduled",
            "Completed",
            "Dropped",
    };

    private final String[] notes = {
            "This course was very beneficial, I think I will use it in my future job",
            "This course only took me 4 hours to complete and I think it should be removed from the program",
            "This course was very boring and took me almost the entire term to study for",
            "This course's assessment has several useful examples for future portfolio projects",
            "This course really needs a more interesting title",
            "This course was very exciting",
    };

    private final String[] mentorNames = {
            "Billy Joel",
            "Bill gates",
            "Thomas Edison",
            "Albert Einstein",
            "Michael Jackson",
            "Mr. Rogers",
    };

    private final String [] mentorPhones = {
            "523-423-1124",
            "342-423-5634",
            "675-345-2756",
            "694-219-3423",
            "994-223-1219",
            "402-239-2319",
    };

    private final String[] mentorEmails = {
            "IAmTheCoolest@gmail.com",
            "CheckYourInbox@live.com",
            "MentorName@wgu.edu",
            "MrPresident@us.gov",
            "SchoolRocks@gmail.com",
            "ThisIsFake@hotmail.com",
    };

    private final String[] assessmentNames = {
            "Performance Assessment One",
            "Performance Assessment Two",
            "Performance Assessment Three",
            "Objective Assessment One",
            "Objective Assessment Two",
            "Objective Assessment Three",
    };

    private final String[] assessmentTypes = {
            "Performance Assessment",
            "Performance Assessment",
            "Performance Assessment",
            "Objective Assessment",
            "Objective Assessment",
            "Objective Assessment",
    };



    public InterimDataSource() {
    }

    @Override
    public List<ListItemTerm> getListOfTerms() {
        ArrayList<ListItemTerm> listOfTerms = new ArrayList<>();

        Random random = new Random();
        for(int i = 1; i < 7; i++) {
            int randOne = random.nextInt(7);
            int randTwo = random.nextInt(7);
            int randThree = random.nextInt(7);


            ListItemTerm term = new ListItemTerm(i, termNames[randOne], dates[randTwo], dates[randThree], getListOfCourses());

            listOfTerms.add(term);
        }

        return listOfTerms;
    }

    @Override
    public List<ListItemCourse> getListOfCourses() {
        ArrayList<ListItemCourse> listOfCourses = new ArrayList<>();

        Random random = new Random();
        for(int i = 1; i < 7; i++) {
            int randOne = random.nextInt(7);
            int randTwo = random.nextInt(7);
            int randThree = random.nextInt(7);
            int randFour = random.nextInt(7);
            int randFive = random.nextInt(7);


            ListItemCourse course = new ListItemCourse(i, courseNames[randOne], dates[randTwo], dates[randThree], status[randFour], getListOfMentors(), getListOfAssessments(), notes[randFive]);

            listOfCourses.add(course);
        }

        return listOfCourses;
    }

    @Override
    public List<ListItemMentor> getListOfMentors() {
        ArrayList<ListItemMentor> listOfMentors = new ArrayList<>();

        Random random = new Random();
        for(int i = 1; i < 7; i++) {
            int randOne = random.nextInt(7);
            int randTwo = random.nextInt(7);
            int randThree = random.nextInt(7);

            ListItemMentor mentor = new ListItemMentor(i, mentorNames[randOne], mentorPhones[randTwo], mentorEmails[randThree]);

            listOfMentors.add(mentor);
        }

        return listOfMentors;
    }

    @Override
    public List<ListItemAssessment> getListOfAssessments() {
        ArrayList<ListItemAssessment> listOfAssessments = new ArrayList<>();

        Random random = new Random();
        for(int i = 1; i < 7; i++) {
            int randOne = random.nextInt(7);
            int randTwo = random.nextInt(7);
            int randThree = random.nextInt(7);

            ListItemAssessment assessment = new ListItemAssessment(i, assessmentNames[randOne], assessmentTypes[randTwo], dates[randThree]);

            listOfAssessments.add(assessment);
        }

        return listOfAssessments;
    }


}
