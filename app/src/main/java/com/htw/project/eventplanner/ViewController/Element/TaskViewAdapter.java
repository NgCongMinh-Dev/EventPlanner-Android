package com.htw.project.eventplanner.ViewController.Element;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.htw.project.eventplanner.Model.Task;
import com.htw.project.eventplanner.Model.TaskSection;
import com.htw.project.eventplanner.R;
import com.htw.project.eventplanner.Utils.DateTimeConverter;

import java.util.Date;

public class TaskViewAdapter extends RecyclerView.Adapter<TaskViewAdapter.TaskViewHolder> {

    private LayoutInflater layoutInflater;

    private TaskSection taskSection;

    private View.OnClickListener listener;

    public TaskViewAdapter(Context context, TaskSection taskSection) {
        this.layoutInflater = LayoutInflater.from(context);
        this.taskSection = taskSection;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.element_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskSection.getTasks().get(position);

        Date date = task.getDueDate();

        holder.title.setText(task.getTitle());
        holder.date.setText(date == null ? "TODAY" : DateTimeConverter.getDate(date));
    }

    @Override
    public int getItemCount() {
        return taskSection.getTasks().size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        TextView date;

        LinearLayout iconContainer;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(listener);

            title = itemView.findViewById(R.id.element_task_title);
            date = itemView.findViewById(R.id.element_task_date);
            iconContainer = itemView.findViewById(R.id.element_task_status_container);
        }
    }

}
