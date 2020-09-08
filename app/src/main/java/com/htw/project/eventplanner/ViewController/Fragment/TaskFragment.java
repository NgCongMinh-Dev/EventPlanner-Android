package com.htw.project.eventplanner.ViewController.Fragment;

import android.app.DatePickerDialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.material.textfield.TextInputEditText;
import com.htw.project.eventplanner.Model.GroupConversation;
import com.htw.project.eventplanner.Model.User;
import com.htw.project.eventplanner.R;
import com.htw.project.eventplanner.Utils.DateTimeConverter;
import com.htw.project.eventplanner.ViewController.Controller.AssignUserDialog;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class TaskFragment extends AbstractFragment {

    public enum TaskAction {
        CREATE, UPDATE;
    }

    private static final String BUNDLE_ARG_GROUP_CONVERSATION = "bundle_arg_group_conversation";
    private static final String BUNDLE_ARG_USER = "bundle_arg_user";
    private static final String BUNDLE_ARG_ACTION = "bundle_arg_action";

    public static TaskFragment newInstance(GroupConversation groupConversation, User currentUser, TaskAction action) {
        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_ARG_GROUP_CONVERSATION, groupConversation);
        args.putParcelable(BUNDLE_ARG_USER, currentUser);
        args.putInt(BUNDLE_ARG_ACTION, action.ordinal());

        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static TaskFragment newInstance(User currentUser) {
        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_ARG_GROUP_CONVERSATION, null);
        args.putParcelable(BUNDLE_ARG_USER, currentUser);
        args.putInt(BUNDLE_ARG_ACTION, TaskAction.CREATE.ordinal());

        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private GroupConversation groupConversation;
    private User currentUser;
    private TaskAction action;

    private TextInputEditText taskTitleEditText;
    private TextInputEditText taskDescriptionEditText;
    private TextInputEditText taskPersonsEditText;
    private TextInputEditText taskDateEditText;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_task;
    }

    @Override
    public void onStart() {
        super.onStart();

        // action bar
        actionBarController.setToolbarTitle(R.string.title_task_add);
        actionBarController.setToolbarAction(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_check), () -> {
        });

        // initialize
        groupConversation = getArguments().getParcelable(BUNDLE_ARG_GROUP_CONVERSATION);
        currentUser = getArguments().getParcelable(BUNDLE_ARG_USER);
        action = TaskAction.values()[getArguments().getInt(BUNDLE_ARG_ACTION)];

        taskTitleEditText = getViewElement(getView(), R.id.task_title);
        taskDescriptionEditText = getViewElement(getView(), R.id.task_description);
        taskPersonsEditText = getViewElement(getView(), R.id.task_persons);
        taskDateEditText = getViewElement(getView(), R.id.task_date);

        // assignee check box
        taskPersonsEditText.setOnClickListener(view -> {
            new AssignUserDialog(getContext(), groupConversation, users -> {
                List<String> fullNames = users.stream().map(user -> user.getFullName()).collect(Collectors.toList());
                String text = TextUtils.join(", ", fullNames);
                taskPersonsEditText.setText(text);
            }).show();
        });

        // date picker
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            taskDateEditText.setText(DateTimeConverter.getDate(calendar.getTime()));
        };

        taskDateEditText.setOnClickListener(view -> {
            new DatePickerDialog(
                    getContext(),
                    dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });
    }

}
