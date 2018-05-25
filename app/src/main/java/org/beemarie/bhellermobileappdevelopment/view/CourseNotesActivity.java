package org.beemarie.bhellermobileappdevelopment.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import org.beemarie.bhellermobileappdevelopment.R;

public class CourseNotesActivity extends AppCompatActivity {

    Button save;
    Button shareViaEmail;
    Button backToCourse;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_notes);
    }
}
