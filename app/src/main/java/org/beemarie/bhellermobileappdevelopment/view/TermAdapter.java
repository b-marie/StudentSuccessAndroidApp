package org.beemarie.bhellermobileappdevelopment.view;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;

import java.util.List;

class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {

    List<ListItemTerm> terms;
    private CourseAdapter courseAdapter;
    public RecyclerView courseList;
    LiveData<List<ListItemCourse>> courses;

    public TermAdapter(List<ListItemTerm> listOfTerms) {
        this.terms = listOfTerms;
    }

    @NonNull
    @Override
    public TermAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_term, parent, false);
        return new TermAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.termName.setText(terms.get(position).getTermName());
        holder.termStart.setText(terms.get(position).getTermStartDate().toString());
        holder.termEnd.setText(terms.get(position).getTermEndDate().toString());
        courses = terms.get(position).getCoursesInTerm();

        holder.coursesList.setList(courses);


//        courseList.setLayoutManager(new LinearLayoutManager(holder));
        courseAdapter = new CourseAdapter(courses);
        courseList.setAdapter(courseAdapter);

    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView termName;
        public TextView termStart;
        public TextView termEnd;
        public RecyclerView coursesList;


        public ViewHolder(View itemView) {
            super(itemView);
            termName = itemView.findViewById(R.id.term_title);
            termStart = itemView.findViewById(R.id.term_start_date);
            termEnd = itemView.findViewById(R.id.term_end_date);
            courseList = itemView.findViewById(R.id.term_course_list);
        }
    }
}
