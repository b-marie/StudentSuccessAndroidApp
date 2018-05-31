package org.beemarie.bhellermobileappdevelopment.view;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    Context context;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        db = AppDatabase.getDatabase(getApplicationContext());

        context = this.getApplicationContext();

        final ListItemCourse course = getIncomingIntent();

        TextView courseName = findViewById(R.id.course_detail_course_name);
        TextView courseStatus = findViewById(R.id.course_detail_status);
        TextView courseStartDate = findViewById(R.id.course_detail_course_start_date);
        TextView courseEndDate = findViewById(R.id.course_detail_course_end_date);
        TextView courseMentorName = findViewById(R.id.course_detail_mentor_name);
        TextView courseMentorPhoneNumber = findViewById(R.id.course_detail_mentor_phone_number);
        TextView courseMentorEmail = findViewById(R.id.course_detail_mentor_email);
        TextView courseAssessmentName = findViewById(R.id.course_detail_course_assessment_name);

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

        TextView courseMentorName = findViewById(R.id.course_detail_mentor_name);
//        courseMentorName.setText(courseMentor.getMentorName());

        TextView courseMentorPhoneNumber = findViewById(R.id.course_detail_mentor_phone_number);
//        courseMentorPhoneNumber.setText(courseMentor.getMentorPhoneNumber());

        TextView courseMentorEmail = findViewById(R.id.course_detail_mentor_email);
//        courseMentorEmail.setText(courseMentor.getMentorEmail());

        ArrayList<ListItemAssessment> courseAssessments = course.getCourseAssessments();
//        ListItemAssessment courseAssessment1 = courseAssessments.get(0);

        TextView courseAssessmentName = findViewById(R.id.course_detail_course_assessment_name);
//        courseAssessmentName.setText(courseAssessment1.getAssessmentName());
    }
}
