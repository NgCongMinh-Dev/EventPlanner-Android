package com.htw.project.eventplanner.Business;

import com.htw.project.eventplanner.Model.Event;
import com.htw.project.eventplanner.Model.Task;

import java.util.stream.Collectors;

public class EventBusiness {

    public float getEventPercentage(Event event) {
        float amountOfPendingTask = (float) event.getTasks().stream().filter(task -> task.getStatus() == Task.Status.FINISHED).collect(Collectors.toList()).size();
        float totalAmount = (float) event.getTasks().size();

        return (amountOfPendingTask / totalAmount) * 100;
    }

}
