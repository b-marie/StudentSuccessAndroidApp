package org.beemarie.bhellermobileappdevelopment.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.beemarie.bhellermobileappdevelopment.R;

public class HomeActivity extends AppCompatActivity {

    Button termButton;
    Button courseButton;
    Button mentorButton;
    Button assessmentButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        termButton = (Button) findViewById(R.id.terms_button);
        termButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TermListActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        courseButton = (Button) findViewById(R.id.courses_button);
        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CourseListActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        assessmentButton = (Button) findViewById(R.id.assessments_button);
        assessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AssessmentListActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        mentorButton = (Button) findViewById(R.id.mentors_button);
        mentorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MentorListActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }



}
