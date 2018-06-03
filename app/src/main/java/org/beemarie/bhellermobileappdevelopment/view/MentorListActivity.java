package org.beemarie.bhellermobileappdevelopment.view;

import android.app.LauncherActivity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.AppDatabase;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;

import java.util.Date;
import java.util.List;

public class MentorListActivity extends AppCompatActivity {

    private RecyclerView mentorRecyclerView;
    private MentorAdapter mentorAdapter;
    Button addMentor;
    Button home;
    Context context;
    private List<ListItemMentor> mentors;
    private MentorViewModel mentorViewModel;
    public static final int NEW_MENTOR_ACTIVITY_REQUEST_CODE = 1;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_list);

        db = AppDatabase.getDatabase(getApplicationContext());

        context = this.getApplicationContext();


        mentorRecyclerView = (RecyclerView) findViewById(R.id.mentor_list_recycler);
        mentorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mentorAdapter = new MentorAdapter(context, mentors);
        mentorRecyclerView.setAdapter(mentorAdapter);

        //Get the viewmodel
        mentorViewModel = ViewModelProviders.of(this).get(MentorViewModel.class);

        //Add an observer to return mentors
        mentorViewModel.getAllMentors().observe(this, new Observer<List<ListItemMentor>>() {
            @Override
            public void onChanged(@Nullable final List<ListItemMentor> mentors) {
                //Update cached list of mentors in the adapter
                mentorAdapter.setMentors(mentors);
            }
        });

        home = (Button) findViewById(R.id.mentor_list_home_button);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        addMentor = (Button) findViewById(R.id.mentor_list_add_mentor_button);
        addMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MentorListActivity.this, AddNewMentor.class);
                startActivityForResult(intent, NEW_MENTOR_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("MentorListActivity", "Got to onActivityResult");
        final Intent intent = getIntent();

        AsyncTask.execute(new Runnable() {

            @Override
            public void run() {
                Log.i("MentorListActivity", "Got to Runnable");
                if(requestCode == NEW_MENTOR_ACTIVITY_REQUEST_CODE) {
                }

            }
        });




    }


}
