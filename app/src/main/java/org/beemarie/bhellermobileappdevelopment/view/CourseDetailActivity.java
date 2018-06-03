package org.beemarie.bhellermobileappdevelopment.view;

import android.app.LauncherActivity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.AppDatabase;
import org.beemarie.bhellermobileappdevelopment.data.ListItemAssessment;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseDetailActivity extends AppCompatActivity {

    TextView courseName;
    TextView courseStatus;
    TextView courseStartDate;
    TextView courseEndDate;

    Button editButton;
    Button homeButton;
    Button courseNotesButton;

    List<ListItemMentor> courseMentors;
    ArrayList<ListItemMentor> currentCourseMentors;
    LinearLayout mentorListViewLayout;

    ListItemCourse currentCourse;

    Context context;
    AppDatabase db;

    RecyclerView mentorRecyclerView;
    MentorAdapter mentorAdapter;
    MentorViewModel mentorViewModel;
    List<ListItemMentor> mentorsInCourse;
    List<ListItemMentor> mentors;

    RecyclerView assessmentRecyclerView;
    AssessmentAdapter assessmentAdapter;
    AssessmentViewModel assessmentViewModel;
    List<ListItemAssessment> assessmentsInCourse;
    List<ListItemAssessment> assessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        db = AppDatabase.getDatabase(getApplicationContext());

        context = this.getApplicationContext();

        currentCourse = getIncomingIntent();

//        populateMentorsList(currentCourse);

        TextView courseName = findViewById(R.id.course_detail_course_name);
        TextView courseStatus = findViewById(R.id.course_detail_status);
        TextView courseStartDate = findViewById(R.id.course_detail_course_start_date);
        TextView courseEndDate = findViewById(R.id.course_detail_course_end_date);

        //Populate mentor recycler view
        mentorRecyclerView = (RecyclerView) findViewById(R.id.course_detail_mentor_recycler_view);
        mentorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mentorAdapter = new MentorAdapter(context, mentorsInCourse);
        mentorRecyclerView.setAdapter(mentorAdapter);

        //Get the viewmodel
        mentorViewModel = ViewModelProviders.of(this).get(MentorViewModel.class);

        //Add an observer to return mentors
        mentorViewModel.getAllMentors().observe(this, new Observer<List<ListItemMentor>>() {
            @Override
            public void onChanged(@Nullable final List<ListItemMentor> mentors) {
                //Update cached list of mentors in the adapter
                mentorsInCourse = mentorsInCourse(mentors);
                mentorAdapter.setMentors(mentorsInCourse);
            }
        });

        //Populate assessment recycler view
        //Set assessment list recycler
        assessmentRecyclerView = (RecyclerView) findViewById(R.id.course_detail_assessment_recycler_view);
        assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter = new AssessmentAdapter(context, assessmentsInCourse);
        assessmentRecyclerView.setAdapter(assessmentAdapter);

        //Get the viewmodel
        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);

        //Add an observer to return mentors
        assessmentViewModel.getAllAssessments().observe(this, new Observer<List<ListItemAssessment>>() {
            @Override
            public void onChanged(@Nullable final List<ListItemAssessment> assessments) {
                //Update cached list of mentors in the adapter
                assessmentsInCourse = assessmentsInCourse(assessments);
                assessmentAdapter.setAssessments(assessmentsInCourse);
            }
        });


        Button editButton = (Button) findViewById(R.id.course_detail_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("CourseDetailActivity", "Clicked on " + currentCourse.getCourseName());
                        Intent intent = new Intent(context, UpdateCourseActivity.class);
                        intent.putExtra("courseID", currentCourse.getCourseID());
                        intent.putExtra("courseName", currentCourse.getCourseName());
                        intent.putExtra("courseStartDate", currentCourse.getCourseStartDate());
                        intent.putExtra("courseEndDate", currentCourse.getCourseEndDate());
                        intent.putExtra("courseStatus", currentCourse.getCourseStatus());
                        intent.putExtra("courseNotes", currentCourse.getCourseNotes());
                        intent.putExtra("courseTermID", currentCourse.getCourseTermID());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }

                });
            }


        });

        Button homeButton = (Button) findViewById(R.id.course_detail_home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                view.getContext().startActivity(intent);
            }
        });


        Button courseNotesButton = (Button) findViewById(R.id.course_detail_course_notes_button);
        courseNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CourseNotesActivity.class);
                intent.putExtra("courseID", currentCourse.getCourseID());
                intent.putExtra("courseName", currentCourse.getCourseName());
                intent.putExtra("courseStartDate", currentCourse.getCourseStartDate());
                intent.putExtra("courseEndDate", currentCourse.getCourseEndDate());
                intent.putExtra("courseStatus", currentCourse.getCourseStatus());
                intent.putExtra("courseNotes", currentCourse.getCourseNotes());
                intent.putExtra("courseTermID", currentCourse.getCourseTermID());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }


    private ListItemCourse getIncomingIntent() {
        Log.d("CourseDetailActivity", "Checking for incoming intents");
                Log.d("CourseDetailActivity", "Found intent extras");
                int courseID = getIntent().getIntExtra("courseID", 0);
                String courseName = getIntent().getStringExtra("courseName");
                Date courseStartDate = (Date) getIntent().getSerializableExtra("courseStartDate");
                Date courseEndDate = (Date) getIntent().getSerializableExtra("courseEndDate");
                String courseStatus = getIntent().getStringExtra("courseStatus");
                String courseNotes = getIntent().getStringExtra("courseNotes");
                int courseTermID = getIntent().getIntExtra("courseTermID", 0);


                ListItemCourse course = new ListItemCourse(courseID, courseName, courseStartDate, courseEndDate, courseStatus,
                        courseNotes, courseTermID);
                setCourse(course);
                return course;
    }

    private void setCourse(ListItemCourse course) {
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        TextView courseName = findViewById(R.id.course_detail_course_name);
        courseName.setText(course.getCourseName());

        TextView courseStatus = findViewById(R.id.course_detail_status);
        courseStatus.setText(course.getCourseStatus());

        TextView courseStartDate = findViewById(R.id.course_detail_course_start_date);
        Date startDate = course.getCourseStartDate();
        String cStartDate = simpleDateFormat.format(startDate);
        courseStartDate.setText(cStartDate);

        TextView courseEndDate = findViewById(R.id.course_detail_course_end_date);
        Date endDate = course.getCourseEndDate();
        String cEndDate = simpleDateFormat.format(endDate);
        courseEndDate.setText(cEndDate);
    }


    private List<ListItemMentor> mentorsInCourse(List<ListItemMentor> mentors) {
        ListItemCourse courseToCompare = currentCourse;

        List<ListItemMentor> mentorsInCourse = new ArrayList<ListItemMentor>();

            for(int i = 0; i < mentors.size(); i++) {
                if(mentors.get(i).getMentorCourseID() == currentCourse.getCourseID()) {
                    mentorsInCourse.add(mentors.get(i));
                }
        }
        return mentorsInCourse;
    }

    private List<ListItemAssessment> assessmentsInCourse(List<ListItemAssessment> assessments) {
        ListItemCourse courseToCompare = currentCourse;

        List<ListItemAssessment> assessmentsInCourse = new ArrayList<ListItemAssessment>();

            for(int i = 0; i < assessments.size(); i++) {
                if(assessments.get(i).getAssessmentCourseID() == currentCourse.getCourseID()) {
                    assessmentsInCourse.add(assessments.get(i));
                }
        }
        return assessmentsInCourse;
    }
}
