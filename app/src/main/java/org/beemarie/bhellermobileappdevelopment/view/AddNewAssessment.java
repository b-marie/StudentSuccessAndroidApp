package org.beemarie.bhellermobileappdevelopment.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.AppDatabase;
import org.beemarie.bhellermobileappdevelopment.data.ListItemAssessment;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddNewAssessment extends AppCompatActivity {

    EditText dueDate;
    EditText assessmentName;
    RadioGroup assessmentTypeRadioGroup;
    RadioButton preAssessmentTypeSelection;
    RadioButton objectiveAssessmentTypeSelection;
    RadioButton performanceAssessmentTypeSelection;
    Calendar mCalendar;
    Button saveButton;
    RadioButton typeButton;
    AppDatabase db;
    List<ListItemCourse> courses;
    Context context;
    Spinner courseDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_assessment);

        db = AppDatabase.getDatabase(getApplicationContext());
        context = getApplicationContext();

        mCalendar = Calendar.getInstance();

        populateCourseList();

        dueDate = (EditText) findViewById(R.id.add_assessment_due_date_entry);
        saveButton = (Button) findViewById(R.id.add_assessment_save_button);
        assessmentName = findViewById(R.id.add_assessment_name_entry);
        assessmentTypeRadioGroup = (RadioGroup) findViewById(R.id.add_assessment_type_group);
        preAssessmentTypeSelection = (RadioButton) findViewById(R.id.add_assessment_pre_assessment_entry);
        objectiveAssessmentTypeSelection = (RadioButton) findViewById(R.id.add_assessment_objective_assessment_entry);
        performanceAssessmentTypeSelection = (RadioButton) findViewById(R.id.add_assessment_performance_assessment_entry);
        final Spinner courseDropdown = findViewById(R.id.add_assessment_course_list);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddNewAssessment.this, date, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                        final Intent replyIntent = new Intent(getApplicationContext(), MentorListActivity.class);
                if (assessmentName.getText().toString().equals("") && dueDate.getText().toString().equals("")) {
                    //Add a toast to say they need to enter text
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.please_enter_text,
                            Toast.LENGTH_LONG).show();
                } else {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("AddNewAssessment", "Got to the adding of assessment");
                            String newAssessmentName = assessmentName.getText().toString();
                            String assessmentDueDate = dueDate.getText().toString();
                            Date newAssessmentDueDate = new Date(assessmentDueDate);
                            int radioID = assessmentTypeRadioGroup.getCheckedRadioButtonId();
                            typeButton = (RadioButton) findViewById(radioID);
                            String assessmentType = typeButton.getText().toString();
                            List<ListItemCourse> courseList = db.courseDao().getAllCoursesArrayList();
                            int i = courseDropdown.getSelectedItemPosition();
                            ListItemCourse courseToAdd = courseList.get(i);
                            int assessmentCourseID = courseToAdd.getCourseID();

                            ListItemAssessment assessment = new ListItemAssessment(newAssessmentName, assessmentType, newAssessmentDueDate, assessmentCourseID);
                            db.assessmentDao().insert(assessment);
                        }

                    });
                    Intent intent = new Intent(view.getContext(), AssessmentListActivity.class);
                    view.getContext().startActivity(intent);

                }
                finish();
            }

        });


    }

    private void updateLabel() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        dueDate.setText(sdf.format(mCalendar.getTime()));
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
                    Spinner courseDropdown = findViewById(R.id.add_assessment_course_list);
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
