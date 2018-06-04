package org.beemarie.bhellermobileappdevelopment.view;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import org.beemarie.bhellermobileappdevelopment.AssessmentAlertReceiver;
import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.AppDatabase;
import org.beemarie.bhellermobileappdevelopment.data.ListItemAssessment;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class UpdateAssessmentActivity extends AppCompatActivity {

    EditText dueDate;
    EditText assessmentName;
    RadioGroup assessmentTypeRadioGroup;
    RadioButton preAssessmentTypeSelection;
    RadioButton objectiveAssessmentTypeSelection;
    RadioButton performanceAssessmentTypeSelection;
    Calendar mCalendar;
    Button saveButton;
    Button deleteButton;
    RadioButton typeButton;
    AppDatabase db;
    Spinner courseDropdown;
    List<ListItemCourse> courses;
    Context context;
    Switch assessmentReminderSwitch;

    NotificationManager notificationManager;
    int assessmentDueNotificationID = 1;
    boolean assessmentDueNotificationActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_assessment);

        context = getApplicationContext();

        db = AppDatabase.getDatabase(getApplicationContext());

        mCalendar = Calendar.getInstance();

        populateCourseList();

        final ListItemAssessment assessment = getIncomingIntent();
        final int assessmentID = assessment.getAssessmentID();

        dueDate = (EditText) findViewById(R.id.update_assessment_due_date_entry);
        saveButton = (Button) findViewById(R.id.update_assessment_save_button);
        deleteButton = (Button) findViewById(R.id.update_assessment_delete_button);
        assessmentName = findViewById(R.id.update_assessment_name_entry);
        assessmentTypeRadioGroup = (RadioGroup) findViewById(R.id.update_assessment_type_group);
        preAssessmentTypeSelection = (RadioButton) findViewById(R.id.update_assessment_pre_assessment_entry);
        objectiveAssessmentTypeSelection = (RadioButton) findViewById(R.id.update_assessment_objective_assessment_entry);
        performanceAssessmentTypeSelection = (RadioButton) findViewById(R.id.update_assessment_performance_assessment_entry);
        courseDropdown = findViewById(R.id.update_assessment_course_list);
        assessmentReminderSwitch = findViewById(R.id.update_assessment_due_date_notification_switch);

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
                new DatePickerDialog(UpdateAssessmentActivity.this, date, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                final int ID = assessmentID;
                if (assessmentName.getText().toString().equals("") || dueDate.getText().toString().equals("") || assessmentTypeRadioGroup.getCheckedRadioButtonId() == -1) {
                    //Add a toast to say they need to enter text
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.please_enter_text,
                            Toast.LENGTH_LONG).show();
                } else {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("UpdateAssessActivity", "Got to the updating of assessment");
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

                            boolean assessmentReminderOn = assessmentReminderSwitch.isChecked();

                            ListItemAssessment assessment = new ListItemAssessment(ID, newAssessmentName,
                                    assessmentType, newAssessmentDueDate, assessmentCourseID, assessmentReminderOn);

                            if(assessment.getAssessmentDueDateNotification()) {
                                setAssessmentDueAlarm(view, assessment);
                            } else {
                                assessmentDueNotificationOff(view, assessment);
                            }

                            db.assessmentDao().updateAssessment(assessment);
                        }

                    });
                    Intent intent = new Intent(view.getContext(), AssessmentListActivity.class);
                    view.getContext().startActivity(intent);
                }
                finish();
            }

        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final ListItemAssessment deleteAssessment = assessment;
                if (assessmentName.getText().toString().equals("")) {
                    //Add a toast to say they need to enter text
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.please_enter_text,
                            Toast.LENGTH_LONG).show();
                } else {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("UpdateAssessActivity", "Got to delete assessment");
                            db.assessmentDao().deleteAssessment(assessment);
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

    private ListItemAssessment getIncomingIntent(){
        Log.d("UpdateAssessActivity", "Checking for incoming intents");
        if(getIntent().hasExtra("assessmentName") && getIntent().hasExtra("assessmentName")) {
            Log.d("UpdateAssessActivity", "Found intent extras");
            int assessmentID = getIntent().getIntExtra("assessmentID", 0);
            String assessmentName = getIntent().getStringExtra("assessmentName");
            String assessmentType = getIntent().getStringExtra("assessmentType");
            Date assessmentDueDate = (Date) getIntent().getSerializableExtra("assessmentDueDate");
            int assessmentCourseID = getIntent().getIntExtra("assessmentCourseID", 0);
            boolean assessmentReminder = getIntent().getBooleanExtra("assessmentReminder", false);


            ListItemAssessment assessment = new ListItemAssessment(assessmentID, assessmentName,
                    assessmentType, assessmentDueDate, assessmentCourseID, assessmentReminder);
            setAssessment(assessment);
            return assessment;
        } else {
            ListItemAssessment assessment = new ListItemAssessment(0, "default",
                    "default", new Date(2018, 1, 1), 0, false);
            setAssessment(assessment);
            return assessment;
        }
    }

    private void setAssessment(ListItemAssessment assessment){
        EditText assessmentName = findViewById(R.id.update_assessment_name_entry);
        assessmentName.setText(assessment.getAssessmentName());

        EditText assessmentDueDate = findViewById(R.id.update_assessment_due_date_entry);
        Date date = assessment.getAssessmentDueDate();
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dueDate = simpleDateFormat.format(date);
        assessmentDueDate.setText(dueDate);

        Switch assessmentReminderSwitch = findViewById(R.id.update_assessment_due_date_notification_switch);
        if(assessment.getAssessmentDueDateNotification() == true) {
            assessmentReminderSwitch.setChecked(true);
        }
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
                    Spinner courseDropdown = findViewById(R.id.update_assessment_course_list);
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

    public void assessmentDueNotificationOn(View view, ListItemAssessment assessment) {

        NotificationCompat.Builder notificBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Assessment is due!")
                .setContentText("The " + assessment.getAssessmentName() + " assessment is due.")
                .setTicker("Alert")
                .setSmallIcon(R.drawable.icon_launcher_background);

        Intent moreInfoIntent = new Intent(this, AssessmentListActivity.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(AssessmentListActivity.class);
        taskStackBuilder.addNextIntent(moreInfoIntent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);

        notificBuilder.setContentIntent(pendingIntent);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(assessmentDueNotificationID, notificBuilder.build());
        assessmentDueNotificationActive = true;
    }

    public void assessmentDueNotificationOff(View view, ListItemAssessment assessment) {
        if(assessmentDueNotificationActive) {
            notificationManager.cancel(assessmentDueNotificationID);
        }
    }

    public void setAssessmentDueAlarm(View view, ListItemAssessment assessment) {
        String assessmentName = assessment.getAssessmentName();
        Date assessmentDueDate = assessment.getAssessmentDueDate();
        int assessmentID = assessment.getAssessmentID();
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(assessmentDueDate);
        Long alertTime = cal.getTimeInMillis();

        Intent alertIntent = new Intent(this, AssessmentAlertReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, PendingIntent.getBroadcast( this,
                1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));
    }
}
