package org.beemarie.bhellermobileappdevelopment.view;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.AppDatabase;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNewMentor extends AppCompatActivity {

    private EditText mentorName;
    private EditText mentorPhoneNumber;
    private EditText mentorEmail;
    AppDatabase db;

    Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_mentor);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production").build();

        saveButton = (Button) findViewById(R.id.add_mentor_save_button);
        mentorName = findViewById(R.id.add_mentor_name_entry);
        mentorPhoneNumber = findViewById(R.id.add_mentor_phone_number_entry);
        mentorEmail = findViewById(R.id.add_mentor_email_entry);

        saveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                addNewMentor();
            }
        });

    }

    public void addNewMentor() {


            if(!mentorName.getText().toString().equals("") && !mentorPhoneNumber.getText().toString().equals("") && !mentorEmail.getText().toString().equals("")){
                String newMentorName = mentorName.getText().toString();
                String newMentorPhoneNumber = mentorPhoneNumber.getText().toString();
                String newMentorEmail = mentorEmail.getText().toString();
                final ListItemMentor newMentor = new ListItemMentor(newMentorName, newMentorPhoneNumber, newMentorEmail);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        db.mentorDao().insert(newMentor);
                    }
                });
                Intent i = new Intent(AddNewMentor.this, MentorListActivity.class);
                startActivity(i);
                finish();
            }
            finish();

    }


}
