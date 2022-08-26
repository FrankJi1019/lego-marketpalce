package com.example.se306project1.utilities;

import android.content.Context;

import androidx.annotation.NonNull;

public class StringBuilder {

    String value;

    public StringBuilder(int resource) {
        Context context = ContextState.getInstance().getCurrentContext();
        this.value = context.getResources().getString(resource);
    }

    public <T> StringBuilder set(String key, T value) {
        this.value = this.value.replace(String.format("{%s}", key), value.toString());
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return this.value;
    }
}
