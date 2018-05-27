package org.beemarie.bhellermobileappdevelopment.view;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.AppDatabase;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddNewTermActivity extends AppCompatActivity {
//
//    public static final ListItemTerm EXTRA_TERM = new ListItemTerm();
//    public static final String EXTRA_REPLY = "EXTRA_REPLY";

    private EditText termName;
    private EditText termStartDate;
    private EditText termEndDate;
    private RecyclerView courseListRecyclerView;
//    private LayoutInflater layoutInflater;
    private CourseAdapter courseAdapter;
    AppDatabase db;

    private List<ListItemCourse> listOfCourses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_term);
        new GetData().execute();

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production").build();

        termName = findViewById(R.id.add_term_term_number_entry);
        termStartDate = findViewById(R.id.add_term_term_start_date_entry);
        termEndDate = findViewById(R.id.add_term_term_end_date_entry);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production").build();


//        setUpAdapterAndView(listOfCourses);
//
//        courseListRecyclerView = (RecyclerView) findViewById(R.id.add_term_course_recycler_view);
//        courseListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        courseAdapter = new CourseAdapter(listOfCourses);
//        courseListRecyclerView.setAdapter(courseAdapter);


        final Button saveButton = findViewById(R.id.add_term_save_button);
        saveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent replyIntent = new Intent();
                if(!termName.getText().toString().equals("") && !termStartDate.getText().toString().equals("") && !termEndDate.getText().toString().equals("")){
                    String newTermName = termName.getText().toString();
                    String newTermStartDateString = termStartDate.getText().toString();
                    String newTermEndDateString = termEndDate.getText().toString();
                    Date newTermStartDate = new Date();
                    Date newTermEndDate = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        newTermStartDate = formatter.parse(newTermStartDateString);
                        newTermEndDate = formatter.parse(newTermEndDateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    final ListItemTerm newTerm = new ListItemTerm();
                    newTerm.setTermName(newTermName);
                    newTerm.setTermStartDate(newTermStartDate);
                    newTerm.setTermEndDate(newTermEndDate);

                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            db.termDao().insert(newTerm);
                        }
                    });

                    Intent i = new Intent(AddNewTermActivity.this, TermListActivity.class);
                    startActivity(i);
                    finish();

                }
                finish();
            }
        });
    }


    private class GetData extends AsyncTask<Void, Void, List<ListItemCourse>> {

        @Override
        protected List<ListItemCourse> doInBackground(Void... params) {
            AppDatabase db = Room.databaseBuilder(AddNewTermActivity.this, AppDatabase.class, "database").build();

            List<ListItemCourse> courses = db.courseDao().getAllCourses();
            return courses;
        }

        @Override
        protected void onPostExecute(List<ListItemCourse> courses) {
            super.onPostExecute(courses);
            loadRecyclerView(courses);
        }
    }

    private void loadRecyclerView(List<ListItemCourse> courses) {
        courseListRecyclerView = (RecyclerView) findViewById(R.id.add_term_course_recycler_view);
        courseListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter = new CourseAdapter(courses);
        courseListRecyclerView.setAdapter(courseAdapter);
    }

//    public void startAddTermActivity(int ID, String name, Date start, Date end, List<ListItemCourse> courses, View view) {
//        Intent i = new Intent(this, AddNewTermActivity.class);
////        i.putExtra(EXTRA_TERM_NAME, name);
//
//        startActivity(i);
//    }
//
//
//    public void setUpAdapterAndView(List<ListItemCourse> listOfCourses) {
//        this.listOfCourses = listOfCourses;
//        courseListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        courseAdapter = new CourseAdapter(this.listOfCourses);
//        courseListRecyclerView.setAdapter(courseAdapter);
//
//    }

//
//    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
//
//        @NonNull
//        @Override
//        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View v = layoutInflater.inflate(R.layout.item_course, parent, false);
//
//            return new CustomAdapter.CustomViewHolder(v);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull CustomAdapter.CustomViewHolder holder, int position) {
//            ListItemCourse currentCourse = listOfCourses.get(position);
//            ListAdapter courseList = (ListAdapter) currentCourse.getCourseID();
//
//
//            holder.termText.setText(currentCourse.getCourseName());
//            holder.courseList.setAdapter(courseList);
//        }
//
//        @Override
//        public int getItemCount() {
//            //Helps adapter decide how many items it needs to manage
//            return listOfCourses.size();
//        }
//
//        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//            private TextView termText;
//            private ListView courseList;
//
//            public CustomViewHolder(View itemView) {
//                super(itemView);
//
//                this.termText = (TextView) itemView.findViewById(R.id.term_title);
//                this.courseList = (ListView) itemView.findViewById(R.id.term_course_list);
//
//                this.termText.setOnClickListener(this);
//
//            }
//
//            @Override
//            public void onClick(View v) {
//                ListItemCourse listItemCourse = listOfCourses.get(
//                        this.getAdapterPosition()
//                );
//
//                controller.onListItemTermClick(listItemCourse, v
//                );
//
//            }
//        }
//
//
//    }
}
