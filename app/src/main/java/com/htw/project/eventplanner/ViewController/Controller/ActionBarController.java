package com.htw.project.eventplanner.ViewController.Controller;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.htw.project.eventplanner.R;
import com.htw.project.eventplanner.Utils.Action;

public class ActionBarController {

    private static final String EMPTY_TITLE = "";

    private Toolbar toolbar;

    private TextView toolbarTitle;

    private ImageButton toolbarAction;

    public ActionBarController(Toolbar toolbar) {
        this.toolbar = toolbar;

        this.toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        this.toolbarAction = toolbar.findViewById(R.id.toolbar_action_button);
        this.toolbarAction.setVisibility(View.GONE);
    }

    public void setToolbarTitle(int titleResourceId) {
        toolbarTitle.setText(titleResourceId);
    }

    public void setToolbarAction(Bitmap image, Action action) {
        toolbarAction.setVisibility(View.VISIBLE);
        toolbarAction.setImageBitmap(image);
        toolbarAction.setOnClickListener(view -> action.execute());
    }

    public void resetToolbar() {
        toolbarTitle.setText(EMPTY_TITLE);
        toolbarAction.setVisibility(View.GONE);
        toolbarAction.setImageBitmap(null);
    }

}
