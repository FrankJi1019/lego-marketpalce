package com.example.se306project1.utilities;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.se306project1.R;

public class AnimationFactory {

    private static final long duration = 1000;

    public Animation getSlideFromLeftAnimation() {
        Context context = ContextState.getInstance().getCurrentContext();
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        animation.setDuration(duration);
        return animation;
    }

    public Animation getFadeInAnimation() {
        Context context = ContextState.getInstance().getCurrentContext();
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        animation.setDuration(duration);
        return animation;
    }

    public Animation getSlideFromTopAnimation() {
        Context context = ContextState.getInstance().getCurrentContext();
        return AnimationUtils.loadAnimation(context, R.anim.slide_from_top);
    }

    public Animation getSlideFromBottomAnimation() {
        Context context = ContextState.getInstance().getCurrentContext();
        return AnimationUtils.loadAnimation(context, R.anim.slide_from_bottom);
    }

}
