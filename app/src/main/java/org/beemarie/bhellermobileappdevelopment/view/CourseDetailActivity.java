package org.beemarie.bhellermobileappdevelopment.view;

import android.app.LauncherActivity;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        populateMentorsList(currentCourse);

        TextView courseName = findViewById(R.id.course_detail_course_name);
        TextView courseStatus = findViewById(R.id.course_detail_status);
        TextView courseStartDate = findViewById(R.id.course_detail_course_start_date);
        TextView courseEndDate = findViewById(R.id.course_detail_course_end_date);

        LinearLayout mentorListViewLayout = (LinearLayout) findViewById(R.id.course_detail_mentor_view_layout);

        final Spinner mentorDropdown = findViewById(R.id.course_detail_mentor_list);
        Spinner assessmentsDropdown = findViewById(R.id.course_detail_assessment_list);


        Button editButton = (Button) findViewById(R.id.course_detail_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("CourseDetailActivity", "Clicked on " + currentCourse.getCourseName());
                        Intent intent = new Intent(context, UpdateCourseActivity.class);
                        intent.putExtra("courseName", currentCourse.getCourseName());
                        intent.putExtra("courseStartDate", currentCourse.getCourseStartDate());
                        intent.putExtra("courseEndDate", currentCourse.getCourseEndDate());
                        intent.putExtra("courseStatus", currentCourse.getCourseStatus());
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("courseMentors", currentCourse.getCourseMentors());
                        bundle.putParcelableArrayList("courseAssessments", currentCourse.getCourseAssessments());
                        intent.putExtras(bundle);
                        intent.putExtra("courseNotes", currentCourse.getCourseNotes());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }

                });
            }


        });



        Button addMentorButton = (Button) findViewById(R.id.course_detail_add_mentor_button);
        addMentorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<ListItemMentor> mentorsList = db.mentorDao().getAllMentorsList();
                        int i = mentorDropdown.getSelectedItemPosition();
                        ListItemMentor mentorToAdd = mentorsList.get(i);
                        Log.i("CourseDetailActivity", "Mentor to add is " + mentorToAdd.getMentorName());
                        ArrayList<ListItemMentor> newMentorList = currentCourse.getCourseMentors();
                        newMentorList.add(mentorToAdd);
                        Log.i("CourseDetailActivity", "Size of mentor list is " + newMentorList.size());
                        int currentCourseID = currentCourse.getCourseID();
                        String currentCourseName = currentCourse.getCourseName();
                        String currentCourseStatus = currentCourse.getCourseStatus();
                        Date currentCourseStartDate = currentCourse.getCourseStartDate();
                        Date currentCourseEndDate = currentCourse.getCourseEndDate();
                        ArrayList<ListItemAssessment> currentCourseAssessments = currentCourse.getCourseAssessments();
                        String currentCourseNotes = currentCourse.getCourseNotes();
                        ListItemCourse updatedCourse = new ListItemCourse(currentCourseID, currentCourseName,
                                currentCourseStartDate, currentCourseEndDate, currentCourseStatus, newMentorList,
                                currentCourseAssessments, currentCourseNotes);
                        db.courseDao().updateCourse(updatedCourse);
                        Log.i("CourseDetailActivity", "Updated course name is " + updatedCourse.getCourseName());
                        ListItemCourse checkCourse = db.courseDao().getCourseByID(currentCourseID);
                        Log.i("CourseDetailActivity", "Length of updated course mentor list is " + checkCourse.getCourseMentors().size());

                        //Refresh current activity
//                        try{

