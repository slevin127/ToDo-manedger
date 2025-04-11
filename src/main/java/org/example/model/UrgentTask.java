package org.example.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
@JsonTypeName("urgent")
public class UrgentTask extends Task {
    private int priorityLevel;

    public UrgentTask() {

    }

    public UrgentTask(String title, String description, String category, int priorityLevel) {
        super(title, description, category);
        this.priorityLevel = priorityLevel;
    }

    @Override
    public String toString() {
return super.toString() + ", priorityLevel=" + priorityLevel;


    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
}
