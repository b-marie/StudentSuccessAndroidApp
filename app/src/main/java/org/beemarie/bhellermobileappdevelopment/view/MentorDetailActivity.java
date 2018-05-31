package org.beemarie.bhellermobileappdevelopment.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.AppDatabase;
import org.beemarie.bhellermobileappdevelopment.data.ListItemAssessment;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MentorDetailActivity extends AppCompatActivity {

    TextView mentorName;
    TextView mentorEmail;
    TextView mentorPhoneNumber;
    Button editButton;
    Button homeButton;
    Context context;
    MentorAdapter mentorAdapter;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_detail);

        db = AppDatabase.getDatabase(getApplicationContext());

        context = this.getApplicationContext();

        final ListItemMentor mentor = getIncomingIntent();

        mentorName = findViewById(R.id.mentor_detail_mentor_name);
        mentorPhoneNumber = findViewById(R.id.mentor_detail_phone_number);
        mentorEmail = findViewById(R.id.mentor_detail_email);


        homeButton = (Button) findViewById(R.id.mentor_detail_home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        editButton = (Button) findViewById(R.id.mentor_detail_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateMentorActivity.class);
                intent.putExtra("mentorName", mentor.getMentorName());
                intent.putExtra("mentorPhoneNumber", mentor.getMentorPhoneNumber());
                intent.putExtra("mentorEmail", mentor.getMentorEmail());
                intent.putExtra("mentorID", mentor.getMentorID());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private ListItemMentor getIncomingIntent(){
        Log.d("MentorDetailActivity", "Checking for incoming intents");
        if(getIntent().hasExtra("mentorName") && getIntent().hasExtra("mentorEmail")) {
            Log.d("MentorDetailActivity", "Found intent extras");
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
        TextView mentorName = findViewById(R.id.mentor_detail_mentor_name);
        mentorName.setText(mentor.getMentorName());

        TextView mentorPhoneNumber = findViewById(R.id.mentor_detail_phone_number);
        mentorPhoneNumber.setText(mentor.getMentorPhoneNumber());

        TextView mentorEmail = findViewById(R.id.mentor_detail_email);
        mentorEmail.setText(mentor.getMentorEmail());

    }
}
