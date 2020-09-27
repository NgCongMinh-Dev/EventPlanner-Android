package com.htw.project.eventplanner.ViewController.Element;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.htw.project.eventplanner.Model.Task;
import com.htw.project.eventplanner.Model.TaskSection;
import com.htw.project.eventplanner.Model.User;
import com.htw.project.eventplanner.R;
import com.htw.project.eventplanner.Utils.DateTimeConverter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TaskViewAdapter extends RecyclerView.Adapter<TaskViewAdapter.TaskViewHolder> {

    public enum Type {
        STATUS_FILTERED, USER_FILTERED;
    }

    private Drawable finishedTaskImage;

    private LayoutInflater layoutInflater;

    private Type type;

    private TaskSection taskSection;

    private ItemClickListener<Task> listener;

    private StatusChangeListener statusChangeListener;

    public TaskViewAdapter(Context context, Type type, TaskSection taskSection, ItemClickListener<Task> listener, StatusChangeListener statusChangeListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.type = type;
        this.taskSection = taskSection;
        this.listener = listener;
        this.statusChangeListener = statusChangeListener;
        this.finishedTaskImage = context.getResources().getDrawable(R.mipmap.icon_check, null);
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
        if (date != null) {
            switch (task.getStatus()) {
                case PENDING:
                    holder.date.setText(DateTimeConverter.getDate(date));
                    holder.date.setVisibility(View.VISIBLE);
                    break;
                case FINISHED:
                    // not necessary
                    break;
            }
        }

        holder.title.setText(task.getTitle());

        holder.changeIcon(task.getStatus());

        holder.bindOnItemClickedListener(task, listener);
        holder.bindOnTaskStatusChangedListener(task, statusChangeListener);

        List<User> assignees = task.getAssignees();
        if (type == Type.STATUS_FILTERED && !assignees.isEmpty()) {
            List<String> fullNames = assignees.stream().map(user -> user.getFullName()).collect(Collectors.toList());
            holder.assignees.setText(fullNames.size() == 0 ? "" : TextUtils.join(", ", fullNames));
            holder.assignees.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return taskSection.getTasks().size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        TextView assignees;

        TextView date;

        LinearLayout infoContainer;

        LinearLayout iconContainer;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.element_task_title);
            assignees = itemView.findViewById(R.id.element_task_assignees);
            date = itemView.findViewById(R.id.element_task_date);
            infoContainer = itemView.findViewById(R.id.element_task_info_container);
            iconContainer = itemView.findViewById(R.id.element_task_status_container);
        }

        private void bindOnItemClickedListener(Task task, ItemClickListener listener) {
            infoContainer.setOnClickListener(view -> listener.onItemClicked(task));
        }

        private void bindOnTaskStatusChangedListener(Task task, StatusChangeListener statusChangeListener) {
            iconContainer.setOnClickListener(view -> {
                Task.Status oldStatus = task.getStatus();
                Task.Status newStatus = Task.Status.PENDING;
                switch (oldStatus) {
                    case PENDING:
                        newStatus = Task.Status.FINISHED;
                        break;
                    case FINISHED:
                        newStatus = Task.Status.PENDING;
                        break;
                }

                statusChangeListener.onStatusChanged(newStatus, task);
            });
        }

        private void changeIcon(Task.Status status) {
            switch (status) {
                case PENDING:
                    iconContainer.setBackground(null);
                    break;
                case FINISHED:
                    iconContainer.setBackground(finishedTaskImage);
                    break;
            }
        }

    }

    public interface StatusChangeListener {

        void onStatusChanged(Task.Status status, Task task);

    }

}
