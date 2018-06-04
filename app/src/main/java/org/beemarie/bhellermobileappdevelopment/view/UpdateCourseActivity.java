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
import android.widget.TextView;
import android.widget.Toast;

import org.beemarie.bhellermobileappdevelopment.CourseAlertReceiver;
import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.AppDatabase;
import org.beemarie.bhellermobileappdevelopment.data.ListItemAssessment;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class UpdateCourseActivity extends AppCompatActivity {

    EditText courseName;
    EditText courseStart;
    EditText courseEnd;
    Switch courseStartDateReminder;
    Switch courseEndDateReminder;
    RadioGroup courseStatusGroup;
    RadioButton courseStatusPlanned;
    RadioButton courseStatusInProgress;
    RadioButton courseStatusCompleted;
    RadioButton statusButton;
    Button updateButton;
    Button deleteButton;
    Context context;
    AppDatabase db;
    Calendar mCalendar;
    List<ListItemTerm> terms;

    NotificationManager notificationManager;
    int courseStartNotificationID = 1;
    boolean courseStartNotificationActive = false;

    int courseEndNotificationID = 2;
    boolean courseEndNotificationActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);

        db = AppDatabase.getDatabase(getApplicationContext());

        context = this.getApplicationContext();


        final ListItemCourse course = getIncomingIntent();
        final int courseID = course.getCourseID();
        populateTermList(course);

        mCalendar = Calendar.getInstance();

        courseStart = findViewById(R.id.update_course_start_date_entry);
        courseEnd = findViewById(R.id.update_course_end_date_entry);
        courseName = findViewById(R.id.update_course_name_entry);
        courseStatusGroup = (RadioGroup) findViewById(R.id.update_course_course_status_radio_group);
        courseStatusPlanned = (RadioButton) findViewById(R.id.update_course_planned_status);
        courseStatusInProgress = (RadioButton) findViewById(R.id.update_course_in_progress_status);
        courseStatusCompleted = (RadioButton) findViewById(R.id.update_course_completed_status);
        updateButton = (Button) findViewById(R.id.update_course_save_button);
        deleteButton = (Button) findViewById(R.id.update_course_delete_button);
        courseStartDateReminder = (Switch) findViewById(R.id.update_course_start_date_toggle);
        courseEndDateReminder = (Switch) findViewById(R.id.update_course_end_date_toggle);


        final Spinner termDropdown = findViewById(R.id.update_course_term_list);
        final DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStartLabel();
            }
        };

        courseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UpdateCourseActivity.this, startDate, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndLabel();
            }
        };

        courseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UpdateCourseActivity.this, endDate, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                final int ID = courseID;
                if (courseName.getText().toString().equals("") ||
                        courseStart.getText().toString().equals("") ||
                        courseEnd.getText().toString().equals("") ||
                        courseStatusGroup.getCheckedRadioButtonId() == -1) {
                    //Add a toast to say they need to enter text
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.please_enter_text,
                            Toast.LENGTH_LONG).show();
                } else {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("UpdateCourseActivity", "Got to the updating of course");
                            int updateCourseID = courseID;
                            String updateCourseName = courseName.getText().toString();
                            String courseStartDate = courseStart.getText().toString();
                            Date updateCourseStartDate = new Date(courseStartDate);
                            String courseEndDate = courseEnd.getText().toString();
                            Date updateCourseEndDate = new Date(courseEndDate);
                            int statusID = courseStatusGroup.getCheckedRadioButtonId();
                            statusButton = (RadioButton) findViewById(statusID);
                            String updateCourseStatus = statusButton.getText().toString();
                            String courseNotes = course.getCourseNotes();

                            List<ListItemTerm> termList = db.termDao().getAllTermsArrayList();
                            int i = termDropdown.getSelectedItemPosition();
                            ListItemTerm termToAdd = termList.get(i);
                            int courseTermID = termToAdd.getTermID();

                            boolean courseStartReminderOn = courseStartDateReminder.isChecked();
                            boolean courseEndReminderOn = courseEndDateReminder.isChecked();

                            ListItemCourse updateCourse = new ListItemCourse(updateCourseID, updateCourseName,
                                    updateCourseStartDate, updateCourseEndDate, updateCourseStatus, courseNotes,
                                    courseTermID, courseStartReminderOn, courseEndReminderOn);
                            if(updateCourse.getCourseStartNotification()) {
                                setCourseStartAlarm(view, updateCourse);
                            } else {
                                courseStartNotificationOff(view, updateCourse);
                            }

                            if(updateCourse.getCourseEndNotification()) {
                                setCourseEndAlarm(view, updateCourse);
                            } else {
                                courseEndNotificationOff(view, updateCourse);
                            }

                            db.courseDao().updateCourse(updateCourse);
                        }

                    });
                    Intent intent = new Intent(view.getContext(), CourseListActivity.class);
                    view.getContext().startActivity(intent);
                }
                finish();
            }

        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final ListItemCourse deleteCourse = course;
