package org.beemarie.bhellermobileappdevelopment.view;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;
import org.beemarie.bhellermobileappdevelopment.logic.ItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    public class TermViewHolder extends RecyclerView.ViewHolder {
        public TextView termName;
        public TextView termStartDate;
        public TextView termEndDate;


        public TermViewHolder(View itemView) {
            super(itemView);
            termName = itemView.findViewById(R.id.term_title);
            termStartDate = itemView.findViewById(R.id.term_start_date);
            termEndDate = itemView.findViewById(R.id.term_end_date);
        }
    }
    public static final String TAG = "RecyclerViewAdapter";
    List<ListItemTerm> terms;
    TermAdapter.TermViewHolder viewHolder;
    View.OnClickListener mClickListener;
    Context context;



    public TermAdapter(Context context, List<ListItemTerm> listOfTerms) {
        this.context = context;
        this.terms = listOfTerms;
    }

    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_term, parent, false);
        TermViewHolder holder = new TermViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, final int position) {
        if(terms != null) {
            ListItemTerm current = terms.get(position);
            String pattern = "MM/dd/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date startDate = current.getTermStartDate();
            String cStartDate = simpleDateFormat.format(startDate);
            Date endDate = current.getTermEndDate();
            String cEndDate = simpleDateFormat.format(endDate);
            holder.termName.setText(current.getTermName());
            holder.termStartDate.setText(cStartDate);
            holder.termEndDate.setText(cEndDate);
        } else {
            holder.termName.setText("No Terms");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TermDetailActivity", "Clicked on " + terms.get(position).getTermName());
                Intent intent = new Intent(context, TermDetailActivity.class);
                intent.putExtra("termID", terms.get(position).getTermID());
                intent.putExtra("termName", terms.get(position).getTermName());
                intent.putExtra("termStartDate", terms.get(position).getTermStartDate());
                intent.putExtra("termEndDate", terms.get(position).getTermEndDate());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        });

    }

    void setTerms(List<ListItemTerm> setTerms) {
        terms = setTerms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(terms != null) {
            return terms.size();
        } else return 0;

    }

}
