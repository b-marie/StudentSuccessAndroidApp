package org.beemarie.bhellermobileappdevelopment.view;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.AppDatabase;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddNewMentor extends AppCompatActivity {

    private EditText mentorName;
    private EditText mentorPhoneNumber;
    private EditText mentorEmail;
    AppDatabase db;
    public static final String EXTRA_MENTOR = "EXTRA_MENTOR";

    Button saveButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_mentor);


        db = AppDatabase.getDatabase(getApplicationContext());

        saveButton = (Button) findViewById(R.id.add_mentor_save_button);
        mentorName = findViewById(R.id.add_mentor_name_entry);
        mentorPhoneNumber = findViewById(R.id.add_mentor_phone_number_entry);
        mentorEmail = findViewById(R.id.add_mentor_email_entry);

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
                                        ListItemMentor mentor = new ListItemMentor(newMentorName, newMentorPhoneNumber, newMentorEmail);
//                                        db.mentorDao().insert(mentor);
                                        Intent intent = getIntent();
//                                        Intent intent = new Intent(AddNewMentor.this, MentorListActivity.class);
                                        intent.putExtra(EXTRA_MENTOR, (Parcelable) mentor);
                                        setResult(RESULT_OK, intent);
                                        finish();
//                                        startActivity(intent);
                                    }

                            });

                        }
                        finish();
                    }

                });



//    public void addNewMentor() {
//            if(!mentorName.getText().toString().equals("") && !mentorPhoneNumber.getText().toString().equals("") && !mentorEmail.getText().toString().equals("")){
//                String newMentorName = mentorName.getText().toString();
//                String newMentorPhoneNumber = mentorPhoneNumber.getText().toString();
//                String newMentorEmail = mentorEmail.getText().toString();
//                final ListItemMentor newMentor = new ListItemMentor(newMentorName, newMentorPhoneNumber, newMentorEmail);
////                AsyncTask.execute(new Runnable() {
////                    @Override
////                    public void run() {
////                        db.mentorDao().insert(newMentor);
////                    }
////                });
//                Intent replyIntent = new Intent();
//                replyIntent.putExtra("EXTRA_MENTOR", (Parcelable) newMentor);
//                setResult(RESULT_OK, replyIntent);
//            }
//            finish();
//
//    }


    }
}
