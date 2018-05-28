package org.beemarie.bhellermobileappdevelopment.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;

import java.util.List;

public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.ViewHolder> {
    public static final String TAG = "RecyclerViewAdapter";
    List<ListItemMentor> mentors;
    private Context context;
    MentorAdapter.ViewHolder viewHolder;

    public MentorAdapter(Context context, List<ListItemMentor> listOfMentors) {
        this.context = context;
        this.mentors = listOfMentors;
    }

    @NonNull
    @Override
    public MentorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mentor, parent, false);
        final MentorAdapter.ViewHolder viewHolder = new MentorAdapter.ViewHolder(view);
        context = parent.getContext();
        return new MentorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MentorAdapter.ViewHolder holder, final int position) {
        holder.mentorName.setText(mentors.get(position).getMentorName());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TermDetailActivity.class);
                intent.putExtra("term_id", mentors.get(position).getMentorID());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mentors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mentorName;
        RelativeLayout parentLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            mentorName = itemView.findViewById(R.id.mentor_title);
            parentLayout = itemView.findViewById(R.id.mentor_item);
        }
    }
}
