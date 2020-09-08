package com.htw.project.eventplanner.ViewController.Fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.htw.project.eventplanner.Business.UserInfoProvider;
import com.htw.project.eventplanner.Model.Event;
import com.htw.project.eventplanner.Model.GroupConversation;
import com.htw.project.eventplanner.Model.Task;
import com.htw.project.eventplanner.Model.TaskSection;
import com.htw.project.eventplanner.R;
import com.htw.project.eventplanner.Utils.DateTimeConverter;
import com.htw.project.eventplanner.Utils.ViewScaleConverter;
import com.htw.project.eventplanner.View.ProgressBar;
import com.htw.project.eventplanner.View.TabBar;
import com.htw.project.eventplanner.View.TaskSectionView;
import com.htw.project.eventplanner.ViewController.Element.TaskViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EventPlannerFragment extends AbstractFragment {

    private static final String BUNDLE_ARG_GROUP_CONVERSATION = "bundle_arg_group_conversation";
    private static final String BUNDLE_ARG_EVENT = "bundle_arg_event";

    public static EventPlannerFragment newInstance(GroupConversation groupConversation, Event event) {
        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_ARG_GROUP_CONVERSATION, groupConversation);
        args.putParcelable(BUNDLE_ARG_EVENT, event);

        EventPlannerFragment fragment = new EventPlannerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private GroupConversation groupConversation;
    private Event event;

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
        event = bundle.getParcelable(BUNDLE_ARG_EVENT);

        taskSectionViewListCollector = new ArrayList<>();
        taskSectionViewPersonCollector = new ArrayList<>();

        actionBarController.setToolbarTitle(R.string.title_event_planner);
        actionBarController.setToolbarAction(
                BitmapFactory.decodeResource(getResources(), R.mipmap.icon_add),
                () -> changeFragment(TaskFragment.newInstance(groupConversation, UserInfoProvider.getCurrentUser(), TaskFragment.TaskAction.CREATE))
        );

        container = getViewElement(getView(), R.id.event_planner_container);

        // progress bar
        ProgressBar progressBar = createProgressbar();
        container.addView(progressBar);

        // task section
        Arrays.asList(testDataList()).stream().forEach(taskSection -> {
            TaskSectionView taskSectionView = createTaskSection(taskSection);
            taskSectionViewListCollector.add(taskSectionView);

            container.addView(taskSectionView);
        });

        Arrays.asList(testDataPerson()).stream().forEach(taskSection -> {
            TaskSectionView taskSectionView = createTaskSection(taskSection);
            taskSectionViewPersonCollector.add(taskSectionView);

        });

        // tab bar
        initTabBar();
    }

    private ProgressBar createProgressbar() {
        ProgressBar progressBar = new ProgressBar(getContext());
        progressBar.setProgress(75f);
        return progressBar;
    }

    private TaskSectionView createTaskSection(TaskSection taskSection) {
        TaskViewAdapter adapter = new TaskViewAdapter(getContext(), taskSection);
        adapter.setOnClickListener(view -> Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show());

        TaskSectionView taskSectionView = new TaskSectionView(getContext());
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) taskSectionView.getLayoutParams();
        params.setMargins(0, ViewScaleConverter.toDP(getContext(), 20), 0, 0);
        taskSectionView.setLayoutParams(params);
        taskSectionView.setSectionTitle(taskSection.getSectionTitle());

        RecyclerView sectionOneView = taskSectionView.getTaskContainer();
        sectionOneView.setAdapter(adapter);

        return taskSectionView;
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

    private TaskSection testDataList() {
        Date date = DateTimeConverter.getDate("02.09.2020");

        List<Task> tasks = new ArrayList<>();

        Task task1 = new Task();
        task1.setTitle("Einkaufen");
        task1.setDueDate(date);
        tasks.add(task1);

        Task task2 = new Task();
        task2.setTitle("Kochen");
        task2.setDueDate(date);
        tasks.add(task2);

        TaskSection taskSection = new TaskSection();
        taskSection.setSectionTitle("Pending");
        taskSection.setTasks(tasks);

        return taskSection;
    }

    private TaskSection testDataPerson() {
        Date date = DateTimeConverter.getDate("02.09.2020");

        List<Task> tasks = new ArrayList<>();

        Task task1 = new Task();
        task1.setTitle("Einkaufen");
        task1.setDueDate(date);
        tasks.add(task1);

        Task task2 = new Task();
        task2.setTitle("Kochen");
        task2.setDueDate(date);
        tasks.add(task2);

        TaskSection taskSection = new TaskSection();
        taskSection.setSectionTitle("Person");
        taskSection.setTasks(tasks);

        return taskSection;
    }

}
