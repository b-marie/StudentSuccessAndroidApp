package org.beemarie.bhellermobileappdevelopment.view;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_list);
        new GetData().execute();

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
                Intent intent = new Intent(view.getContext(), AddNewMentor.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    private class GetData extends AsyncTask<Void, Void, List<ListItemMentor>> {

        @Override
        protected List<ListItemMentor> doInBackground(Void... params) {
            AppDatabase db = Room.databaseBuilder(MentorListActivity.this, AppDatabase.class, "database").build();
//            db.termDao().deleteAllTerms();

            ListItemMentor mentor = new ListItemMentor("Bob Ross", "541-220-2330", "Bob.Ross@wgu.edu");

            db.mentorDao().insert(mentor);
            List<ListItemMentor> mentors = db.mentorDao().getAllMentors();
            return mentors;
        }

        @Override
        protected void onPostExecute(List<ListItemMentor> mentors) {
            super.onPostExecute(mentors);
            loadRecyclerView(mentors);
        }
    }

    private void loadRecyclerView(List<ListItemMentor> mentors) {
        mentorRecyclerView = (RecyclerView) findViewById(R.id.mentor_list_recycler);
        mentorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mentorAdapter = new MentorAdapter(context, mentors);
        mentorRecyclerView.setAdapter(mentorAdapter);
    }
}
