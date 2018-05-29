package org.beemarie.bhellermobileappdevelopment.view;

import android.app.LauncherActivity;
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

public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.MentorViewHolder> {
    public class MentorViewHolder extends RecyclerView.ViewHolder {
        public TextView mentorName;
//        RelativeLayout parentLayout;


        public MentorViewHolder(View itemView) {
            super(itemView);
            mentorName = itemView.findViewById(R.id.mentor_title);
//            parentLayout = itemView.findViewById(R.id.mentor_list_view);
        }
    }
    public static final String TAG = "RecyclerViewAdapter";
    List<ListItemMentor> mentors;
    private Context context;
    MentorViewHolder viewHolder;

    public MentorAdapter(Context context, List<ListItemMentor> listOfMentors) {
        this.context = context;
//        LayoutInflater inflater = LayoutInflater.from(context);
        this.mentors = listOfMentors;
    }

    @NonNull
    @Override
    public MentorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mentor, parent, false);
        return new MentorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MentorViewHolder holder, final int position) {
        if(mentors != null) {
            ListItemMentor current = mentors.get(position);
            holder.mentorName.setText(current.getMentorName());
        } else {
            holder.mentorName.setText("No Mentors");
        }
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
