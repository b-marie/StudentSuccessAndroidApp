package org.beemarie.bhellermobileappdevelopment.view;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.AppDatabase;
import org.beemarie.bhellermobileappdevelopment.data.CourseRepository;
import org.beemarie.bhellermobileappdevelopment.data.ListItemAssessment;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssessmentDetailActivity extends AppCompatActivity {

    TextView assessmentName;
    TextView assessmentType;
    TextView assessmentDueDate;
    Button editButton;
    Button homeButton;
    Context context;
    AssessmentAdapter assessmentAdapter;
    AppDatabase db;
    String courseName;
    CourseRepository courseRepository;
    RecyclerView courseRecyclerView;
    List<ListItemCourse> courseWithAssessment;
    CourseAdapter courseAdapter;
    CourseViewModel courseViewModel;
    ListItemAssessment assessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        db = AppDatabase.getDatabase(getApplicationContext());

        context = this.getApplicationContext();


        //Get current assessment based on incoming intent
        assessment = getIncomingIntent();


        //Set the course recycler view
        courseRecyclerView = (RecyclerView) findViewById(R.id.assessment_detail_course_recycler_view);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter = new CourseAdapter(context, courseWithAssessment);
        courseRecyclerView.setAdapter(courseAdapter);

        //Get the viewmodel for courses
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

        courseViewModel.getAllCourses().observe(this, new Observer<List<ListItemCourse>>() {
            @Override
            public void onChanged(@Nullable final List<ListItemCourse> courses) {
                //Update cached list of mentors in the adapter
                courseWithAssessment = getCoursesByID(courses);
                courseAdapter.setCourses(courseWithAssessment);
            }
        });





        assessmentName = findViewById(R.id.assessment_detail_assessment_name);
        assessmentType = findViewById(R.id.assessment_detail_assessment_type);
        assessmentDueDate = findViewById(R.id.assessment_detail_assessment_due_date);


        homeButton = (Button) findViewById(R.id.assessment_detail_home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        editButton = (Button) findViewById(R.id.assessment_detail_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateAssessmentActivity.class);
                intent.putExtra("assessmentID", assessment.getAssessmentID());
                intent.putExtra("assessmentName", assessment.getAssessmentName());
                intent.putExtra("assessmentType", assessment.getAssessmentType());
                intent.putExtra("assessmentDueDate", assessment.getAssessmentDueDate());
                intent.putExtra("assessmentCourseID", assessment.getAssessmentCourseID());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private ListItemAssessment getIncomingIntent(){
        Log.d("AssessDetailActivity", "Checking for incoming intents");
        if(getIntent().hasExtra("assessmentName") && getIntent().hasExtra("assessmentName")) {
            Log.d("AssessDetailActivity", "Found intent extras");
            int assessmentID = getIntent().getIntExtra("assessmentID", 0);
            String assessmentName = getIntent().getStringExtra("assessmentName");
            String assessmentType = getIntent().getStringExtra("assessmentType");
            Date assessmentDueDate = (Date) getIntent().getSerializableExtra("assessmentDueDate");
            int assessmentCourseID = getIntent().getIntExtra("assessmentCourseID", 0);
            boolean assessmentReminder = getIntent().getBooleanExtra("assessmentReminder", false);

            ListItemAssessment assessment = new ListItemAssessment(assessmentID, assessmentName, assessmentType, assessmentDueDate, assessmentCourseID, assessmentReminder);
            setAssessment(assessment);
            return assessment;
        } else {
            ListItemAssessment assessment = new ListItemAssessment(0, "default", "default", new Date(2018, 1, 1), 1, false);
            setAssessment(assessment);
            return assessment;
        }
    }

    private void setAssessment(final ListItemAssessment assessment){
        TextView assessmentName = findViewById(R.id.assessment_detail_assessment_name);
        assessmentName.setText(assessment.getAssessmentName());

        TextView assessmentDueDate = findViewById(R.id.assessment_detail_assessment_due_date);
        Date date = assessment.getAssessmentDueDate();
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dueDate = simpleDateFormat.format(date);
        assessmentDueDate.setText(dueDate);

        TextView assessmentType = findViewById(R.id.assessment_detail_assessment_type);
        assessmentType.setText(assessment.getAssessmentType());

    }

    public List<ListItemCourse> getCoursesByID (List<ListItemCourse> courses) {
        ListItemAssessment assessmentToCompare = assessment;
        List<ListItemCourse> courseToReturn = new ArrayList<ListItemCourse>();
        for(int i = 0; i < courses.size(); i++  ){
            if(assessment.getAssessmentCourseID() == courses.get(i).getCourseID()){
                courseToReturn.add(courses.get(i));
            }
        }
        return courseToReturn;
    }

}
