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
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;

import java.util.List;

public class CourseListActivity extends AppCompatActivity {

    Button addCourse;
    Button home;
    Context context;
    AppDatabase db;
    RecyclerView courseRecyclerView;
    private List<ListItemCourse> courses;
    CourseViewModel courseViewModel;
    CourseAdapter courseAdapter;
    public static final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        db = AppDatabase.getDatabase(getApplicationContext());

        context = this.getApplicationContext();

        courseRecyclerView = (RecyclerView) findViewById(R.id.course_list_recycler);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter = new CourseAdapter(context, courses);
        courseRecyclerView.setAdapter(courseAdapter);

        home = (Button) findViewById(R.id.course_list_home_button);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        //Get the viewmodel
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

        //Add an observer to return mentors
        courseViewModel.getAllCourses().observe(this, new Observer<List<ListItemCourse>>() {
            @Override
            public void onChanged(@Nullable final List<ListItemCourse> courses) {
                //Update cached list of mentors in the adapter
                courseAdapter.setCourses(courses);
            }
        });

        addCourse = (Button) findViewById(R.id.course_list_add_course_button);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseListActivity.this, AddNewCourse.class);
                startActivityForResult(intent, NEW_COURSE_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("CourseListActivity", "Got to onActivityResult");
        final Intent intent = getIntent();

        AsyncTask.execute(new Runnable() {

            @Override
            public void run() {
                Log.i("CourseListActivity", "Got to Runnable");
                if(requestCode == NEW_COURSE_ACTIVITY_REQUEST_CODE) {
                }

            }
        });




    }
}
