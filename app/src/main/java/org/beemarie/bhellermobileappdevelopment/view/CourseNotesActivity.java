package org.beemarie.bhellermobileappdevelopment.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.AppDatabase;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CourseNotesActivity extends AppCompatActivity {

    Button save;
    Button shareViaEmail;
    Button backToCourse;
    Button home;
    EditText courseNotesEntry;
    ListItemCourse currentCourse;
    AppDatabase db;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_notes);

        db = AppDatabase.getDatabase(getApplicationContext());
        context = this.getApplicationContext();
        currentCourse = getIncomingIntent();

        save = (Button) findViewById(R.id.course_notes_save_button);
        shareViaEmail = (Button) findViewById(R.id.course_notes_share_button);
        backToCourse = (Button) findViewById(R.id.course_notes_back_button);
        home = (Button) findViewById(R.id.course_notes_home_button);
        courseNotesEntry = findViewById(R.id.course_notes_entry);

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final int ID = currentCourse.getCourseID();
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("CourseNotesActivity", "Saving course notes");
                            String courseNotes = courseNotesEntry.getText().toString();
                            currentCourse.setCourseNotes(courseNotes);
                            db.courseDao().updateCourse(currentCourse);
                            Intent intent = new Intent(context, CourseDetailActivity.class);
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

        shareViaEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "", null
                ));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Course notes for " + currentCourse.getCourseName());
                intent.putExtra(Intent.EXTRA_TEXT, "Course notes: " + currentCourse.getCourseNotes());
                startActivity(Intent.createChooser(intent, "Choose an Email client: "));
            }
        });

        backToCourse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final int ID = currentCourse.getCourseID();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("CourseNotesActivity", "Returning to course");
                        Intent intent = new Intent(context, CourseDetailActivity.class);
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

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                view.getContext().startActivity(intent);
            }
        });

    }


    private ListItemCourse getIncomingIntent() {
        Log.d("CourseNotesActivity", "Checking for incoming intents");
        Log.d("CourseNotesActivity", "Found intent extras");
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
        courseNotesEntry = findViewById(R.id.course_notes_entry);
        courseNotesEntry.setText(course.getCourseNotes());

    }
}
