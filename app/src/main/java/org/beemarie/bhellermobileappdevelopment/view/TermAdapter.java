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

import java.text.SimpleDateFormat;
import java.util.List;

class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {

    List<ListItemTerm> terms;

    public TermAdapter(List<ListItemTerm> listOfTerms) {
        this.terms = listOfTerms;
    }

    @NonNull
    @Override
    public TermAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_term, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String pattern = "d MMM yyyy";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);
        holder.termName.setText(terms.get(position).getTermName());
        String startDate = dateFormatter.format(terms.get(position).getTermStartDate());
        holder.termStart.setText(startDate);
        String endDate = dateFormatter.format(terms.get(position).getTermEndDate());
        holder.termEnd.setText(endDate);


    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView termName;
        public TextView termStart;
        public TextView termEnd;


        public ViewHolder(View itemView) {
            super(itemView);
            termName = itemView.findViewById(R.id.term_title);
            termStart = itemView.findViewById(R.id.term_start_date);
            termEnd = itemView.findViewById(R.id.term_end_date);
        }
    }
}
