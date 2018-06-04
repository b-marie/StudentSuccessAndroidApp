package org.beemarie.bhellermobileappdevelopment.view;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
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

import org.beemarie.bhellermobileappdevelopment.CourseAlertReceiver;
import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.AppDatabase;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class AddNewCourse extends AppCompatActivity {

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
    Button saveButton;
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
        setContentView(R.layout.activity_add_new_course);

        db = AppDatabase.getDatabase(getApplicationContext());

        context = this.getApplicationContext();

        mCalendar = Calendar.getInstance();

        populateTermList();


        courseStart = findViewById(R.id.add_course_start_date_entry);
        courseEnd = findViewById(R.id.add_course_end_date_entry);
        courseName = findViewById(R.id.add_course_name_entry);
        courseStatusGroup = (RadioGroup) findViewById(R.id.add_course_course_status_radio_group);
        courseStatusPlanned = (RadioButton) findViewById(R.id.add_course_planned_status);
        courseStatusInProgress = (RadioButton) findViewById(R.id.add_course_in_progress_status);
        courseStatusCompleted = (RadioButton) findViewById(R.id.add_course_completed_status);
        saveButton = (Button) findViewById(R.id.add_course_save_button);
        courseStartDateReminder = (Switch) findViewById(R.id.add_course_start_date_toggle);
        courseEndDateReminder = (Switch) findViewById(R.id.add_course_end_date_toggle);

        final Spinner termDropdown = findViewById(R.id.add_course_term_list);

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
                new DatePickerDialog(AddNewCourse.this, startDate, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
                new DatePickerDialog(AddNewCourse.this, endDate, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
//                        final Intent replyIntent = new Intent(getApplicationContext(), MentorListActivity.class);
                if (courseName.getText().toString().equals("") && courseStart.getText().toString().equals("") && courseEnd.getText().toString().equals("")) {
                    //Add a toast to say they need to enter text
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.please_enter_text,
                            Toast.LENGTH_LONG).show();
                } else {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("AddNewCourse", "Got to the adding of course");
                            String newCourseName = courseName.getText().toString();
                            String courseStartDate = courseStart.getText().toString();
                            Date newCourseStartDate = new Date(courseStartDate);
                            String courseEndDate = courseEnd.getText().toString();
                            Date newCourseEndDate = new Date(courseEndDate);
                            int statusID = courseStatusGroup.getCheckedRadioButtonId();
                            statusButton = (RadioButton) findViewById(statusID);
                            String courseStatus = statusButton.getText().toString();
                            String courseNotes = "";

                            List<ListItemTerm> termList = db.termDao().getAllTermsArrayList();
                            int i = termDropdown.getSelectedItemPosition();
                            ListItemTerm termToAdd = termList.get(i);
                            int courseTermID = termToAdd.getTermID();

                            boolean startDateReminderOn = courseStartDateReminder.isChecked();
                            boolean endDateReminderOn = courseEndDateReminder.isChecked();


                            ListItemCourse course = new ListItemCourse(newCourseName, newCourseStartDate,
                                    newCourseEndDate, courseStatus, courseNotes, courseTermID,
                                    startDateReminderOn, endDateReminderOn);
                            if(startDateReminderOn) {
                                setCourseStartAlarm(view, course);
                            }

                            if(endDateReminderOn) {
                                setCourseEndAlarm(view, course);
                            }

                            db.courseDao().insert(course);
                        }

                    });
                    Intent intent = new Intent(view.getContext(), CourseListActivity.class);
                    view.getContext().startActivity(intent);

                }
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

    private void populateTermList() {
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
                    Spinner termDropdown = findViewById(R.id.add_course_term_list);
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
