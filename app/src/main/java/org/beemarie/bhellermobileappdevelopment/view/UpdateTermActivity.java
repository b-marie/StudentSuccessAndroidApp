package org.beemarie.bhellermobileappdevelopment.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.AppDatabase;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateTermActivity extends AppCompatActivity {

    EditText termName;
    EditText termStartDate;
    EditText termEndDate;
    Button saveButton;
    Button deleteButton;
    Context context;
    AppDatabase db;
    Calendar mCalendar;
    ListItemTerm term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_term);

        db = AppDatabase.getDatabase(getApplicationContext());

        context = this.getApplicationContext();

        mCalendar = Calendar.getInstance();

        term = getIncomingIntent();
        final int termID = term.getTermID();

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
                new DatePickerDialog(UpdateTermActivity.this, startDate, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
                new DatePickerDialog(UpdateTermActivity.this, endDate, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        saveButton = (Button) findViewById(R.id.update_term_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final int ID = termID;
                if (termName.getText().toString().equals("") && termStartDate.getText().toString().equals("") && termEndDate.getText().toString().equals("")) {
                    //Add a toast to say they need to enter text
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.please_enter_text,
                            Toast.LENGTH_LONG).show();
                } else {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("UpdateTermActivity", "Got to the updating of term");
                            int updateTermID = termID;
                            String updateTermName = termName.getText().toString();
                            String termStart = termStartDate.getText().toString();
                            Date updateTermStartDate = new Date(termStart);
                            String termEnd = termEndDate.getText().toString();
                            Date updateTermEndDate = new Date(termEnd);

                            ListItemTerm updateTerm = new ListItemTerm(updateTermID, updateTermName, updateTermStartDate, updateTermEndDate);
                            db.termDao().updateTerm(updateTerm);
                        }

                    });
                    Intent intent = new Intent(view.getContext(), TermListActivity.class);
                    view.getContext().startActivity(intent);
                }
                finish();
            }

        });

        deleteButton = (Button) findViewById(R.id.update_term_delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            int termId = termID;
            public void onClick(View view) {
                final ListItemTerm deleteTerm = term;
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        int termID = termId;
                        Log.i("UpdateTermActivity", "Got to delete course");
                        db.termDao().deleteByID(termId);
                    }

                });
                Intent intent = new Intent(view.getContext(), TermListActivity.class);
                view.getContext().startActivity(intent);
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

    private ListItemTerm getIncomingIntent() {
        Log.d("TermUpdateActivity", "Checking for incoming intents");
        if (getIntent().hasExtra("termName")) {
            Log.d("TermUpdateActivity", "Found intent extras");
            int termID = getIntent().getIntExtra("termID", 0);
            String termName = getIntent().getStringExtra("termName");
            Date termStart = (Date) getIntent().getSerializableExtra("termStartDate");
            Date termEnd = (Date) getIntent().getSerializableExtra("termEndDate");

            ListItemTerm term = new ListItemTerm(termID, termName, termStart, termEnd);
            setTerm(term);
            return term;
        } else {
            ListItemTerm term = new ListItemTerm(0, "default",
                    new Date(2018, 1, 1), new Date(2018, 1, 1));
            setTerm(term);
            return term;
        }
    }

    private void setTerm(ListItemTerm term) {
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        termName = findViewById(R.id.update_term_term_number_entry);
        termName.setText(term.getTermName());

        termStartDate = findViewById(R.id.update_term_term_start_date_entry);
        Date startDate = term.getTermStartDate();
        String tStartDate = simpleDateFormat.format(startDate);
        termStartDate.setText(tStartDate);

        termEndDate = findViewById(R.id.update_term_term_end_date_entry);
        Date endDate = term.getTermEndDate();
        String tEndDate = simpleDateFormat.format(endDate);
        termEndDate.setText(tEndDate);
    }
}
