package com.htw.project.eventplanner.ViewController.Element;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.htw.project.eventplanner.Model.User;
import com.htw.project.eventplanner.R;

import java.util.List;
import java.util.function.BiConsumer;

public class AssigneeAdapter extends RecyclerView.Adapter<AssigneeAdapter.AssigneeViewHolder> {

    private LayoutInflater inflater;

    private List<User> users;

    private BiConsumer<Boolean, Integer> biConsumer;

    public AssigneeAdapter(Context context, List<User> users, BiConsumer<Boolean, Integer> biConsumer) {
        this.inflater = LayoutInflater.from(context);
        this.users = users;
        this.biConsumer = biConsumer;
    }

    @NonNull
    @Override
    public AssigneeAdapter.AssigneeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.element_assignee_selection, parent, false);
        return new AssigneeAdapter.AssigneeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssigneeAdapter.AssigneeViewHolder holder, int position) {
        User user = users.get(position);

        holder.container.setOnClickListener(view -> {
            boolean selected = !holder.selectionBox.isChecked();
            holder.selectionBox.setChecked(selected);

            biConsumer.accept(selected, position);
        });
        holder.fullNameTextView.setText(user.getFullName());
        holder.usernameTextView.setText(user.getUsername());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class AssigneeViewHolder extends RecyclerView.ViewHolder {

        View container;

        CheckBox selectionBox;

        TextView fullNameTextView;

        TextView usernameTextView;

        public AssigneeViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView;
            selectionBox = itemView.findViewById(R.id.assignee_selection_check_box);
            fullNameTextView = itemView.findViewById(R.id.assignee_selection_participant_full_name);
            usernameTextView = itemView.findViewById(R.id.assignee_selection_participant_username);
        }

    }

}
