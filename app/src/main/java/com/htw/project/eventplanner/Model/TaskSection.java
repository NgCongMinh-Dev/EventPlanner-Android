package com.htw.project.eventplanner.Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskSection {

    private Comparator comparator = new StatusComparator();

    private Object sectionTitle;

    private List<Task> tasks;

    private boolean showRatio = false;

    private int unfinishedTasks = 0;

    public TaskSection() {
        tasks = new ArrayList<>();
    }

    public Object getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public void setSectionTitle(int sectionTitleId) {
        this.sectionTitle = sectionTitleId;
    }

    public List<Task> getTasks() {
        // sort tasks according to their status before get
        tasks.sort(comparator);
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);

        if (task.getStatus() == Task.Status.PENDING) {
            unfinishedTasks = unfinishedTasks + 1;
        }
    }

    public void setShowRatio(boolean showRatio) {
        this.showRatio = showRatio;
    }

    public boolean isShowRatio() {
        return showRatio;
    }

    public int getFinishedTasks() {
        return getTotalTasks() - unfinishedTasks;
    }

    public int getTotalTasks() {
        return tasks.size();
    }

    private class StatusComparator implements Comparator<Task> {

        @Override
        public int compare(Task t1, Task t2) {
            return t1.getStatus().compareTo(t2.getStatus());
        }

    }

}
