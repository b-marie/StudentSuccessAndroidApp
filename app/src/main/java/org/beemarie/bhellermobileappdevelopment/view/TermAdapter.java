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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;
import org.beemarie.bhellermobileappdevelopment.logic.ItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {

    public static final String TAG = "RecyclerViewAdapter";
    List<ListItemTerm> terms;
//    private ItemClickListener itemClickListener;
    private Context context;
    ViewHolder viewHolder;

    public TermAdapter(Context context, List<ListItemTerm> listOfTerms) {
        this.context = context;
        this.terms = listOfTerms;
    }

    @NonNull
    @Override
    public TermAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_term, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = viewHolder.getAdapterPosition();
//                Toast.makeText(context, "Long Click: " + terms.get(position), Toast.LENGTH_SHORT).show();
//            }
//        });

        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String pattern = "d MMM yyyy";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);
        holder.termName.setText(terms.get(position).getTermName());
        String startDate = dateFormatter.format(terms.get(position).getTermStartDate());
        holder.termStart.setText(startDate);
        String endDate = dateFormatter.format(terms.get(position).getTermEndDate());
        holder.termEnd.setText(endDate);
//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position, boolean isLongClick) {
//                if(isLongClick)
//                    Toast.makeText(context, "Long Click: " + terms.get(position), Toast.LENGTH_SHORT).show();
//            }
//        });

//        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                Toast.makeText(context, terms.get(position), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, TermDetailActivity.class);
//                intent.putExtra("term_id", terms.get(position).getTermID());
//                context.startActivity(intent);
//            }
//        });

    }

    void setTerms(List<ListItemTerm> terms){
        mWords = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

//    @Override
//    public void onClick(View v) {
//        viewHolder = new ViewHolder(v);
//        int position = viewHolder.getAdapterPosition();
//        String tag = "tag";
//        String message = "This is the position " + position;
//        Log.d(tag, message);
//
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView termName;
        public TextView termStart;
        public TextView termEnd;
        RelativeLayout parentLayout;


        public ViewHolder(View itemView) {
            super(itemView);
//            itemView.setOnClickListener((View.OnClickListener) this);
            termName = itemView.findViewById(R.id.term_title);
            termStart = itemView.findViewById(R.id.term_start_date);
            termEnd = itemView.findViewById(R.id.term_end_date);
            parentLayout = itemView.findViewById(R.id.root_list_term_activity);
        }
    }

//    public void setItemClickListener(ItemClickListener itemClickListener){
//        this.itemClickListener = itemClickListener;
//    }
//
//    @Override
//    public void onClick(View v){
//        itemClickListener.onClick(v, viewHolder.getAdapterPosition(), false);
//    }

}
