package org.beemarie.bhellermobileappdevelopment.view;

import android.arch.lifecycle.LiveData;
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
import android.widget.ListView;
import android.widget.TextView;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.AppDatabase;
import org.beemarie.bhellermobileappdevelopment.data.CourseRepository;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TermDetailActivity extends AppCompatActivity {

    private static final String EXTRA_TERM_NAME = "EXTRA_TERM_NAME";
    private static final String EXTRA_TERM_START_DATE = "EXTRA_TERM_START_DATE";
    private static final String EXTRA_TERM_END_DATE = "EXTRA_TERM_END_DATE";
    private static final String term_id = "Term ID";


    private TextView termName;
    private TextView termStart;
    private TextView termEnd;
    private RecyclerView courseRecyclerView;
    Button editButton;
    Button homeButton;
    AppDatabase db;
    Context context;
    CourseViewModel courseViewModel;
    CourseAdapter courseAdapter;
    ListItemTerm term;
    CourseRepository courseRepository;
    List<ListItemCourse> courses;
    List<ListItemCourse> coursesInTerm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);

        db = AppDatabase.getDatabase(getApplicationContext());

        context = this.getApplicationContext();

        //Set the current term based on the incoming intent
        term = getIncomingIntent();

        //Set recycler view
        courseRecyclerView = (RecyclerView) findViewById(R.id.term_detail_course_recycler_view);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter = new CourseAdapter(context, coursesInTerm);
        courseRecyclerView.setAdapter(courseAdapter);

        //Get the viewmodel for courses
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

        courseViewModel.getAllCourses().observe(this, new Observer<List<ListItemCourse>>() {
            @Override
            public void onChanged(@Nullable final List<ListItemCourse> courses) {
                //Update cached list of mentors in the adapter
                coursesInTerm = coursesInTerm(courses);
                courseAdapter.setCourses(coursesInTerm);
            }
        });

        termName = findViewById(R.id.term_detail_term_name);
        termStart = findViewById(R.id.term_detail_term_start);
        termEnd = findViewById(R.id.term_detail_term_end);

        homeButton = (Button) findViewById(R.id.term_detail_home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        editButton = (Button) findViewById(R.id.term_detail_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateTermActivity.class);
                intent.putExtra("termID", term.getTermID());
                intent.putExtra("termName", term.getTermName());
                intent.putExtra("termStartDate", term.getTermStartDate());
                intent.putExtra("termEndDate", term.getTermEndDate());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private ListItemTerm getIncomingIntent(){
        Log.d("TermDetailActivity", "Checking for incoming intents");
        if(getIntent().hasExtra("termName") && getIntent().hasExtra("termID")) {
            Log.d("MentorDetailActivity", "Found intent extras");
            int termID = getIntent().getIntExtra("termID", 0);
            String termName = getIntent().getStringExtra("termName");
            Date termStartDate = (Date) getIntent().getSerializableExtra("termStartDate");
            Date termEndDate = (Date) getIntent().getSerializableExtra("termEndDate");

            ListItemTerm term = new ListItemTerm(termID, termName, termStartDate, termEndDate);
            setTerm(term);
            return term;
        } else {
            ListItemTerm term = new ListItemTerm(0, "default", new Date(2018, 1, 1), new Date(2018, 1, 1));
            setTerm(term);
            return term;
        }
    }

    private void setTerm(ListItemTerm term){
        TextView termName = findViewById(R.id.term_detail_term_name);
        termName.setText(term.getTermName());

        String pattern = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        TextView termStartDate = findViewById(R.id.term_detail_term_start);
        Date startDate = term.getTermStartDate();
        String startDateText = simpleDateFormat.format(startDate);
        termStartDate.setText(startDateText);

        TextView termEndDate = findViewById(R.id.term_detail_term_end);
        Date endDate = term.getTermEndDate();
        String endDateText = simpleDateFormat.format(endDate);
        termEndDate.setText(endDateText);

    }

    private List<ListItemCourse> coursesInTerm(List<ListItemCourse> courses) {
        ListItemTerm termToCompare = term;

        List<ListItemCourse> coursesInTerm = new ArrayList<ListItemCourse>();

        if(courses != null) {
            for(int i = 0; i < courses.size(); i++) {
                if(courses.get(i).getCourseTermID() == term.getTermID()) {
                    coursesInTerm.add(courses.get(i));
                }
            }
        }
        return coursesInTerm;
    }
}
