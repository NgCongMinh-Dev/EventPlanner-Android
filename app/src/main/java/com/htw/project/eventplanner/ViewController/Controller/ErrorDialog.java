package com.htw.project.eventplanner.ViewController.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;

import com.htw.project.eventplanner.R;

public class ErrorDialog extends AlertDialog {

    public ErrorDialog(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        setTitle(R.string.dialog_error_title);
        setMessage(getContext().getResources().getString(R.string.dialog_error_description));
        setButton(
                DialogInterface.BUTTON_POSITIVE,
                getContext().getResources().getString(R.string.dialog_error_confirm),
                (dialog, which) -> {
                    dismiss();
                });
    }
}
