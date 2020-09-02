package com.htw.project.eventplanner.ViewController.Fragment;

import android.app.DatePickerDialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;
import com.htw.project.eventplanner.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TaskFragment extends AbstractFragment {

    public static TaskFragment newInstance() {
        Bundle args = new Bundle();

        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
        taskTitleEditText = getViewElement(getView(), R.id.task_title);
        taskDescriptionEditText = getViewElement(getView(), R.id.task_description);
        taskPersonsEditText = getViewElement(getView(), R.id.task_persons);
        taskDateEditText = getViewElement(getView(), R.id.task_date);

        // date picker
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);

            taskDateEditText.setText(sdf.format(calendar.getTime()));
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
