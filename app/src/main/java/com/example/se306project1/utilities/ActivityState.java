package com.example.se306project1.utilities;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityState {

    private static ActivityState activityState = new ActivityState();

    private AppCompatActivity currentActivity;

    private ActivityState() {}

    public static ActivityState getInstance() {
        return activityState;
    }

    public AppCompatActivity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(AppCompatActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

}
