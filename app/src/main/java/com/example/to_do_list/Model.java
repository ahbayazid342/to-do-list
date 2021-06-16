package com.example.to_do_list;

import android.view.Display;

import java.util.ArrayList;

public class Model {
    private String id;
    private String taskName;
    private String description;

    @Override
    public String toString() {
        return "Model{" +
                "taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Model(){
    }

    public Model(String id, String taskName, String description) {
        this.id = id;
        this.taskName = taskName;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
