package org.beemarie.bhellermobileappdevelopment.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class MentorDetailActivity extends AppCompatActivity {

    TextView mentorName;
    TextView mentorEmail;
    TextView mentorPhoneNumber;
    TextView mentorCourse;
    Button editButton;
    Button homeButton;
    Context context;
    MentorAdapter mentorAdapter;
    AppDatabase db;
    CourseRepository courseRepository;
    CourseViewModel courseViewModel;
    CourseAdapter courseAdapter;
    List<ListItemCourse> courses;
    List<ListItemCourse> courseWithMentor;
    ListItemCourse course;
    RecyclerView courseRecyclerView;
    ListItemMentor mentor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_detail);

        db = AppDatabase.getDatabase(getApplicationContext());

        context = this.getApplicationContext();

        //Set current mentor based on incoming intent
        mentor = getIncomingIntent();

        //Set the course recycler view
        courseRecyclerView = (RecyclerView) findViewById(R.id.mentor_detail_course_recycler_view);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter = new CourseAdapter(context, courseWithMentor);
        courseRecyclerView.setAdapter(courseAdapter);

        courseAdapter = new CourseAdapter(context, courses);
        //Get the viewmodel for courses
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

        courseViewModel.getAllCourses().observe(this, new Observer<List<ListItemCourse>>() {
            @Override
            public void onChanged(@Nullable final List<ListItemCourse> courses) {
                //Update cached list of mentors in the adapter
                courseWithMentor = getCoursesByID(courses);
                courseAdapter.setCourses(courseWithMentor);
            }
        });


//        mentorName = findViewById(R.id.mentor_detail_mentor_name);
//        mentorPhoneNumber = findViewById(R.id.mentor_detail_phone_number);
//        mentorEmail = findViewById(R.id.mentor_detail_email);
//        mentorCourse=findViewById(R.id.mentor_detail_course);

        homeButton = (Button) findViewById(R.id.mentor_detail_home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        editButton = (Button) findViewById(R.id.mentor_detail_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateMentorActivity.class);
                intent.putExtra("mentorID", mentor.getMentorID());
                intent.putExtra("mentorName", mentor.getMentorName());
                intent.putExtra("mentorPhoneNumber", mentor.getMentorPhoneNumber());
                intent.putExtra("mentorEmail", mentor.getMentorEmail());
                intent.putExtra("mentorCourseID", mentor.getMentorCourseID());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private ListItemMentor getIncomingIntent(){
        Log.d("MentorDetailActivity", "Checking for incoming intents");
        if(getIntent().hasExtra("mentorName") && getIntent().hasExtra("mentorEmail")) {
            Log.d("MentorDetailActivity", "Found intent extras");
            int mentorID = getIntent().getIntExtra("mentorID", 0);
            String mentorName = getIntent().getStringExtra("mentorName");
            String mentorPhoneNumber = getIntent().getStringExtra("mentorPhoneNumber");
            String mentorEmail = getIntent().getStringExtra("mentorEmail");
            int mentorCourseID = getIntent().getIntExtra("mentorCourseID", 0);

            ListItemMentor mentor = new ListItemMentor(mentorID, mentorName, mentorPhoneNumber, mentorEmail, mentorCourseID);
            setMentor(mentor);
            return mentor;
        } else {
            ListItemMentor mentor = new ListItemMentor(0, "default", "default", "default", 1);
            setMentor(mentor);
            return mentor;
        }
    }

    private void setMentor(ListItemMentor mentor){
        TextView mentorName = findViewById(R.id.mentor_detail_mentor_name);
        mentorName.setText(mentor.getMentorName());

        TextView mentorPhoneNumber = findViewById(R.id.mentor_detail_phone_number);
        mentorPhoneNumber.setText(mentor.getMentorPhoneNumber());

        TextView mentorEmail = findViewById(R.id.mentor_detail_email);
        mentorEmail.setText(mentor.getMentorEmail());
    }

    public List<ListItemCourse> getCoursesByID (List<ListItemCourse> courses) {
        ListItemMentor mentorToCompare = mentor;
        List<ListItemCourse> courseToReturn = new ArrayList<ListItemCourse>();
        for(int i = 0; i < courses.size(); i++  ){
            if(mentor.getMentorCourseID() == courses.get(i).getCourseID()){
                courseToReturn.add(courses.get(i));
            }
        }
        return courseToReturn;
    }
}
