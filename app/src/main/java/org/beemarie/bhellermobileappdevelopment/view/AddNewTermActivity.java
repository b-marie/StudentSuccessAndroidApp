package org.beemarie.bhellermobileappdevelopment.view;

import android.app.DatePickerDialog;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
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
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddNewTermActivity extends AppCompatActivity {
//
//    public static final ListItemTerm EXTRA_TERM = new ListItemTerm();
//    public static final String EXTRA_REPLY = "EXTRA_REPLY";

    private EditText termName;
    private EditText termStartDate;
    private EditText termEndDate;
    Calendar mCalendar;
    AppDatabase db;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_term);

        mCalendar = Calendar.getInstance();

        //Find database
        db = AppDatabase.getDatabase(getApplicationContext());

        //Instantiate XML items
        termName = findViewById(R.id.add_term_term_number_entry);
        termStartDate = findViewById(R.id.add_term_term_start_date_entry);
        termEndDate = findViewById(R.id.add_term_term_end_date_entry);

        //Add start date picker
        final DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStartLabel();
            }
        };

        termStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddNewTermActivity.this, startDate, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Add end date picker
        final DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndLabel();
            }
        };

        termEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddNewTermActivity.this, endDate, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



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
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    try {
                        newTermStartDate = formatter.parse(newTermStartDateString);
                        newTermEndDate = formatter.parse(newTermEndDateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    final ListItemTerm newTerm = new ListItemTerm(newTermName, newTermStartDate, newTermEndDate);

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

    private void updateStartLabel() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        termStartDate.setText(sdf.format(mCalendar.getTime()));
    }

    private void updateEndLabel() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        termEndDate.setText(sdf.format(mCalendar.getTime()));
    }

}
