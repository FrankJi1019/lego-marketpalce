package com.example.se306project1.utilities;

import android.content.Context;

public class ContextState {

    private static final ContextState contextState = new ContextState();

    private Context currentContext;

    private ContextState() {}

    public static ContextState getInstance() {
        return contextState;
    }

    public Context getCurrentContext() {
        return currentContext;
    }

    public void setCurrentContext(Context currentActivity) {
        this.currentContext = currentActivity;
    }

}
