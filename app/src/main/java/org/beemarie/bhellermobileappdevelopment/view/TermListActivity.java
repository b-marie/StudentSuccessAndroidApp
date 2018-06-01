package org.beemarie.bhellermobileappdevelopment.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.AppDatabase;
import org.beemarie.bhellermobileappdevelopment.data.DataSourceInterface;
import org.beemarie.bhellermobileappdevelopment.data.ListItemAssessment;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;
import org.beemarie.bhellermobileappdevelopment.logic.RecyclerItemClickListener;
import org.beemarie.bhellermobileappdevelopment.logic.TermController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TermListActivity extends AppCompatActivity {

    private List<ListItemTerm> terms;
    int NEW_TERM_ACTIVITY_REQUEST_CODE = 1;
    private RecyclerView termRecyclerView;
    private TermAdapter termAdapter;
    Button addTerm;
    Button home;
    Context context;
    private TermViewModel termViewModel;
    AppDatabase db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        db = AppDatabase.getDatabase(getApplicationContext());

        context = this.getApplicationContext();

        termRecyclerView = (RecyclerView) findViewById(R.id.term_list_recycler);
        termRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter = new TermAdapter(context, terms);
        termRecyclerView.setAdapter(termAdapter);

        //Get the viewmodel
        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);

        //Add an observer to return mentors
        termViewModel.getAllTerms().observe(this, new Observer<List<ListItemTerm>>() {
            @Override
            public void onChanged(@Nullable final List<ListItemTerm> terms) {
                //Update cached list of mentors in the adapter
                termAdapter.setTerms(terms);
            }
        });

        home = (Button) findViewById(R.id.term_list_home_button);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        addTerm = (Button) findViewById(R.id.term_list_add_term_button);
        addTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddNewTermActivity.class);
                view.getContext().startActivity(intent);
                finish();
            }
        });


    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Intent intent = getIntent();

        AsyncTask.execute(new Runnable() {

            @Override
            public void run() {
                if(requestCode == NEW_TERM_ACTIVITY_REQUEST_CODE) {
                }

            }
        });




    }

}
