package com.htw.project.eventplanner.ViewController.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.htw.project.eventplanner.BuildConfig;
import com.htw.project.eventplanner.Business.GroupConversationBusiness;
import com.htw.project.eventplanner.Model.GroupConversation;
import com.htw.project.eventplanner.R;
import com.htw.project.eventplanner.Rest.ApiCallback;
import com.htw.project.eventplanner.ViewController.Controller.ErrorDialog;
import com.htw.project.eventplanner.ViewController.Element.ParticipantAdapter;

public class GroupConversationFragment extends AbstractFragment {

    public static GroupConversationFragment newInstance() {
        Bundle args = new Bundle();

        GroupConversationFragment fragment = new GroupConversationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private GroupConversationBusiness gcBusiness;

    private GroupConversation gc;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_group_conversation;
    }

    @Override
    public void onStart() {
        super.onStart();

        gcBusiness = new GroupConversationBusiness();
        gcBusiness.getGroupConversation(BuildConfig.GROUP_CONVERSATION_ID, new ApiCallback<GroupConversation>() {
            @Override
            public void onSuccess(GroupConversation response) {
                gc = response;
                initComponents();
            }

            @Override
            public void onError(String message) {
                getActivity().runOnUiThread(() -> {
                    System.err.println(message);
                    new ErrorDialog(getContext()).show();
                });
            }
        });

        // actionbar
        actionBarController.setToolbarTitle(R.string.title_conversation_group_detail);
    }

    private void initComponents() {
        // title
        TextView titleView = getViewElement(getView(), R.id.group_info_title);
        titleView.setText(gc.getTitle());

        // description
        TextView descriptionView = getViewElement(getView(), R.id.group_info_description);
        descriptionView.setText("Description");

        // event planner action
        View eventPlannerView = getViewElement(getView(), R.id.group_info_action_event_planner);
        eventPlannerView.setOnClickListener(view -> changeFragment(EventPlannerFragment.newInstance(gc)));

        // participants
        RecyclerView participantContainer = getViewElement(getView(), R.id.group_info_participant_container);
        participantContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        participantContainer.setAdapter(new ParticipantAdapter(getContext(), gc.getParticipants()));
    }

}