//                        final Intent replyIntent = new Intent(getApplicationContext(), MentorListActivity.class);
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            int courseId = courseID;
                            Log.i("UpdateCourseActivity", "Got to delete course");
                            db.courseDao().deleteByID(courseId);
                        }

                    });
                    Intent intent = new Intent(view.getContext(), CourseListActivity.class);
                    view.getContext().startActivity(intent);
                finish();
            }

        });



    }

    private void updateStartLabel() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        courseStart.setText(sdf.format(mCalendar.getTime()));
    }

    private void updateEndLabel() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        courseEnd.setText(sdf.format(mCalendar.getTime()));
    }

    private ListItemCourse getIncomingIntent() {
        Log.d("CourseDetailActivity", "Checking for incoming intents");
        if (getIntent().hasExtra("courseName")) {
            Log.d("CourseDetailActivity", "Found intent extras");
            int courseID = getIntent().getIntExtra("courseID", 0);
            String courseName = getIntent().getStringExtra("courseName");
            Date courseStartDate = (Date) getIntent().getSerializableExtra("courseStartDate");
            Date courseEndDate = (Date) getIntent().getSerializableExtra("courseEndDate");
            String courseStatus = getIntent().getStringExtra("courseStatus");
            String courseNotes = getIntent().getStringExtra("courseNotes");
            int courseTermID = getIntent().getIntExtra("courseTermID", 0);
            boolean courseStartReminderOn = getIntent().getBooleanExtra("courseStartReminder", false);
            boolean courseEndReminderOn = getIntent().getBooleanExtra("courseEndReminder", false);

            ListItemCourse course = new ListItemCourse(courseID, courseName, courseStartDate, courseEndDate, courseStatus,
                    courseNotes, courseTermID, courseStartReminderOn, courseEndReminderOn);
            setCourse(course);
            return course;
        } else {
            ListItemCourse course = new ListItemCourse(0, "default",
                    new Date(2018, 1, 1), new Date(2018, 1, 1),
                    "default", "default", 0, false, false);
            setCourse(course);
            return course;
        }
    }

    private void setCourse(ListItemCourse course) {
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        EditText courseName = findViewById(R.id.update_course_name_entry);
        courseName.setText(course.getCourseName());

        EditText courseStart = findViewById(R.id.update_course_start_date_entry);
        Date startDate = course.getCourseStartDate();
        String cStartDate = simpleDateFormat.format(startDate);
        courseStart.setText(cStartDate);

        EditText courseEnd = findViewById(R.id.update_course_end_date_entry);
        Date endDate = course.getCourseEndDate();
        String cEndDate = simpleDateFormat.format(endDate);
        courseEnd.setText(cEndDate);

        Switch courseStartDateReminder = findViewById(R.id.update_course_start_date_toggle);
        if(course.getCourseStartNotification()) {
            courseStartDateReminder.setChecked(true);
            courseStartNotificationActive = true;
        }

        Switch courseEndDateReminder = findViewById(R.id.update_course_end_date_toggle);
        if(course.getCourseEndNotification()) {
            courseEndDateReminder.setChecked(true);
            courseEndNotificationActive = true;
        }


        RadioGroup courseStatusGroup = (RadioGroup) findViewById(R.id.update_course_course_status_radio_group);
        RadioButton courseStatusPlanned = (RadioButton) findViewById(R.id.update_course_planned_status);
        RadioButton courseStatusInProgress = (RadioButton) findViewById(R.id.update_course_in_progress_status);
        RadioButton courseStatusCompleted = (RadioButton) findViewById(R.id.update_course_completed_status);
        if(course.getCourseStatus().equals("In Progress")) {
            courseStatusInProgress.setSelected(true);
        } else if (course.getCourseStatus().equals("Planned")) {
            courseStatusPlanned.setSelected(true);
        } else if(course.getCourseStatus().equals("Completed")) {
            courseStatusCompleted.setSelected(true);
        }



    }

    private void populateTermList(ListItemCourse course) {
        final int termID = course.getCourseTermID();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (db.termDao().getAllTermsArrayList().isEmpty()) {
                    return;
                } else {
                    //Populate the dropdown
                    terms = db.termDao().getAllTermsArrayList();
                    ArrayList<String> termNames = new ArrayList<>();
                    for (int i = 0; i < terms.size(); i++) {
                        termNames.add(terms.get(i).getTermName());
                    }
                    Spinner termDropdown = findViewById(R.id.update_course_term_list);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, termNames);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    termDropdown.setAdapter(adapter);
                    termDropdown.setSelection(0);
                    termDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
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


    public void courseStartNotificationOn(View view, ListItemCourse course) {
        if(!courseStartNotificationActive){
            NotificationCompat.Builder notificBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle("Course is starting!")
                    .setContentText("The " + course.getCourseName() + " course is starting.")
                    .setTicker("Alert")
                    .setSmallIcon(R.drawable.icon_launcher_background);

            Intent moreInfoIntent = new Intent(this, CourseListActivity.class);

            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
            taskStackBuilder.addParentStack(CourseListActivity.class);
            taskStackBuilder.addNextIntent(moreInfoIntent);

            PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            notificBuilder.setContentIntent(pendingIntent);

            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(courseStartNotificationID, notificBuilder.build());
            courseStartNotificationActive = true;
        }
    }

    public void courseStartNotificationOff(View view, ListItemCourse course) {
        if(courseStartNotificationActive) {
            notificationManager.cancel(courseStartNotificationID);
        }
    }

    public void setCourseStartAlarm(View view, ListItemCourse course) {
        String courseName = course.getCourseName();
        Date courseStartDate = course.getCourseStartDate();
        int courseID = course.getCourseID();
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(courseStartDate);
        Long alertTime = cal.getTimeInMillis();

        Intent alertIntent = new Intent(this, CourseAlertReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, PendingIntent.getBroadcast( this,
                1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    public void courseEndNotificationOn(View view, ListItemCourse course) {
        if(!courseEndNotificationActive){
            NotificationCompat.Builder notificBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle("Course is ending!")
                    .setContentText("The " + course.getCourseName() + " course is ending.")
                    .setTicker("Alert")
                    .setSmallIcon(R.drawable.icon_launcher_background);

            Intent moreInfoIntent = new Intent(this, CourseListActivity.class);

            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
            taskStackBuilder.addParentStack(CourseListActivity.class);
            taskStackBuilder.addNextIntent(moreInfoIntent);

            PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            notificBuilder.setContentIntent(pendingIntent);

            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(courseEndNotificationID, notificBuilder.build());
            courseEndNotificationActive = true;
        }
    }

    public void courseEndNotificationOff(View view, ListItemCourse course) {
        if(courseEndNotificationActive) {
            notificationManager.cancel(courseEndNotificationID);
        }
    }

    public void setCourseEndAlarm(View view, ListItemCourse course) {
        String courseName = course.getCourseName();
        Date courseEndDate = course.getCourseEndDate();
        int courseID = course.getCourseID();
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(courseEndDate);
        Long alertTime = cal.getTimeInMillis();

        Intent alertIntent = new Intent(this, CourseAlertReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, PendingIntent.getBroadcast( this,
                1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));
    }
}
