package com.example.se306project1.utilities;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AnimationFactory {

    public Animation getSlideToLeftAnimation(long duration) {
        Context context = ContextState.getInstance().getCurrentContext();
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        animation.setDuration(duration);
        return animation;
    }

    public Animation getFadeInAnimation(long duration) {
        Context context = ContextState.getInstance().getCurrentContext();
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        animation.setDuration(duration);
        return animation;
    }

}
