package com.htw.project.eventplanner.ViewController.Fragment;

import android.app.DatePickerDialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.htw.project.eventplanner.Business.TaskBusiness;
import com.htw.project.eventplanner.Business.UserInfoProvider;
import com.htw.project.eventplanner.Model.GroupConversation;
import com.htw.project.eventplanner.Model.Task;
import com.htw.project.eventplanner.Model.User;
import com.htw.project.eventplanner.R;
import com.htw.project.eventplanner.Rest.ApiCallback;
import com.htw.project.eventplanner.Utils.DateTimeConverter;
import com.htw.project.eventplanner.ViewController.Controller.AssignUserDialog;
import com.htw.project.eventplanner.ViewController.Controller.ConfirmationDialog;
import com.htw.project.eventplanner.ViewController.Controller.ErrorDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TaskFragment extends AbstractFragment {

    public enum TaskAction {
        CREATE, UPDATE, VIEW;
    }

    private static final String BUNDLE_ARG_GROUP_CONVERSATION = "bundle_arg_group_conversation";
    private static final String BUNDLE_ARG_ACTION = "bundle_arg_action";
    private static final String BUNDLE_ARG_TASK = "bundle_arg_task";

    public static TaskFragment newInstance(GroupConversation groupConversation) {
        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_ARG_GROUP_CONVERSATION, groupConversation);
        args.putInt(BUNDLE_ARG_ACTION, TaskAction.CREATE.ordinal());

        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static TaskFragment newInstance(GroupConversation groupConversation, Task task, TaskAction action) {
        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_ARG_GROUP_CONVERSATION, groupConversation);
        args.putInt(BUNDLE_ARG_ACTION, action.ordinal());
        args.putParcelable(BUNDLE_ARG_TASK, task);

        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private GroupConversation groupConversation;
    private User currentUser;
    private TaskAction action;
    private Task task;

    private TaskBusiness business;

    private TextInputEditText taskTitleEditText;
    private TextInputEditText taskDescriptionEditText;
    private TextInputEditText taskPersonsEditText;
    private TextInputEditText taskDateEditText;
    private TextView taskDeleteButtonView;

    // variables hold value for request
    private List<User> assignees;
    private Date dueDate;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_task;
    }

    @Override
    public void onStart() {
        super.onStart();

        // initialize
        groupConversation = getArguments().getParcelable(BUNDLE_ARG_GROUP_CONVERSATION);
        currentUser = UserInfoProvider.getCurrentUser();
        action = TaskAction.values()[getArguments().getInt(BUNDLE_ARG_ACTION)];
        task = getArguments().getParcelable(BUNDLE_ARG_TASK);

        business = new TaskBusiness();

        initActionBar();
        initViewComponents();
        initAssigneeSelection();
        initDueDatePicker();
        initDeleteButton();
    }

    private void initActionBar() {
        int titleResourceId = 0;
        switch (action) {
            case CREATE:
                titleResourceId = R.string.title_task_add;
                break;
            case UPDATE:
                titleResourceId = R.string.title_task_update;
                break;
            case VIEW:
                titleResourceId = R.string.title_task_view;
                break;
        }
        actionBarController.setToolbarTitle(titleResourceId);
        actionBarController.setToolbarAction(
                BitmapFactory.decodeResource(getResources(), R.mipmap.icon_check),
                this::submit
        );
    }

    private void initViewComponents() {
        taskTitleEditText = getViewElement(getView(), R.id.task_title);
        taskDescriptionEditText = getViewElement(getView(), R.id.task_description);
        taskPersonsEditText = getViewElement(getView(), R.id.task_persons);
        taskDateEditText = getViewElement(getView(), R.id.task_date);
        taskDeleteButtonView = getViewElement(getView(), R.id.task_action_delete);

        if (action == TaskAction.UPDATE) {
            taskDeleteButtonView.setVisibility(View.VISIBLE);
            setValuesToView();
        }

        if (action == TaskAction.VIEW) {
            setValuesToView();

            taskTitleEditText.setClickable(false);
            taskTitleEditText.setFocusable(false);

            taskDescriptionEditText.setClickable(false);
            taskDescriptionEditText.setFocusable(false);

            taskPersonsEditText.setClickable(false);
            taskPersonsEditText.setFocusable(false);

            taskDateEditText.setClickable(false);
            taskDateEditText.setFocusable(false);
        }
    }

    private void setValuesToView() {
        taskTitleEditText.setText(task.getTitle());
        taskDescriptionEditText.setText(task.getDescription());

        // set for possible update
        assignees = task.getAssignees();
        taskPersonsEditText.setText(concatenateUserFullNames(task.getAssignees()));

        // set for possible update
        dueDate = task.getDueDate();
        taskDateEditText.setText(dueDate == null ? "" : DateTimeConverter.getDate(dueDate));
    }

    private void initAssigneeSelection() {
        if (action == TaskAction.VIEW) {
            return;
        }

        taskPersonsEditText.setOnClickListener(view -> {
            new AssignUserDialog(getContext(), groupConversation, users -> {
                assignees = users;

                String text = concatenateUserFullNames(users);
                taskPersonsEditText.setText(text);
            }).show();
        });
    }

    private String concatenateUserFullNames(List<User> users) {
        if (users == null || users.size() == 0) {
            return "";
        }

        List<String> fullNames = users.stream().map(user -> user.getFullName()).collect(Collectors.toList());
        return TextUtils.join(", ", fullNames);
    }

    private void initDueDatePicker() {
        if (action == TaskAction.VIEW) {
            return;
        }

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            dueDate = calendar.getTime();

            taskDateEditText.setText(DateTimeConverter.getDate(dueDate));
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

    private void initDeleteButton() {
        taskDeleteButtonView.setOnClickListener(view -> {
            ConfirmationDialog dialog = new ConfirmationDialog(getContext());
            dialog.setTitle(R.string.task_form_delete_dialog_title);
            dialog.setMessage(getContext().getResources().getString(R.string.task_form_delete_dialog_description));
            dialog.setPositiveButton(() -> {
                business.deleteTask(task, new ApiCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        returnToPreviousFragment();
                    }

                    @Override
                    public void onError(String message) {
                        getActivity().runOnUiThread(() -> {
                            System.err.println(message);
                            new ErrorDialog(getContext()).show();
                        });
                    }
                });
            });
            dialog.setNegativeButton();
            dialog.show();
        });
    }

    private Task getTaskFromView() {
        Task task = new Task();
        task.setTitle(taskTitleEditText.getText().toString());
        task.setDescription(taskDescriptionEditText.getText().toString());
        task.setStatus(Task.Status.PENDING);
        task.setAssignees(assignees);
        task.setDueDate(dueDate);
        return task;
    }

    private void submit() {
        switch (action) {
            case CREATE:
                createTask();
                break;
            case UPDATE:
                updateTask();
                break;
            case VIEW:
                returnToPreviousFragment();
                break;
        }
    }

    private void createTask() {
        Task task = getTaskFromView();

        business.createTask(task, groupConversation.getEvent(), new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                returnToPreviousFragment();
            }

            @Override
            public void onError(String message) {
                getActivity().runOnUiThread(() -> {
                    System.err.println(message);
                    new ErrorDialog(getContext()).show();
                });
            }
        });
    }

    private void updateTask() {
        Task taskFromView = getTaskFromView();
        taskFromView.setId(task.getId());

        business.updateTask(taskFromView, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                returnToPreviousFragment();
            }

            @Override
            public void onError(String message) {
                getActivity().runOnUiThread(() -> {
                    System.err.println(message);
                    new ErrorDialog(getContext()).show();
                });
            }
        });
    }

}
