package com.htw.project.eventplanner.ViewController.Controller;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.htw.project.eventplanner.R;
import com.htw.project.eventplanner.Utils.Action;

public class ActionBarController {

    private Toolbar toolbar;

    private TextView toolbarTitle;

    private TextView toolbarAction;

    public ActionBarController(Toolbar toolbar) {
        this.toolbar = toolbar;

        this.toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        this.toolbarAction = toolbar.findViewById(R.id.toolbar_action_icon);
        this.toolbarAction.setVisibility(View.GONE);
    }

    public void setToolbarTitle(int titleResourceId) {
        toolbarTitle.setText(titleResourceId);
    }

    public void setToolbarAction(String icon, Action action) {
        toolbarAction.setText(icon);
        toolbarAction.setOnClickListener(view -> action.execute());
    }

}
