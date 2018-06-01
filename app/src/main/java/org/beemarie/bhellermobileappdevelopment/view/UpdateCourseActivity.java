package org.beemarie.bhellermobileappdevelopment.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.AppDatabase;
import org.beemarie.bhellermobileappdevelopment.data.ListItemAssessment;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateCourseActivity extends AppCompatActivity {

    EditText courseName;
    EditText courseStart;
    EditText courseEnd;
    Switch courseStartDateReminder;
    Switch courseEndDateReminder;
    RadioGroup courseStatusGroup;
    RadioButton courseStatusPlanned;
    RadioButton courseStatusInProgress;
    RadioButton courseStatusCompleted;
    RadioButton statusButton;
    Button updateButton;
    Button deleteButton;
    Context context;
    AppDatabase db;
    Calendar mCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);

        db = AppDatabase.getDatabase(getApplicationContext());

        context = this.getApplicationContext();

        final ListItemCourse course = getIncomingIntent();
        final int courseID = course.getCourseID();

        mCalendar = Calendar.getInstance();

        courseStart = findViewById(R.id.update_course_start_date_entry);
        courseEnd = findViewById(R.id.update_course_end_date_entry);
        courseName = findViewById(R.id.update_course_name_entry);
        courseStatusGroup = (RadioGroup) findViewById(R.id.update_course_course_status_radio_group);
        courseStatusPlanned = (RadioButton) findViewById(R.id.update_course_planned_status);
        courseStatusInProgress = (RadioButton) findViewById(R.id.update_course_in_progress_status);
        courseStatusCompleted = (RadioButton) findViewById(R.id.update_course_completed_status);
        updateButton = (Button) findViewById(R.id.update_course_save_button);
        deleteButton = (Button) findViewById(R.id.update_course_delete_button);
        final DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStartLabel();
            }
        };

        courseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UpdateCourseActivity.this, startDate, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndLabel();
            }
        };

        courseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UpdateCourseActivity.this, endDate, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final int ID = courseID;
                if (courseName.getText().toString().equals("") && courseStart.getText().toString().equals("") && courseEnd.getText().toString().equals("")) {
                    //Add a toast to say they need to enter text
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.please_enter_text,
                            Toast.LENGTH_LONG).show();
                } else {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("UpdateCourseActivity", "Got to the updating of course");
                            int updateCourseID = courseID;
                            String updateCourseName = courseName.getText().toString();
                            String courseStartDate = courseStart.getText().toString();
                            Date updateCourseStartDate = new Date(courseStartDate);
                            String courseEndDate = courseEnd.getText().toString();
                            Date updateCourseEndDate = new Date(courseEndDate);
                            int statusID = courseStatusGroup.getCheckedRadioButtonId();
                            statusButton = (RadioButton) findViewById(statusID);
                            String updateCourseStatus = statusButton.getText().toString();
                            String courseNotes = course.getCourseNotes();
                            int courseTermID = course.getCourseTermID();

                            ListItemCourse updateCourse = new ListItemCourse(updateCourseID, updateCourseName, updateCourseStartDate, updateCourseEndDate, updateCourseStatus, courseNotes, courseTermID);
                            db.courseDao().updateCourse(updateCourse);
                        }

                    });
                    Intent intent = new Intent(view.getContext(), CourseListActivity.class);
                    view.getContext().startActivity(intent);
                }
                finish();
            }

        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final ListItemCourse deleteCourse = course;
//                        final Intent replyIntent = new Intent(getApplicationContext(), MentorListActivity.class);
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            int courseId = courseID;
                            Log.i("UpdateCourseActivity", "Got to delete course");
                            db.courseDao().deleteByID(courseId);
                        }

                    });
                    Intent intent = new Intent(view.getContext(), CourseListActivity.class);
                    view.getContext().startActivity(intent);
                finish();
            }

        });



    }

    private void updateStartLabel() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        courseStart.setText(sdf.format(mCalendar.getTime()));
    }

    private void updateEndLabel() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        courseEnd.setText(sdf.format(mCalendar.getTime()));
    }

    private ListItemCourse getIncomingIntent() {
        Log.d("CourseDetailActivity", "Checking for incoming intents");
        if (getIntent().hasExtra("courseName")) {
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
        } else {
            ListItemCourse course = new ListItemCourse(0, "default",
                    new Date(2018, 1, 1), new Date(2018, 1, 1),
                    "default", "default", 0);
            setCourse(course);
            return course;
        }
    }

    private void setCourse(ListItemCourse course) {
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        EditText courseName = findViewById(R.id.update_course_name_entry);
        courseName.setText(course.getCourseName());

        EditText courseStart = findViewById(R.id.update_course_start_date_entry);
        Date startDate = course.getCourseStartDate();
        String cStartDate = simpleDateFormat.format(startDate);
        courseStart.setText(cStartDate);

        EditText courseEnd = findViewById(R.id.update_course_end_date_entry);
        Date endDate = course.getCourseEndDate();
        String cEndDate = simpleDateFormat.format(endDate);
        courseEnd.setText(cEndDate);

        Switch courseStartDateReminder = findViewById(R.id.update_course_start_date_toggle);
        Switch courseEndDateReminder = findViewById(R.id.update_course_end_date_toggle);


        RadioGroup courseStatusGroup = (RadioGroup) findViewById(R.id.update_course_course_status_radio_group);

        RadioButton courseStatusPlanned = (RadioButton) findViewById(R.id.update_course_planned_status);
        RadioButton courseStatusInProgress = (RadioButton) findViewById(R.id.update_course_in_progress_status);
        RadioButton courseStatusCompleted = (RadioButton) findViewById(R.id.update_course_completed_status);

    }
}
