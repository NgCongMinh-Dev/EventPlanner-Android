package com.htw.project.eventplanner.ViewController.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.htw.project.eventplanner.R;
import com.htw.project.eventplanner.Utils.Action;

public class ConfirmationDialog extends AlertDialog implements DialogInterface.OnClickListener {

    private static String BUTTON_TEXT_CONFIRMATION;
    private static String BUTTON_TEXT_CANCEL;

    public ConfirmationDialog(Context context) {
        super(context);

        BUTTON_TEXT_CONFIRMATION = context.getResources().getString(R.string.dialog_conformation_confirm);
        BUTTON_TEXT_CANCEL = context.getResources().getString(R.string.dialog_conformation_cancel);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:

                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dismiss();
                break;
        }
    }

    public void setPositiveButton(Action action) {
        setButton(DialogInterface.BUTTON_POSITIVE, BUTTON_TEXT_CONFIRMATION, (dialog, which) -> {
            action.execute();
            dismiss();
        });
    }

    public void setNegativeButton(Action action) {
        setButton(DialogInterface.BUTTON_NEGATIVE, BUTTON_TEXT_CANCEL, (dialog, which) -> {
            action.execute();
        });
    }

    public void setNegativeButton() {
        setButton(DialogInterface.BUTTON_NEGATIVE, BUTTON_TEXT_CANCEL, (dialog, which) -> {
            dismiss();
        });
    }

}
