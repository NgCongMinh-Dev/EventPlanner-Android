package com.htw.project.eventplanner.ViewController.Element;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.htw.project.eventplanner.Model.Task;
import com.htw.project.eventplanner.R;

import java.util.List;

public class TaskViewAdapter extends RecyclerView.Adapter<TaskViewAdapter.TaskViewHolder> {

    private LayoutInflater layoutInflater;

    private List<Task> tasks;

    private View.OnClickListener listener;

    public TaskViewAdapter(Context context, List<Task> tasks) {
        this.layoutInflater = LayoutInflater.from(context);
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.element_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);

        holder.title.setText(task.getTitle());
        holder.date.setText(task.getDate());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        public TextView title;

        public TextView date;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(listener);
            this.title = itemView.findViewById(R.id.element_task_title);
            this.date = itemView.findViewById(R.id.element_task_date);
        }
    }

}
