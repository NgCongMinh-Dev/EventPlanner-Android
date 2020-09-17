package com.htw.project.eventplanner.ViewController.Fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.htw.project.eventplanner.Business.EventBusiness;
import com.htw.project.eventplanner.Business.GroupConversationBusiness;
import com.htw.project.eventplanner.Business.TaskBusiness;
import com.htw.project.eventplanner.Business.UserInfoProvider;
import com.htw.project.eventplanner.Model.Event;
import com.htw.project.eventplanner.Model.GroupConversation;
import com.htw.project.eventplanner.Model.Task;
import com.htw.project.eventplanner.Model.TaskSection;
import com.htw.project.eventplanner.Model.User;
import com.htw.project.eventplanner.R;
import com.htw.project.eventplanner.Rest.ApiCallback;
import com.htw.project.eventplanner.Utils.ViewScaleConverter;
import com.htw.project.eventplanner.View.ProgressBar;
import com.htw.project.eventplanner.View.TabBar;
import com.htw.project.eventplanner.View.TaskSectionView;
import com.htw.project.eventplanner.ViewController.Controller.ErrorDialog;
import com.htw.project.eventplanner.ViewController.Element.TaskViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class EventPlannerFragment extends AbstractFragment {

    private static final String BUNDLE_ARG_GROUP_CONVERSATION = "bundle_arg_group_conversation";

    public static EventPlannerFragment newInstance(GroupConversation groupConversation) {
        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_ARG_GROUP_CONVERSATION, groupConversation);

        EventPlannerFragment fragment = new EventPlannerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private GroupConversation groupConversation;
    private Event event;
    private User currentUser;

    private GroupConversationBusiness gcBusiness;
    private TaskBusiness taskBusiness;

    private LinearLayout container;
    private List<TaskSectionView> taskSectionViewListCollector;
    private List<TaskSectionView> taskSectionViewPersonCollector;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_event_planner;
    }

    @Override
    public void onStart() {
        super.onStart();

        // initialize
        Bundle bundle = getArguments();
        groupConversation = bundle.getParcelable(BUNDLE_ARG_GROUP_CONVERSATION);
        event = groupConversation.getEvent();
        currentUser = UserInfoProvider.getCurrentUser();

        gcBusiness = new GroupConversationBusiness();
        taskBusiness = new TaskBusiness();

        initTaskContainer();
    }

    private void initTaskContainer() {
        gcBusiness.getEvent(groupConversation, new ApiCallback<Event>() {
            @Override
            public void onSuccess(Event result) {
                event = result;
                groupConversation.setEvent(event);

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
    }

    private void initComponents() {
        container = getViewElement(getView(), R.id.event_planner_container);

        initActionBar();
        initProgressbar();
        initTaskSection();
        initTabBar();
    }

    private void initActionBar() {
        actionBarController.setToolbarTitle(R.string.title_event_planner);

        if (currentUser.getRole() == User.Role.ADMIN) {
            actionBarController.setToolbarAction(
                    BitmapFactory.decodeResource(getResources(), R.mipmap.icon_add),
                    () -> changeFragment(TaskFragment.newInstance(groupConversation))
            );
        }
    }

    private void initProgressbar() {
        EventBusiness business = new EventBusiness();

        ProgressBar progressBar = new ProgressBar(getContext());
        progressBar.setProgress(business.getEventPercentage(event));

        container.addView(progressBar);
    }

    private void initTaskSection() {
        taskSectionViewListCollector = new ArrayList<>();
        taskSectionViewPersonCollector = new ArrayList<>();

        taskBusiness.getTasksSortedByStatus(event).stream().forEach(taskSection -> {
            TaskSectionView taskSectionView = createTaskSection(taskSection);
            taskSectionViewListCollector.add(taskSectionView);

            container.addView(taskSectionView);
        });

        taskBusiness.getTasksSortedByAssignee(groupConversation, event).stream().forEach(taskSection -> {
            TaskSectionView taskSectionView = createTaskSection(taskSection);
            taskSectionViewPersonCollector.add(taskSectionView);
        });
    }

    private TaskSectionView createTaskSection(TaskSection taskSection) {
        TaskViewAdapter adapter = new TaskViewAdapter(
                getContext(),
                taskSection,
                this::handleTaskOnClick,
                this::handleTaskStatusChanged);

        TaskSectionView taskSectionView = new TaskSectionView(getContext());
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) taskSectionView.getLayoutParams();
        params.setMargins(0, ViewScaleConverter.toDP(getContext(), 20), 0, 0);
        taskSectionView.setLayoutParams(params);
        taskSectionView.setRecyclerViewAdapter(adapter);

        Object title = taskSection.getSectionTitle();
        if (title instanceof Integer) {
            taskSectionView.setSectionTitle((Integer) title);
        } else if (title instanceof String) {
            taskSectionView.setSectionTitle((String) title);
        }

        return taskSectionView;
    }

    private void resetFragmentView() {
        // remove all views in task container
        container.removeAllViews();

        // remove all views in tab bar container
        TabBar tabBar = getViewElement(getView(), R.id.tab_container);
        tabBar.removeAllViews();
    }

    private void handleTaskOnClick(Task task) {
        switch (task.getStatus()) {
            case PENDING:
                // admin can update/delete task
                // user can only view task
                changeFragment(TaskFragment.newInstance(
                        groupConversation,
                        task,
                        currentUser.isAdmin() ? TaskFragment.TaskAction.UPDATE : TaskFragment.TaskAction.VIEW)
                );
                break;
            case FINISHED:
                // all user can only view task
                changeFragment(TaskFragment.newInstance(groupConversation, task, TaskFragment.TaskAction.VIEW));
                break;
        }
    }

    private void handleTaskStatusChanged(Task.Status status, Task task) {
        // only admin and assignee can change the status of the task
        if (currentUser.isAdmin() || taskBusiness.isAssignee(task, currentUser)) {
            taskBusiness.changeTaskStatus(status, task, new ApiCallback<Task>() {
                @Override
                public void onSuccess(Task response) {
                    resetFragmentView();
                    initTaskContainer();
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

    private void initTabBar() {
        TabBar tabBar = getViewElement(getView(), R.id.tab_container);

        TabBar.TabBarElement taskListViewElement = new TabBar.TabBarElement(tabBar.getContext());
        taskListViewElement.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_task_list));
        taskListViewElement.setOnClickListener(new TabBar.TabBarElementClickListener() {
            @Override
            protected void execute(View view) {
                if (taskSectionViewListCollector.isEmpty()) {
                    return;
                }
                taskSectionViewPersonCollector.stream().forEach(taskSectionView -> {
                    container.removeView(taskSectionView);
                });

                taskSectionViewListCollector.stream().forEach(taskSectionView -> {
                    container.addView(taskSectionView);
                });
            }
        });
        tabBar.addView(taskListViewElement);

        TabBar.TabBarElement taskPersonViewElement = new TabBar.TabBarElement(tabBar.getContext());
        taskPersonViewElement.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_task_person));
        taskPersonViewElement.setOnClickListener(new TabBar.TabBarElementClickListener() {
            @Override
            protected void execute(View view) {
                if (taskSectionViewPersonCollector.isEmpty()) {
                    return;
                }
                taskSectionViewListCollector.stream().forEach(taskSectionView -> {
                    container.removeView(taskSectionView);
                });

                taskSectionViewPersonCollector.stream().forEach(taskSectionView -> {
                    container.addView(taskSectionView);
                });
            }
        });
        tabBar.addView(taskPersonViewElement);
    }

}
