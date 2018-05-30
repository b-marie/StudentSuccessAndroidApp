package org.beemarie.bhellermobileappdevelopment.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.AppDatabase;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;

public class UpdateMentorActivity extends AppCompatActivity {

    private EditText mentorName;
    private EditText mentorPhoneNumber;
    private EditText mentorEmail;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mentor);
        db = AppDatabase.getDatabase(getApplicationContext());

        final ListItemMentor mentor = getIncomingIntent();
        final int mentorID = mentor.getMentorID();


        Button updateButton;
        Button deleteButton;

        updateButton = (Button) findViewById(R.id.update_mentor_save_button);
        deleteButton = (Button) findViewById(R.id.update_mentor_delete_button);
        mentorName = findViewById(R.id.update_mentor_name_entry);
        mentorPhoneNumber = findViewById(R.id.update_mentor_phone_number_entry);
        mentorEmail = findViewById(R.id.update_mentor_email_entry);


        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final int ID = mentorID;
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
                            Log.i("UpdateMentorActivity", "Got to the updating of mentor");
                            String updatedMentorName = mentorName.getText().toString();
                            String updatedMentorPhoneNumber = mentorPhoneNumber.getText().toString();
                            String updatedMentorEmail = mentorEmail.getText().toString();
                            ListItemMentor mentor = new ListItemMentor(ID, updatedMentorName, updatedMentorPhoneNumber, updatedMentorEmail);
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
            String mentorName = getIntent().getStringExtra("mentorName");
            String mentorPhoneNumber = getIntent().getStringExtra("mentorPhoneNumber");
            String mentorEmail = getIntent().getStringExtra("mentorEmail");
            int mentorID = getIntent().getIntExtra("mentorID", 0);


            ListItemMentor mentor = new ListItemMentor(mentorID, mentorName, mentorPhoneNumber, mentorEmail);
            setMentor(mentor);
            return mentor;
        } else {
            ListItemMentor mentor = new ListItemMentor(0, "default", "default", "default");
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
}
