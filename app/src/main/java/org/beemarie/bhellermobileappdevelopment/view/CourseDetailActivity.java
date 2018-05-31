package org.beemarie.bhellermobileappdevelopment.view;

import android.app.LauncherActivity;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    TextView courseMentorName;
    TextView courseMentorPhoneNumber;
    TextView courseMentorEmail;
    TextView courseAssessmentName;

    Button editButton;
    Button addMentorButton;
    Button homeButton;
    Button addAssessmentButton;
    Button courseNotesButton;

    List<ListItemMentor> courseMentors;
    ArrayList<ListItemMentor> currentCourseMentors;
    LinearLayout mentorListViewLayout;

    ListItemCourse currentCourse;

    Context context;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        db = AppDatabase.getDatabase(getApplicationContext());

        context = this.getApplicationContext();

        final ListItemCourse currentCourse = getIncomingIntent();

        populateMentorsList();

        TextView courseName = findViewById(R.id.course_detail_course_name);
        TextView courseStatus = findViewById(R.id.course_detail_status);
        TextView courseStartDate = findViewById(R.id.course_detail_course_start_date);
        TextView courseEndDate = findViewById(R.id.course_detail_course_end_date);

        LinearLayout mentorListViewLayout = (LinearLayout) findViewById(R.id.course_detail_mentor_view_layout);

        Spinner mentorDropdown = findViewById(R.id.course_detail_mentor_list);
        Spinner assessmentsDropdown = findViewById(R.id.course_detail_assessment_list);



        Button editButton = (Button) findViewById(R.id.course_detail_edit_button);
        Button addMentorButton = (Button) findViewById(R.id.course_detail_add_mentor_button);


        Button homeButton = (Button) findViewById(R.id.course_detail_home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                view.getContext().startActivity(intent);
            }
        });


        Button addAssessmentButton = (Button) findViewById(R.id.course_detail_add_assessment_button);
        Button courseNotesButton = (Button) findViewById(R.id.course_detail_course_notes_button);




    }


    private ListItemCourse getIncomingIntent(){
        Log.d("CourseDetailActivity", "Checking for incoming intents");
        if(getIntent().hasExtra("courseName")) {
            Log.d("CourseDetailActivity", "Found intent extras");
            String courseName = getIntent().getStringExtra("courseName");
            String courseStatus = getIntent().getStringExtra("courseStatus");
            String courseNotes = getIntent().getStringExtra("courseNotes");
            Date courseStartDate = (Date) getIntent().getSerializableExtra("courseStartDate");
            Date courseEndDate = (Date) getIntent().getSerializableExtra("courseEndDate");
            int courseID = getIntent().getIntExtra("courseID", 0);
            Bundle bundle = getIntent().getExtras();
            ArrayList<ListItemMentor> courseMentors = bundle.getParcelableArrayList("courseMentors");
            ArrayList<ListItemAssessment> courseAssessments = bundle.getParcelableArrayList("courseAssessments");


            ListItemCourse course = new ListItemCourse(courseID, courseName, courseStartDate, courseEndDate, courseStatus, courseMentors, courseAssessments, courseNotes);
            setCourse(course);
            return course;
        } else {
            ArrayList<ListItemMentor> mentors = new ArrayList<>();
            ArrayList<ListItemAssessment> assessments = new ArrayList<>();
            ListItemCourse course = new ListItemCourse(0, "default", new Date(2018, 1, 1), new Date(2018, 1, 1), "default", mentors, assessments, "default");
            setCourse(course);
            return course;
        }
    }

    private void setCourse(ListItemCourse course){
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

        ArrayList<ListItemMentor> courseMentors = course.getCourseMentors();
//        ListItemMentor courseMentor = courseMentors.get(0);

        ArrayList<ListItemAssessment> courseAssessments = course.getCourseAssessments();
//        ListItemAssessment courseAssessment1 = courseAssessments.get(0);

    }

    private void populateMentorsList(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if(db.mentorDao().getAllMentorsList().isEmpty()){
                    return;
                } else {
                    //Populate the dropdown
                    courseMentors = db.mentorDao().getAllMentorsList();
                    ArrayList<String> mentorNames = new ArrayList<>();
                    for (int i = 0; i < courseMentors.size(); i++) {
                        mentorNames.add(courseMentors.get(i).getMentorName());
                    }
                    Spinner mentorDropdown = findViewById(R.id.course_detail_mentor_list);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, mentorNames);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    mentorDropdown.setAdapter(adapter);
                    mentorDropdown.setSelection(0);
                    //Populate the current course list
                    LinearLayout mentorListViewLayout = (LinearLayout) findViewById(R.id.course_detail_mentor_view_layout);
                    if(currentCourse.getCourseMentors().isEmpty()) {
                        TextView noMentors = new TextView(context);
                        noMentors.setText("No Mentors");
                        mentorListViewLayout.addView(noMentors);
                    } else {
                        currentCourseMentors = currentCourse.getCourseMentors();
                        final TextView[] mentorTextViews = new TextView[currentCourseMentors.size()];

                        for(int i = 0; i < currentCourseMentors.size(); i++) {
                            final TextView rowTextView = new TextView(context);
                            rowTextView.setText(currentCourseMentors.get(i).getMentorName() + "\n Phone Number: " + currentCourseMentors.get(i).getMentorPhoneNumber() + "\n Email: " + currentCourseMentors.get(i).getMentorEmail());
                            mentorListViewLayout.addView(rowTextView);
                            mentorTextViews[i] = rowTextView;
                        }
                    }
                }
            }
        });
    }
}
