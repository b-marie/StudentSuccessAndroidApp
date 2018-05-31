package org.beemarie.bhellermobileappdevelopment.view;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
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
import java.util.List;
import java.util.Locale;

public class AddNewCourse extends AppCompatActivity {

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
    Button saveButton;
    Context context;
    AppDatabase db;
    Calendar mCalendar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_course);

        db = AppDatabase.getDatabase(getApplicationContext());

        context = this.getApplicationContext();

        mCalendar = Calendar.getInstance();


        courseStart = findViewById(R.id.add_course_start_date_entry);
        courseEnd = findViewById(R.id.add_course_end_date_entry);
        courseName = findViewById(R.id.add_course_name_entry);
        courseStatusGroup = (RadioGroup) findViewById(R.id.add_course_course_status_radio_group);
        courseStatusPlanned = (RadioButton) findViewById(R.id.add_course_planned_status);
        courseStatusInProgress = (RadioButton) findViewById(R.id.add_course_in_progress_status);
        courseStatusCompleted = (RadioButton) findViewById(R.id.add_course_completed_status);
        saveButton = (Button) findViewById(R.id.add_course_save_button);
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
                new DatePickerDialog(AddNewCourse.this, startDate, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
                new DatePickerDialog(AddNewCourse.this, endDate, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                        final Intent replyIntent = new Intent(getApplicationContext(), MentorListActivity.class);
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
                            Log.i("AddNewCourse", "Got to the adding of course");
                            String newCourseName = courseName.getText().toString();
                            String courseStartDate = courseStart.getText().toString();
                            Date newCourseStartDate = new Date(courseStartDate);
                            String courseEndDate = courseEnd.getText().toString();
                            Date newCourseEndDate = new Date(courseEndDate);
                            int statusID = courseStatusGroup.getCheckedRadioButtonId();
                            statusButton = (RadioButton) findViewById(statusID);
                            String courseStatus = statusButton.getText().toString();
                            String courseNotes = "";
                            ArrayList<ListItemMentor> courseMentors = new ArrayList<>();
                            ArrayList<ListItemAssessment> courseAssessments = new ArrayList<>();
                            ListItemCourse course = new ListItemCourse(newCourseName, newCourseStartDate, newCourseEndDate, courseStatus, courseMentors, courseAssessments, courseNotes);
                            db.courseDao().insert(course);
                        }

                    });
                    Intent intent = new Intent(view.getContext(), CourseListActivity.class);
                    view.getContext().startActivity(intent);

                }
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
}
