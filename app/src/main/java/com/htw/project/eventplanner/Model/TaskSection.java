package com.htw.project.eventplanner.Model;

import java.util.List;

public class TaskSection {

    private String sectionTitle;

    private List<Task> tasks;

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
