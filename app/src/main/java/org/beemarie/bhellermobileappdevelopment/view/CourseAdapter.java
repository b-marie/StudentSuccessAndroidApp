package org.beemarie.bhellermobileappdevelopment.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;

import java.util.List;

class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        public TextView courseName;


        public CourseViewHolder(View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.course_title);
        }
    }
    public static final String TAG = "RecyclerViewAdapter";
    List<ListItemCourse> courses;
    CourseAdapter.CourseViewHolder viewHolder;
    View.OnClickListener mClickListener;
    Context context;



    public CourseAdapter(Context context, List<ListItemCourse> listOfCourses) {
        this.context = context;
//        LayoutInflater inflater = LayoutInflater.from(context);
        this.courses = listOfCourses;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        CourseViewHolder holder = new CourseViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, final int position) {
        if(courses != null) {
            ListItemCourse current = courses.get(position);
            holder.courseName.setText(current.getCourseName());
        } else {
            holder.courseName.setText("No Courses");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("UpdateCourseActivity", "Clicked on " + courses.get(position).getCourseName());
                Intent intent = new Intent(context, CourseDetailActivity.class);
                intent.putExtra("courseName", courses.get(position).getCourseName());
                intent.putExtra("courseStartDate", courses.get(position).getCourseStartDate());
                intent.putExtra("courseEndDate", courses.get(position).getCourseEndDate());
                intent.putExtra("courseStatus", courses.get(position).getCourseStatus());
                Bundle mentors = new Bundle();
                mentors.putParcelableArrayList("courseMentors", courses.get(position).getCourseMentors());
                intent.putExtras(mentors);
                Bundle assessments = new Bundle();
                assessments.putParcelableArrayList("courseAssessments", courses.get(position).getCourseAssessments());
                intent.putExtras(assessments);
                intent.putExtra("courseNotes", courses.get(position).getCourseNotes());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        });

    }

    void setCourses(List<ListItemCourse> setCourses) {
        courses = setCourses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(courses != null) {
            return courses.size();
        } else return 0;

    }

}
