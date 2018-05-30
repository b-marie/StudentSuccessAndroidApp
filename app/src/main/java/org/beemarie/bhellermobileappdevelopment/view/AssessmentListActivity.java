package org.beemarie.bhellermobileappdevelopment.view;

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

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.AppDatabase;
import org.beemarie.bhellermobileappdevelopment.data.ListItemAssessment;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;

import java.util.List;

public class AssessmentListActivity extends AppCompatActivity {

    Button addAssessment;
    Button home;
    public static final int NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE = 1;
    AppDatabase db;
    Context context;
    private List<ListItemAssessment> assessments;
    private RecyclerView assessmentRecyclerView;
    AssessmentAdapter assessmentAdapter;
    AssessmentViewModel assessmentViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        db = AppDatabase.getDatabase(getApplicationContext());

        context = this.getApplicationContext();

        //Set assessment list recycler
        assessmentRecyclerView = (RecyclerView) findViewById(R.id.assessment_list_recycler);
        assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter = new AssessmentAdapter(context, assessments);
        assessmentRecyclerView.setAdapter(assessmentAdapter);

        //Get the viewmodel
        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);

        //Add an observer to return mentors
        assessmentViewModel.getAllAssessments().observe(this, new Observer<List<ListItemAssessment>>() {
            @Override
            public void onChanged(@Nullable final List<ListItemAssessment> assessments) {
                //Update cached list of mentors in the adapter
                assessmentAdapter.setAssessments(assessments);
            }
        });


        home = (Button) findViewById(R.id.assessment_list_home_button);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        addAssessment = (Button) findViewById(R.id.assessment_list_add_assessment_button);
        addAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentListActivity.this, AddNewAssessment.class);
                startActivityForResult(intent, NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("AssessmentListActivity", "Got to onActivityResult");
        final Intent intent = getIntent();

        AsyncTask.execute(new Runnable() {

            @Override
            public void run() {
                Log.i("AssessmentListActivity", "Got to Runnable");
                if(requestCode == NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE) {
                }

            }
        });




    }

}
