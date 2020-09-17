package com.htw.project.eventplanner.Model;

import java.util.ArrayList;
import java.util.List;

public class TaskSection {

    private Object sectionTitle;

    private List<Task> tasks;

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
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

}
