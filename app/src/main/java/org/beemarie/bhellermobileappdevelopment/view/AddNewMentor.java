package org.beemarie.bhellermobileappdevelopment.view;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddNewMentor extends AppCompatActivity {

    private EditText mentorName;
    private EditText mentorPhoneNumber;
    private EditText mentorEmail;
    AppDatabase db;
    public static final String EXTRA_MENTOR = "EXTRA_MENTOR";
    List<ListItemCourse> courses;
    Context context;
    Spinner courseDropdown;

    Button saveButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_mentor);

        context = getApplicationContext();


        db = AppDatabase.getDatabase(getApplicationContext());

        populateCourseList();

        saveButton = (Button) findViewById(R.id.add_mentor_save_button);
        mentorName = findViewById(R.id.add_mentor_name_entry);
        mentorPhoneNumber = findViewById(R.id.add_mentor_phone_number_entry);
        mentorEmail = findViewById(R.id.add_mentor_email_entry);
        final Spinner courseDropdown = findViewById(R.id.add_mentor_course_list);

                saveButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
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
                                        Log.i("AddNewMentor", "Got to the adding of mentor");
                                        String newMentorName = mentorName.getText().toString();
                                        String newMentorPhoneNumber = mentorPhoneNumber.getText().toString();
                                        String newMentorEmail = mentorEmail.getText().toString();

                                        List<ListItemCourse> courseList = db.courseDao().getAllCoursesArrayList();
                                        int i = courseDropdown.getSelectedItemPosition();
                                        ListItemCourse courseToAdd = courseList.get(i);
                                        int mentorCourseID = courseToAdd.getCourseID();


                                        ListItemMentor mentor = new ListItemMentor(newMentorName, newMentorPhoneNumber, newMentorEmail, mentorCourseID);
                                        db.mentorDao().insert(mentor);
                                    }

                            });
                            Intent intent = new Intent(view.getContext(), MentorListActivity.class);
                            view.getContext().startActivity(intent);

                        }
                        finish();
                    }

                });

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
                    Spinner courseDropdown = findViewById(R.id.add_mentor_course_list);
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
