package com.htw.project.eventplanner.ViewController.Controller;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.htw.project.eventplanner.Model.GroupConversation;
import com.htw.project.eventplanner.Model.User;
import com.htw.project.eventplanner.R;
import com.htw.project.eventplanner.ViewController.Element.AssigneeAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class AssignUserDialog extends Dialog {

    private GroupConversation groupConversation;
    private Consumer<List<User>> consumer;

    public AssignUserDialog(@NonNull Context context, GroupConversation groupConversation, Consumer<List<User>> consumer) {
        super(context, false, null);

        this.groupConversation = groupConversation;
        this.consumer = consumer;

        init();
    }

    private void init() {
        setContentView(R.layout.dialog_task_assignee_selction);

        // cancel button
        Button cancelButton = findViewById(R.id.dialog_button_cancel);
        cancelButton.setOnClickListener(v -> {
            dismiss();
        });

        // ok button
        Set<Integer> selectedUserIndexes = new HashSet<>();

        Button okButton = findViewById(R.id.dialog_button_approve);
        okButton.setOnClickListener(v -> {
            List<User> selectedUsers = new ArrayList<>();

            List<User> users = groupConversation.getParticipants();
            selectedUserIndexes.stream().forEach(index -> {
                User user = users.get(index);
                selectedUsers.add(user);
            });

            consumer.accept(selectedUsers);

            dismiss();
        });

        // assignee container
        RecyclerView assigneeContainer = findViewById(R.id.dialog_assignee_container);
        AssigneeAdapter adapter = new AssigneeAdapter(
                getContext(),
                groupConversation.getParticipants(),
                (selected, position) -> {
                    if (selected) {
                        selectedUserIndexes.add(position);
                    } else {
                        selectedUserIndexes.remove(position);
                    }
                }
        );
        assigneeContainer.setAdapter(adapter);
        assigneeContainer.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
