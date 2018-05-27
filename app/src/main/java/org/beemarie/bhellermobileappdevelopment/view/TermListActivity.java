package org.beemarie.bhellermobileappdevelopment.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.DataSourceInterface;
import org.beemarie.bhellermobileappdevelopment.data.ListItemAssessment;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;
import org.beemarie.bhellermobileappdevelopment.logic.TermController;

import java.util.Date;
import java.util.List;

public class TermListActivity extends AppCompatActivity implements TermViewInterface {
    private static final String EXTRA_TERM_NAME = "EXTRA_TERM_NAME";
    private static final String EXTRA_COURSE_LIST = "EXTRA_COURSE_LIST";


    private List<ListItemTerm> listOfTerms;

    private LayoutInflater layoutInflater;
    private RecyclerView termRecyclerView;
    private TermAdapter termAdapter;


    private TermController controller;

    Button addTerm;
    Button home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        termRecyclerView = (RecyclerView) findViewById(R.id.rec_list_term_activity);
        layoutInflater = getLayoutInflater();

//        controller = new TermController(this, AppDatabase);

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
            }
        });

    }

    @Override
    public void startTermDetailActivity(int ID, String name, Date start, Date end, List<ListItemCourse> courses, View view) {
        Intent i = new Intent(this, TermDetailActivity.class);
        i.putExtra(EXTRA_TERM_NAME, name);

        startActivity(i);
    }

    @Override
    public void setUpAdapterAndView(List<ListItemTerm> listOfTerms) {
        this.listOfTerms = listOfTerms;
        termRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter = new TermAdapter(listOfTerms);
        termRecyclerView.setAdapter(termAdapter);

    }

//    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
//
//        @NonNull
//        @Override
//        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View v = layoutInflater.inflate(R.layout.item_term, parent, false);
//
//            return new CustomViewHolder(v);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
//            ListItemTerm currentTerm = listOfTerms.get(position);
//            ListAdapter courseList = (ListAdapter) currentTerm.getCoursesInTerm();
//
//
//            holder.termText.setText(currentTerm.getTermName());
//            holder.courseList.setAdapter(courseList);
//        }
//
//        @Override
//        public int getItemCount() {
//            //Helps adapter decide how many items it needs to manage
//            return listOfTerms.size();
//        }
//
//        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//            private TextView termText;
//            private ListView courseList;
//
//            public CustomViewHolder(View itemView) {
//                super(itemView);
//
//                this.termText = (TextView) itemView.findViewById(R.id.term_title);
//                this.courseList = (ListView) itemView.findViewById(R.id.term_course_list);
//
//                this.termText.setOnClickListener(this);
//
//            }
//
//            @Override
//            public void onClick(View v) {
//                ListItemTerm listItemTerm = listOfTerms.get(
//                       this.getAdapterPosition()
//                );
//
//                controller.onListItemTermClick(listItemTerm, v
//                );
//
//            }
//        }

//
//    }
}
