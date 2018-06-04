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
import org.beemarie.bhellermobileappdevelopment.data.ListItemAssessment;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    public class AssessmentViewHolder extends RecyclerView.ViewHolder {
        public TextView assessmentName;

        public AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentName = itemView.findViewById(R.id.assessment_title);
        }
    }
    public static final String TAG = "RecyclerViewAdapter";
    List<ListItemAssessment> assessments;
    AssessmentAdapter.AssessmentViewHolder viewHolder;
    View.OnClickListener mClickListener;
    Context context;

    public AssessmentAdapter(Context context, List<ListItemAssessment> listOfAssessments) {
        this.context = context;
        this.assessments = listOfAssessments;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assessment, parent, false);
        AssessmentAdapter.AssessmentViewHolder holder = new AssessmentAdapter.AssessmentViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, final int position) {
        if(assessments != null) {
            ListItemAssessment current = assessments.get(position);
            holder.assessmentName.setText(current.getAssessmentName());
        } else {
            holder.assessmentName.setText("No Assessment");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AssessDetailActivity", "Clicked on " + assessments.get(position).getAssessmentName());
                Intent intent = new Intent(context, AssessmentDetailActivity.class);
                intent.putExtra("assessmentID", assessments.get(position).getAssessmentID());
                intent.putExtra("assessmentName", assessments.get(position).getAssessmentName());
                intent.putExtra("assessmentType", assessments.get(position).getAssessmentType());
                intent.putExtra("assessmentDueDate", assessments.get(position).getAssessmentDueDate());
                intent.putExtra("assessmentCourseID", assessments.get(position).getAssessmentCourseID());
                intent.putExtra("assessmentReminder", assessments.get(position).getAssessmentDueDateNotification());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        });

    }

    void setAssessments(List<ListItemAssessment> setAssessments) {
        assessments = setAssessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(assessments != null) {
            return assessments.size();
        } else return 0;

    }

}
