package org.beemarie.bhellermobileappdevelopment.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.AppDatabase;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;

import java.util.ArrayList;
import java.util.List;

public class UpdateMentorActivity extends AppCompatActivity {

    private EditText mentorName;
    private EditText mentorPhoneNumber;
    private EditText mentorEmail;
    AppDatabase db;
    Button updateButton;
    Button deleteButton;
    List<ListItemCourse> courses;
    Context context;
    Spinner courseDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mentor);

        context = getApplicationContext();
        db = AppDatabase.getDatabase(getApplicationContext());

        populateCourseList();

        final ListItemMentor mentor = getIncomingIntent();
        final int mentorID = mentor.getMentorID();

        updateButton = (Button) findViewById(R.id.update_mentor_save_button);
        deleteButton = (Button) findViewById(R.id.update_mentor_delete_button);
        mentorName = findViewById(R.id.update_mentor_name_entry);
        mentorPhoneNumber = findViewById(R.id.update_mentor_phone_number_entry);
        mentorEmail = findViewById(R.id.update_mentor_email_entry);
        courseDropdown = findViewById(R.id.update_mentor_course_list);


        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final int ID = mentorID;
                if (mentorName.getText().toString().equals("") || mentorPhoneNumber.getText().toString().equals("") || mentorEmail.getText().toString().equals("")) {
                    //Add a toast to say they need to enter text
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.please_enter_text,
                            Toast.LENGTH_LONG).show();
                } else {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("UpdateMentorActivity", "Got to the updating of mentor");
                            String updatedMentorName = mentorName.getText().toString();
                            String updatedMentorPhoneNumber = mentorPhoneNumber.getText().toString();
                            String updatedMentorEmail = mentorEmail.getText().toString();

                            List<ListItemCourse> courseList = db.courseDao().getAllCoursesArrayList();
                            int i = courseDropdown.getSelectedItemPosition();
                            ListItemCourse courseToAdd = courseList.get(i);
                            int mentorCourseID = courseToAdd.getCourseID();

                            ListItemMentor mentor = new ListItemMentor(ID, updatedMentorName, updatedMentorPhoneNumber, updatedMentorEmail, mentorCourseID);
                            db.mentorDao().updateMentor(mentor);
                        }

                    });
                    Intent intent = new Intent(view.getContext(), MentorListActivity.class);
                    view.getContext().startActivity(intent);
                }
                finish();
            }

        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final ListItemMentor deleteMentor = mentor;
//                        final Intent replyIntent = new Intent(getApplicationContext(), MentorListActivity.class);
                if (mentorName.getText().toString().equals("") && mentorPhoneNumber.getText().toString().equals("") && mentorEmail.getText().toString().equals("")) {
                    //Add a toast to say they need to enter text
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.please_enter_text,
                            Toast.LENGTH_LONG).show();
                } else {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("UpdateMentorActivity", "Got to delete mentor");
                            db.mentorDao().deleteMentor(mentor);
                        }

                    });
                    Intent intent = new Intent(view.getContext(), MentorListActivity.class);
                    view.getContext().startActivity(intent);

                }
                finish();
            }

        });

    }

    private ListItemMentor getIncomingIntent(){
        Log.d("UpdateMentorActivity", "Checking for incoming intents");
        if(getIntent().hasExtra("mentorName") && getIntent().hasExtra("mentorPhoneNumber")) {
            Log.d("UpdateMentorActivity", "Found intent extras");
            int mentorID = getIntent().getIntExtra("mentorID", 0);
            String mentorName = getIntent().getStringExtra("mentorName");
            String mentorPhoneNumber = getIntent().getStringExtra("mentorPhoneNumber");
            String mentorEmail = getIntent().getStringExtra("mentorEmail");
            int mentorCourseID = getIntent().getIntExtra("mentorCourseID", 0);



            ListItemMentor mentor = new ListItemMentor(mentorID, mentorName, mentorPhoneNumber, mentorEmail, mentorCourseID);
            setMentor(mentor);
            return mentor;
        } else {
            ListItemMentor mentor = new ListItemMentor(0, "default", "default", "default", 0);
            setMentor(mentor);
            return mentor;
        }
    }

    private void setMentor(ListItemMentor mentor){
        EditText mentorName = findViewById(R.id.update_mentor_name_entry);
        mentorName.setText(mentor.getMentorName());

        EditText mentorPhoneNumber = findViewById(R.id.update_mentor_phone_number_entry);
        mentorPhoneNumber.setText(mentor.getMentorPhoneNumber());

        EditText mentorEmail = findViewById(R.id.update_mentor_email_entry);
        mentorEmail.setText(mentor.getMentorEmail());
    }

    private void populateCourseList() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (db.courseDao().getAllCoursesArrayList().isEmpty()) {
                    return;
                } else {
                    //Populate the dropdown
                    courses = db.courseDao().getAllCoursesArrayList();
                    ArrayList<String> courseNames = new ArrayList<>();
                    for (int i = 0; i < courses.size(); i++) {
                        courseNames.add(courses.get(i).getCourseName());
                    }
                    Spinner courseDropdown = findViewById(R.id.update_mentor_course_list);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, courseNames);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    courseDropdown.setAdapter(adapter);
                    courseDropdown.setSelection(0);
                    courseDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                        public void onItemSelected(AdapterView<?>  parent, View view, int pos, long id){
                            Object item = parent.getItemAtPosition(pos);
                        }
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }
        });
    }
}
