package org.beemarie.bhellermobileappdevelopment.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.beemarie.bhellermobileappdevelopment.R;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;

import java.util.List;

class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    List<ListItemCourse> courses;

    public CourseAdapter(List<ListItemCourse> listOfCourses) {
        this.courses = listOfCourses;
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        holder.courseName.setText(courses.get(position).getCourseName());

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView courseName;

        public ViewHolder(View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.course_title);
        }
    }
}