//                            intent.putExtra("courseName", currentCourse.getCourseName());
//                            intent.putExtra("courseStatus", currentCourse.getCourseStatus());
//                            intent.putExtra("courseNotes", currentCourse.getCourseNotes());
//                            intent.putExtra("courseStartDate", currentCourse.getCourseStartDate());
//                            intent.putExtra("courseEndDate", currentCourse.getCourseEndDate());
//                            intent.putExtra("courseID", currentCourse.getCourseID());
//                            Bundle bundle = new Bundle();
//                            bundle.putParcelableArrayList("courseMentors", currentCourse.getCourseMentors());
//                            bundle.putParcelableArrayList("courseAssessments", currentCourse.getCourseAssessments());
//                            intent.putExtras(bundle);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            finish();
//                            context.startActivity(intent);}
//                        catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }

                });
                Intent intent = new Intent(context, CourseListActivity.class);
                view.getContext().startActivity(intent);
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


        Button addAssessmentButton = (Button) findViewById(R.id.course_detail_add_assessment_button);
        Button courseNotesButton = (Button) findViewById(R.id.course_detail_course_notes_button);


    }


    private ListItemCourse getIncomingIntent() {
        Log.d("CourseDetailActivity", "Checking for incoming intents");
                Log.d("CourseDetailActivity", "Found intent extras");
                int courseID = getIntent().getIntExtra("courseID", 0);
                String courseName = getIntent().getStringExtra("courseName");
                Date courseStartDate = (Date) getIntent().getSerializableExtra("courseStartDate");
                Date courseEndDate = (Date) getIntent().getSerializableExtra("courseEndDate");
                String courseStatus = getIntent().getStringExtra("courseStatus");
                Bundle bundle = getIntent().getExtras();
                ArrayList<ListItemMentor> courseMentors = bundle.getParcelableArrayList("courseMentors");
                ArrayList<ListItemAssessment> courseAssessments = bundle.getParcelableArrayList("courseAssessments");
                String courseNotes = getIntent().getStringExtra("courseNotes");


                ListItemCourse course = new ListItemCourse(courseID, courseName, courseStartDate, courseEndDate, courseStatus,
                        courseMentors, courseAssessments, courseNotes);
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

        ArrayList<ListItemMentor> courseMentors = course.getCourseMentors();
        if(courseMentors == null) {
            courseMentors = new ArrayList<ListItemMentor>();
        }
        Log.i("CourseDetailActivity", "Course mentors number is " + courseMentors.size());

        ArrayList<ListItemAssessment> courseAssessments = course.getCourseAssessments();
//        ListItemAssessment courseAssessment1 = courseAssessments.get(0);

    }

    private void populateMentorsList(final ListItemCourse course) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (db.mentorDao().getAllMentorsList().isEmpty()) {
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
                    mentorDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                        public void onItemSelected(AdapterView<?>  parent, View view, int pos, long id){
                            Object item = parent.getItemAtPosition(pos);
                        }
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    //Populate the current course list
                    LinearLayout mentorListViewLayout = (LinearLayout) findViewById(R.id.course_detail_mentor_view_layout);
                    ArrayList<ListItemMentor> currentCourseMentors = new ArrayList<ListItemMentor>(course.getCourseMentors());
                    if (currentCourseMentors.isEmpty()) {
                        TextView noMentors = new TextView(context);
                        noMentors.setText("No Mentors");
                        mentorListViewLayout.addView(noMentors);
                    } else {
                        currentCourseMentors = course.getCourseMentors();
                        final TextView[] mentorTextViews = new TextView[currentCourseMentors.size()];

                        for (int i = 0; i < currentCourseMentors.size(); i++) {
                            final TextView rowTextView = new TextView(context);
                            String text = currentCourseMentors.get(i).getMentorName() +
                                    "\n Phone Number: " + currentCourseMentors.get(i).getMentorPhoneNumber() +
                                    "\n Email: " + currentCourseMentors.get(i).getMentorEmail();
                            rowTextView.setText(text);
                            rowTextView.setTextColor(getResources().getColor(R.color.black));
                            mentorListViewLayout.addView(rowTextView);
                            mentorTextViews[i] = rowTextView;
                        }
                    }
                }
            }
        });
    }
}
