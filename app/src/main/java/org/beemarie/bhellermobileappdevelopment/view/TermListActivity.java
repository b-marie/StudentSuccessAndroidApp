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
//    private static final String EXTRA_TERM_NAME = "EXTRA_TERM_NAME";
//    private static final String EXTRA_COURSE_LIST = "EXTRA_COURSE_LIST";
//
//
    private List<ListItemTerm> terms;

//    private LayoutInflater layoutInflater;
    private RecyclerView termRecyclerView;
    private TermAdapter termAdapter;
    Button addTerm;
    Button home;
    Context context;
    private TermViewModel termViewModel;

//    private TermController controller;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        new GetData().execute();

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

//        termViewModel.getAllTerms().observe(this, new Observer<List<ListItemTerm>>() {
//            @Override
//            public void onChanged(@Nullable final List<ListItemTerm> terms) {
//                // Update the cached copy of the words in the adapter.
//                termAdapter.setTerms(terms);
//            }
//        });

//        termRecyclerView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                int termID = terms.get(position).getTermID();
////                Log.d("MyTag", "Term ID is " + termID);
////                Intent intent = new Intent(view.getContext(), TermDetailActivity.class);
////                intent.putExtra("term_id", termID);
////                startActivity(intent);
////            }
////        });
//
//            termRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this.getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                int termID = terms.get(position).getTermID();
//                Intent intent = new Intent(view.getContext(), TermDetailActivity.class);
//                intent.putExtra("term_id", termID);
//                startActivity(intent);
//            }
//        }));

    }


    private class GetData extends AsyncTask<Void, Void, List<ListItemTerm>> {

        @Override
        protected List<ListItemTerm> doInBackground(Void... params) {
            AppDatabase db = Room.databaseBuilder(TermListActivity.this, AppDatabase.class, "database").build();
//            db.termDao().deleteAllTerms();

            ListItemTerm term = new ListItemTerm("Term Two", new Date(2018, 1, 1), new Date(2018, 6, 30));

            db.termDao().insert(term);
            List<ListItemTerm> terms = db.termDao().getAllTerms();
            return terms;
        }

        @Override
        protected void onPostExecute(List<ListItemTerm> terms) {
            super.onPostExecute(terms);
            loadRecyclerView(terms);
        }
    }

    private void loadRecyclerView(List<ListItemTerm> terms) {
        termRecyclerView = (RecyclerView) findViewById(R.id.rec_list_term_activity);
        termRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter = new TermAdapter(context, terms);
        termRecyclerView.setAdapter(termAdapter);
    }

//    private void registerCallClickBack() {
//
//        termRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this.getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                int termID = terms.get(position).getTermID();
//                Log.d("MyTag", "Term ID is " + termID);
//                Intent intent = new Intent(view.getContext(), TermDetailActivity.class);
//                intent.putExtra("term_id", termID);
//                startActivity(intent);
//            }
//        }));
//    }
}
