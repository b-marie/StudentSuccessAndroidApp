package org.beemarie.bhellermobileappdevelopment.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class AssessmentDetailActivity extends AppCompatActivity {

    TextView assessmentName;
    TextView assessmentType;
    TextView assessmentDueDate;
    TextView assessmentCourse;
    Button editButton;
    Button homeButton;
    Context context;
    AssessmentAdapter assessmentAdapter;
    AppDatabase db;
    String courseName;
    CourseRepository courseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        db = AppDatabase.getDatabase(getApplicationContext());

        context = this.getApplicationContext();

        final ListItemAssessment assessment = getIncomingIntent();

        assessmentName = findViewById(R.id.assessment_detail_assessment_name);
        assessmentType = findViewById(R.id.assessment_detail_assessment_type);
        assessmentDueDate = findViewById(R.id.assessment_detail_assessment_due_date);
        assessmentCourse = findViewById(R.id.assessment_detail_assessment_course);


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

            ListItemAssessment assessment = new ListItemAssessment(assessmentID, assessmentName, assessmentType, assessmentDueDate, assessmentCourseID);
            setAssessment(assessment);
            return assessment;
        } else {
            ListItemAssessment assessment = new ListItemAssessment(0, "default", "default", new Date(2018, 1, 1), 1);
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

        TextView assessmentCourse = findViewById(R.id.assessment_detail_assessment_course);
        ListItemCourse course = courseRepository.getCourseByID(assessment.getAssessmentCourseID());
        String courseName = course.getCourseName();
        assessmentCourse.setText(courseName);
    }

}
