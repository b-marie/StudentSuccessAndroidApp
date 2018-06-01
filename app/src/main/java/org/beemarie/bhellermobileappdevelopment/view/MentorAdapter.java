package org.beemarie.bhellermobileappdevelopment.view;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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


        public MentorViewHolder(View itemView) {
            super(itemView);
            mentorName = itemView.findViewById(R.id.mentor_title);
        }
    }
    public static final String TAG = "RecyclerViewAdapter";
    List<ListItemMentor> mentors;
    MentorViewHolder viewHolder;
    View.OnClickListener mClickListener;
    Context context;



    public MentorAdapter(Context context, List<ListItemMentor> listOfMentors) {
        this.context = context;
//        LayoutInflater inflater = LayoutInflater.from(context);
        this.mentors = listOfMentors;
    }

    @NonNull
    @Override
    public MentorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mentor, parent, false);
        MentorViewHolder holder = new MentorViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull MentorViewHolder holder, final int position) {
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
                intent.putExtra("mentorID", mentors.get(position).getMentorID());
                intent.putExtra("mentorName", mentors.get(position).getMentorName());
                intent.putExtra("mentorPhoneNumber", mentors.get(position).getMentorPhoneNumber());
                intent.putExtra("mentorEmail", mentors.get(position).getMentorEmail());
                intent.putExtra("mentorCourseID", mentors.get(position).getMentorCourseID());

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
