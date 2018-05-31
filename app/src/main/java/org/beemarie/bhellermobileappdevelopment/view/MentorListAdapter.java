package org.beemarie.bhellermobileappdevelopment.view;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;

import java.util.List;

//Adapter used for when Mentor List is in a list with courses
public class MentorListAdapter extends RecyclerView.Adapter<MentorListAdapter.MentorViewHolder> {

    public class MentorViewHolder extends RecyclerView.ViewHolder {
        public TextView mentorName;


        public MentorViewHolder(View itemView) {
            super(itemView);
            mentorName = itemView.findViewById(R.id.mentor_title);
        }
    }
    public static final String TAG = "RecyclerViewAdapter";
    List<ListItemMentor> mentors;
    MentorListAdapter.MentorViewHolder viewHolder;
    View.OnClickListener mClickListener;
    Context context;



    public MentorListAdapter(Context context, List<ListItemMentor> listOfMentors) {
        this.context = context;
//        LayoutInflater inflater = LayoutInflater.from(context);
        this.mentors = listOfMentors;
    }

    @NonNull
    @Override
    public MentorListAdapter.MentorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mentor, parent, false);
        MentorListAdapter.MentorViewHolder holder = new MentorListAdapter.MentorViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull MentorListAdapter.MentorViewHolder holder, final int position) {
        if(mentors != null) {
            ListItemMentor current = mentors.get(position);
            holder.mentorName.setText(current.getMentorName());
        } else {
            holder.mentorName.setText("No Mentors");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("UpdateMentorActivity", "Clicked on " + mentors.get(position).getMentorName());
                Intent intent = new Intent(context, MentorDetailActivity.class);
                intent.putExtra("mentorName", mentors.get(position).getMentorName());
                intent.putExtra("mentorPhoneNumber", mentors.get(position).getMentorPhoneNumber());
                intent.putExtra("mentorEmail", mentors.get(position).getMentorEmail());
                intent.putExtra("mentorID", mentors.get(position).getMentorID());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        });

    }

    void setMentors(List<ListItemMentor> setMentors) {
        mentors = setMentors;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mentors != null) {
            return mentors.size();
        } else return 0;

    }
}
