package com.htw.project.eventplanner.ViewController.Fragment;

import android.os.Bundle;
import android.view.View;

import com.htw.project.eventplanner.R;

public class GroupConversationFragment extends AbstractFragment {

    public static GroupConversationFragment newInstance() {
        Bundle args = new Bundle();

        GroupConversationFragment fragment = new GroupConversationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_group_conversation;
    }

    @Override
    public void onStart() {
        super.onStart();

        // actionbar
        actionBarController.setToolbarTitle(R.string.title_conversation_group_detail);

        // register components
        View eventPlannerView = getViewElement(getView(), R.id.action_event_planner);
        eventPlannerView.setOnClickListener(this::openEventPlannerView);
    }

    private void openEventPlannerView(View view) {
        changeFragment(EventPlannerFragment.newInstance());
    }

}
