package com.example.se306project1.statemanagement;

import java.util.Stack;

public class ActivityState {

    private static ActivityState activityState = new ActivityState();

    private Stack<ActivityResumer> activities = new Stack<>();

    private ActivityState(){}

    public void startNewActivity(ActivityResumer activityResumer) {
        this.activities.add(activityResumer);
    }

    public void goBack() {
        this.activities.pop();
        this.activities.pop().start();
    }

    public static ActivityState getInstance() {
        return activityState;
    }

}