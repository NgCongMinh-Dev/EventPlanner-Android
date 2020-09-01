package com.htw.project.eventplanner.ViewController.Fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.htw.project.eventplanner.Model.Task;
import com.htw.project.eventplanner.R;
import com.htw.project.eventplanner.Utils.ViewScaleConverter;
import com.htw.project.eventplanner.View.TabBar;
import com.htw.project.eventplanner.View.TaskSection;
import com.htw.project.eventplanner.ViewController.Element.TaskViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class EventPlannerFragment extends AbstractFragment {

    public static EventPlannerFragment newInstance() {
        Bundle args = new Bundle();

        EventPlannerFragment fragment = new EventPlannerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private LinearLayout container;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_event_planner;
    }

    @Override
    public void onStart() {
        super.onStart();

        TaskViewAdapter adapter = new TaskViewAdapter(getContext(), testData());
        adapter.setOnClickListener(view -> Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show());

        container = getViewElement(getView(), R.id.event_planner_container);

        TaskSection taskSection = new TaskSection(getContext());
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) taskSection.getLayoutParams();
        params.setMargins(0, ViewScaleConverter.toDP(getContext(), 20), 0, 0);
        taskSection.setLayoutParams(params);

        RecyclerView sectionOneView = taskSection.getTaskContainer();
        sectionOneView.setAdapter(adapter);

        container.addView(taskSection);

        // tab bar
        TabBar tabBar = getViewElement(getView(), R.id.tab_container);

        TabBar.TabBarElement taskListViewElement = new TabBar.TabBarElement(tabBar.getContext());
        taskListViewElement.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_task_list));
        taskListViewElement.setOnClickListener(new TabBar.TabBarElementClickListener() {
            @Override
            protected void execute(View view) {
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        });
        tabBar.addView(taskListViewElement);

        TabBar.TabBarElement taskPersonViewElement = new TabBar.TabBarElement(tabBar.getContext());
        taskPersonViewElement.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_task_person));
        taskPersonViewElement.setOnClickListener(new TabBar.TabBarElementClickListener() {
            @Override
            protected void execute(View view) {
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        });
        tabBar.addView(taskPersonViewElement);



    }

    private List<Task> testData() {
        List<Task> tasks = new ArrayList<>();

        Task task1 = new Task();
        task1.setTitle("Einkaufen");
        task1.setDate("10.10.10");
        tasks.add(task1);

        Task task2 = new Task();
        task2.setTitle("Kochen");
        task2.setDate("10.10.10");
        tasks.add(task2);

        return tasks;
    }


}
